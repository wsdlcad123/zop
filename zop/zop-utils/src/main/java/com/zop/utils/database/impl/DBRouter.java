package com.zop.utils.database.impl;

import com.zop.utils.database.IDBRouter;
import com.zop.utils.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据路由器
 */
public class DBRouter implements IDBRouter {
    protected  final Log logger = LogFactory.getLog(getClass());
	// 表前缀
	private String prefix;

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

    /**
     * 获取表名
     * @param moudle 模块名
     * @return
     */
	public String getTableName(String moudle) {
		String result = StringUtil.addPrefix(moudle, prefix);
		return result;
	}
}
