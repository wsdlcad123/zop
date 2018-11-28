package com.zop.rewrite.core;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.zop.rewrite.util.FreeMarkerUtil;
import com.zop.webutils.context.ThreadContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sun.xml.messaging.saaj.util.ByteOutputStream;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * FreeMarker解析器
 * 
 * @author kingapex 2010-2-16下午03:42:40
 */
public final class FreeMarkerPaser {

    private final Log logger=LogFactory.getLog(getClass());

	private static ThreadLocal<FreeMarkerPaser> managerLocal = new ThreadLocal<FreeMarkerPaser>();
    /*
    * freemarker data model 通过putData方法设置模板中的值
    */
    private Map<String, Object> data;

    /*
     * 模板文件的名字，默认为与插件类同名
     */
    private String pageName;

    /*
     * 模板页面的扩展名，默认为.html
     */
    private String pageExt;

    /*
     * 页面所在文件夹
     */
    private String pageFolder;


	public FreeMarkerPaser() {
		data = new HashMap<String, Object>();
		this.pageFolder = null;
	}

	/**
	 * 获取当前线程的 fremarkManager
	 * 
	 * @return
	 */
	public final static FreeMarkerPaser getInstance() {
		if (managerLocal.get() == null) {
			throw new RuntimeException("freemarker paser is null");
		}
		FreeMarkerPaser fmp = managerLocal.get();
		fmp.setPageFolder(null);
		fmp.setPageName(null);
		return fmp;
	}

	public final static FreeMarkerPaser getCurrInstance() {
		if (managerLocal.get() == null) {
			throw new RuntimeException("freemarker paser is null");
		}
		FreeMarkerPaser fmp = managerLocal.get();
		return fmp;
	}

	public final static void set(FreeMarkerPaser fp) {
		managerLocal.set(fp);
	}

	public final static void remove() {
		managerLocal.remove();
	}

	/**
	 * 设置挂件模板的变量
	 * 
	 * @param key
	 * @param value
	 */
	public void putData(String key, Object value) {
		if (key != null && value != null) {
            data.put(key, value);
        }
	}

	public void putData(Map map) {
		if (map != null)
			data.putAll(map);
	}

	public Object getData(String key) {
		if (key == null)
			return null;
		return data.get(key);
	}

	public String proessPageContent() {
		try {
            pageExt = pageExt == null ? ".html" : pageExt;
			Configuration	cfg = this.getCfg();
			cfg.setNumberFormat("0.##");
			Template temp = cfg.getTemplate(pageName + pageExt);
			ByteOutputStream stream = new ByteOutputStream();
			Writer out = new OutputStreamWriter(stream);
			temp.process(data, out);
			out.flush();
			String content = stream.toString();
			return content;
		} catch (Exception e) {
			logger.error("template" ,e);
            logger.debug(" pageFolder ["+pageFolder+"] pagename ["+pageName+"]" );
		 	e.printStackTrace();
		}
		return "widget  processor error";
	}




	private Configuration getCfg() {
		Configuration cfg = FreeMarkerUtil.getCfg();
		cfg.setServletContextForTemplateLoading(ThreadContextHolder.getHttpRequest().getSession().getServletContext(), pageFolder);
		return cfg;
	}

	/**
	 * 设置模板文件的名称
	 * 
	 * @param pageName
	 */
	public void setPageName(String pageName) {
		this.pageName = pageName;
		logger.debug("set pageName ["+pageName+"]" );
	}

	/**
	 * 设置模板页面扩展名
	 * 
	 * @param pageExt
	 */
	public void setPageExt(String pageExt) {
		this.pageExt = pageExt;
	}

	public void setPageFolder(String pageFolder) {
		this.pageFolder = pageFolder;
        logger.debug("set folder ["+pageFolder+"]" );
	}
}
