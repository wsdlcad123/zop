package com.zop.utils.dbsolution;

import java.sql.Connection;
/**
 * 数据库导入导出解决方案接口
 * 
 */
public interface IDBSolution {
	public void setConnection(Connection conn);
	public boolean dbImport(String xml);
	public String dbExport(String[] tables, boolean dataOnly);
	public void setPrefix(String prefix);
}
