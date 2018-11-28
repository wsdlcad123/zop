package com.zop.utils.database.impl;

import com.zop.utils.database.ISqlFileExecutor;
import com.zop.utils.util.FileUtil;
import com.zop.utils.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
/**
 * sql文件执行器
 */
final public class DefaultSqlFileExecutor implements ISqlFileExecutor {
	
	private JdbcTemplate jdbcTemplate;

    protected  final Log logger = LogFactory.getLog(getClass());
	
	public void execute(String sqlPath) {
		String content ;
		if(sqlPath.startsWith("file:")){
			content = FileUtil.readFile(sqlPath.replaceAll("file:", ""));
		}else{
			content = sqlPath;
		}
		batchExecute(content);
	}

	private void batchExecute(String content){
		content = StringUtil.delSqlComment(content);
		content = content.replaceAll("\r", "");
		String spliter = ";\n";
		
		String[] sql_ar = StringUtils.split(content, spliter);
		
		if(StringUtil.isEmpty(content) || sql_ar== null || sql_ar.length==0) return ;
		
		if(logger.isDebugEnabled()){
			logger.debug("开始执行sql...." );
		}
		
		try{
		 	//this.jdbcTemplate.batchUpdate(sql_ar);
			for(int i=0;i<sql_ar.length;i++){
			String s = sql_ar[i];
			if(!StringUtil.isEmpty(s)){
				s = s.trim().replaceAll("\n", " ");
				if(logger.isDebugEnabled()){
					logger.debug("execute->"+s );
				}
				if(!s.startsWith("declare")  )
				this.jdbcTemplate.execute(s); 
			} else{
                if(logger.isDebugEnabled()){
                    logger.debug("sql is empty");
                }
			}
		  }				
		}catch(RuntimeException e){
			this.logger.error("执行sql出错",e.fillInStackTrace());
			throw  e;
		}
		if(logger.isDebugEnabled()){
			logger.debug("执行完成");
		}
		
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public static void main(String[] args){

	}
	
}
