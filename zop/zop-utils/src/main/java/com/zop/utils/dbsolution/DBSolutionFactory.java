package com.zop.utils.dbsolution;

import java.sql.Connection;
import java.sql.SQLException;

import com.zop.utils.context.SpringContextHolder;
import com.zop.utils.context.ZopUtilsSetting;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据解决方案工厂类
 * 
 */
public class DBSolutionFactory {
	public static IDBSolution getDBSolution() {
        return SpringContextHolder.getBean("mysqlSolution");
	}
	
	public static Connection getConnection(JdbcTemplate jdbcTemplate){
		if(jdbcTemplate==null) {
            jdbcTemplate = SpringContextHolder.getBean("jdbcTemplate");
        }
		try {
			return jdbcTemplate.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean dbImport(String xml, String prefix) {
		Connection conn = getConnection(null);
		IDBSolution dbsolution = getDBSolution();
		dbsolution.setPrefix(prefix);
		dbsolution.setConnection(conn);
		boolean result;
		result = dbsolution.dbImport(xml);
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return result;
	}
	
	public static String dbExport(String[] tables,boolean dataOnly,String prefix) {
		Connection conn = getConnection(null);
		IDBSolution dbsolution = getDBSolution();
		dbsolution.setPrefix(prefix);
		dbsolution.setConnection(conn);
		String result = "";
		result = dbsolution.dbExport(tables,dataOnly);
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
}
