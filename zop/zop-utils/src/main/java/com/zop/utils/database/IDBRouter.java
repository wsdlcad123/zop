package com.zop.utils.database;

/**
 * 数据库路由器
 * @author kingapex
 * 2010-1-5下午05:14:44
 */
public interface IDBRouter {
	
	/**
	 * 得到表名
	 * @param moudle 模块名
	 * @return
	 */
	public String getTableName(String moudle);

}
