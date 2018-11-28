package com.zop.utils.database.impl;

import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


/**
 * 覆写jdbctemlate ，使用LowerCaseColumnMapRowMapper
 */
public class LowerCaseJdbcTemplate extends JdbcTemplate {
	@Override
	protected RowMapper getColumnMapRowMapper() {
		return new MySqlColumnMapRowMapper();
	}

}
