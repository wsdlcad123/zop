package com.zop.utils.dbsolution.impl;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.zop.utils.context.ZopUtilsSetting;
import com.zop.utils.util.FileUtil;
import com.zop.utils.util.StringUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
/**
 * 数据库导入类
 * 
 * @author liuzy
 * 
 */
public class DBImporter extends DBPorter {
	private Document xmlDoc;

	public DBImporter(DBSolution solution) {
		super(solution);
	}

	/**
	 * 加载xml文件
	 * 
	 * @param xmlFile
	 * @return
	 */
	private Document loadDocument(String xmlFile) throws DocumentException {
		Document document = null;
		SAXReader saxReader = new SAXReader();
		File file = new File(xmlFile);
		if (file.exists())
			document = saxReader.read(new File(xmlFile));
		return document;
	}

	private List<Object> prepareValue(String values) {
		List<Object> objects = new ArrayList<Object>();
		String[] value = values.split(",");
		for (int i = 0; i < value.length; i++) {
			objects.add(solution.getFuncValue(solution.decodeValue(value[i]
					.replaceAll("'", ""))));
		}

		return objects;
	}

	private void doExecute(Statement state, String sql){
		try {
			if(sql!=null)
				state.execute(sql);
		} catch (SQLException e) {

		}
	}
	
	private boolean doInsert(Element action) {
		
		final String table = solution.getTableName(action.elementText("table"));
		String fields = action.elementText("fields");
		String values = action.elementText("values");

		final List<Object> objects = prepareValue(values);

		String sql = "insert into " + table + " (" + fields + ") values (";
		final String[] value = values.split(",");
		for (int i = 0; i < value.length; i++)
			sql = sql + "?,";

		sql = sql.substring(0, sql.length() - 1) + ")";

		try {
			Statement state = solution.conn.createStatement();
			PreparedStatement ps = solution.conn.prepareStatement(sql);

			for (int i = 1; i <= value.length; i++) {
				ps.setObject(i, objects.get(i - 1));
			}

			if (solution.beforeInsert(table, fields, values)) {
				doExecute(state,solution.getSqlExchange());
				ps.execute();
				solution.afterInsert(table, fields, values);
				doExecute(state,solution.getSqlExchange());
				ps.close();
				state.close();
			} else {
				System.out.println("beforeInsert返回false，insert被阻止！");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("出错语句：" + sql);
			System.out.println("出错值：" + values);
			return false;
		}
		return true;
	}
	private boolean doTruncate(Element action) {
		String table = solution.getTableName(action.elementText("table"));
		String sql="" ;
		sql+="truncate table "+table;
		return solution.executeSqls(sql);
	}
	private boolean doDrop(Element action) {
		String table = solution.getTableName(action.elementText("table"));
		String sql = solution.getDropSQL(table);
		return solution.executeSqls(sql);
	}
	
	private boolean doCreate(Element action) {
		String sql = solution.getCreateSQL(action);

		return solution.executeSqls(sql);
	}

    private boolean doIndex(Element action) {
        String sql = "create index ";
        String table;
        table = solution.getTableName(action.elementText("table"));
        List<Element> fields = action.elements("field");
        String field = " (";
        String name = "_";
        for(int i=0,len=fields.size();i<len;i++) {
            Element element = fields.get(i);
            field = field + element.elementText("name") + ",";
            name = name + element.elementText("name") + "_";
        }
        field = field.substring(0,field.length()-1) + ")";
        name = name.substring(0,name.length()-1);

        sql = sql + "idx" + name + " on " + table + field;
        return solution.executeSqls(sql);
    }

    private boolean doAlter(Element action){

        try{
            String table;
            String sql="" ;
            table = solution.getTableName(action.elementText("table"));
            List<Element> fields = action.elements("field");
            for(int i=0,len=fields.size();i<len;i++) {
                Element element = fields.get(i);
                String type = element.attributeValue("type");
                String name = element.elementText("name") ;
                String size = element.elementText("size") ;
                if(i!=0){
                    sql+=",";
                }
                if("add".equals(type)){
                    String datatype = element.elementText("type") ;
                    sql+=" add column "+ name +" " ;
                    sql+=solution.toLocalType(datatype, size);
                    String def = element.elementText("default") ;
                    if(!StringUtil.isEmpty(def)){
                        sql+=" default "+def;
                    }
                }
                if("drop".equals(type)){
                    sql+=" drop column "+ name ;
                }
            }
            sql ="alter table "+table +" "+sql;
            solution.executeSqls(sql);
            return true;
        }catch(RuntimeException e){
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 执行action内容
     *
     * @param action
     * @return
     */
    private boolean doAction(Element action) {
        String command = action.elementText("command").toLowerCase();
        if ("create".equals(command)) {
            return doCreate(action);
        } else if ("insert".equals(command)) {
            return doInsert(action);
        } else if ("drop".equals(command)) {
            return doDrop(action);
        } else if ("index".equals(command)) {
            return doIndex(action);
        } else  if("alter".equals(command)){
            return doAlter(action);
        } else if ("truncate".equals(command)) {
            return doTruncate(action);
        }
        return true;
    }


	/**
	 * 导入一个xml文件到数据库中
	 * 
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean doImport(String xml) {
		solution.beforeImport();
		try {
			if (xml.startsWith("file:")) {
				xml = FileUtil.readFile(xml.replaceAll("file:", ""));
				xmlDoc = DocumentHelper.parseText(xml);
			} else if (xml.startsWith("<?xml version")) {
				xmlDoc = DocumentHelper.parseText(xml);
			}
			else {
				xmlDoc = loadDocument(xml);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			return false;
		}
		List<Element> actions = xmlDoc.getRootElement().elements("action");
		for (Element action : actions) {
			if (!doAction(action))
				return false;
		}
		solution.afterImport();
		return true;
	}
}