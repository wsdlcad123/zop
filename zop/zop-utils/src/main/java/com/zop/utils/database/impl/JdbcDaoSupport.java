package com.zop.utils.database.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zop.utils.util.ReflectionUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.zop.utils.database.DBRuntimeException;
import com.zop.utils.database.IDaoSupport;
import com.zop.utils.util.StringUtil;
import com.zop.utils.database.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.util.Assert;

/**
 * jdbc数据库操作支撑
 *
 */
public class JdbcDaoSupport<T> implements IDaoSupport<T> {

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcTemplate simpleJdbcTemplate;
    protected  final Log logger = LogFactory.getLog(getClass());

	public void execute(String sql, Object... args) {
		try {
			this.simpleJdbcTemplate.update(sql, args);
		} catch (Exception e) {
			throw new DBRuntimeException(e, sql);
		}
	}

	public int getLastId(String table) {
			return this.jdbcTemplate.queryForInt("SELECT last_insert_id() as id");
	}

	public void insert(String table, Map fields) {
		String sql = "";
		try {
			Assert.hasText(table, "表名不能为空");
			Assert.notEmpty(fields, "字段不能为空");
			table = quoteCol(table);

			Object[] cols = fields.keySet().toArray();
			Object[] values = new Object[cols.length];
			for (int i = 0; i < cols.length; i++) {
				if (fields.get(cols[i]) == null) {
					values[i] = null;
				} else {
					values[i] = fields.get(cols[i]).toString();
				}
				cols[i] = quoteCol(cols[i].toString());
			}
			sql = "INSERT INTO " + table + " (" + StringUtil.implode(", ", cols);
			sql = sql + ") VALUES (" + StringUtil.implodeValue(", ", values);
			sql = sql + ")";
            if(logger.isDebugEnabled()){
                logger.debug("execute->"+sql);
            }
            this.jdbcTemplate.update(sql, values);
		} catch (Exception e) {
			throw new DBRuntimeException(e, sql);
		}
	}

	public void insert(String table, Object po) {
		insert(table, ReflectionUtil.po2Map(po));
	}

	public int queryForInt(String sql, Object... args) {
		try {
			return this.simpleJdbcTemplate.queryForInt(sql, args);
		} catch (Exception e) {
            throw new DBRuntimeException(e, sql);
		}
	}

    public long queryForLong(String sql, Object... args) {
        return this.jdbcTemplate.queryForLong(sql, args);
    }

	public String queryForString(String sql) {
		String s = "";
		try {
			s = (String) this.jdbcTemplate.queryForObject(sql, String.class);
		} catch (Exception e) {
            throw new DBRuntimeException(e, sql);
		}
		return s;
	}

    public T queryForObject(String sql, Class mappedClass, Object... args) {
        try {
            return (T) simpleJdbcTemplate
                    .queryForObject(sql, ParameterizedBeanPropertyRowMapper
                            .newInstance(mappedClass), args);
        } catch (Exception e) {
            throw new DBRuntimeException(e, sql);
        }
    }

    public T queryForObject(String sql, ParameterizedRowMapper mapper,
                            Object... args) {
        try {
            return  (T) this.simpleJdbcTemplate.queryForObject(sql, mapper, args);
        } catch (Exception e) {
            throw new DBRuntimeException(e, sql);
        }
    }

    public Map queryForMap(String sql, Object... args) {
        try {
            Map map = this.jdbcTemplate.queryForMap(sql, args);
            return map;
        } catch (Exception e) {
            throw new DBRuntimeException(e, sql);
        }
    }



	public List queryForList(String sql, Object... args) {
		return this.jdbcTemplate.queryForList(sql, args);
	}

	public List<T> queryForList(String sql, RowMapper mapper, Object... args) {
		try {
			return this.jdbcTemplate.query(sql, args, mapper);
		} catch (Exception e) {
			throw new DBRuntimeException(e, sql);
		}
	}

	public List<T> queryForList(String sql, Class mappedClass, Object... args) {
		return this.simpleJdbcTemplate.query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(mappedClass), args);
	}

	public List queryForListPage(String sql, int pageNo, int pageSize,
			Object... args) {
		try {
			String listSql = this.buildPageSql(sql, pageNo, pageSize);
			return queryForList(listSql, args);
		} catch (Exception e) {
			throw new DBRuntimeException(e, sql);
		}

	}

	public List<T> queryForListPage(String sql, int pageNo, int pageSize,
			RowMapper mapper) {
		try {
			String listSql = this.buildPageSql(sql, pageNo, pageSize);
			return this.queryForList(listSql, mapper);
		} catch (Exception e) {
			throw new DBRuntimeException(e, sql);
		}

	}


	public Page queryForPage(String sql, int pageNo, int pageSize,
			Object... args) {
		try {
			String listSql = buildPageSql(sql, pageNo, pageSize);
			String countSql = "SELECT COUNT(*) " + removeSelect(removeOrders(sql));
			List list = queryForList(listSql, args);
			int totalCount = queryForInt(countSql, args);
            long start = (pageNo - 1) * pageSize;
			return new Page(start, totalCount, pageSize, list);
		} catch (Exception ex) {
			throw new DBRuntimeException(ex, sql);
		}
	}

	
	public Page queryForPage(String sql, int pageNo, int pageSize,
			RowMapper rowMapper, Object... args) {
		try {
			String listSql = buildPageSql(sql, pageNo, pageSize);
			String countSql = "SELECT COUNT(*) " + removeSelect(removeOrders(sql));
			List<T> list = this.queryForList(listSql, rowMapper, args);
			int totalCount = queryForInt(countSql, args);
            long start = (pageNo - 1) * pageSize;
			return new Page(start, totalCount, pageSize, list);
		} catch (Exception ex) {
			throw new DBRuntimeException(ex, sql);
		}
	}

	public Page queryForPage(String sql, int pageNo, int pageSize,
			Class<T> mappedClass, Object... args) {
		try {
			String listSql = buildPageSql(sql, pageNo, pageSize);
			String countSql = "SELECT COUNT(*) " + removeSelect(removeOrders(sql));
			List<T> list = this.queryForList(listSql, mappedClass, args);
			int totalCount = queryForInt(countSql, args);
            long start = (pageNo - 1) * pageSize;
			return new Page(start, totalCount, pageSize, list);
		} catch (Exception ex) {
			throw new DBRuntimeException(ex, sql);
		}
	}

	public void update(String table, Map fields, Map where) {
		String whereSql = "";
		if (where != null) {
			Object[] wherecols = where.keySet().toArray();
			for (int i = 0; i < wherecols.length; i++) {
				wherecols[i] = quoteCol(wherecols[i].toString()) + "="
						+ quoteValue(where.get(wherecols[i]).toString());
			}
			whereSql += StringUtil.implode(" AND ", wherecols);
		}
		update(table, fields, whereSql);
	}

	public void update(String table, T po, Map where) {
		String whereSql = "";
		// where值
		if (where != null) {
			Object[] wherecols = where.keySet().toArray();
			for (int i = 0; i < wherecols.length; i++) {
				wherecols[i] = quoteCol(wherecols[i].toString()) + "="
						+ quoteValue(where.get(wherecols[i]).toString());
			}
			whereSql += StringUtil.implode(" AND ", wherecols);
		}
		update(table, ReflectionUtil.po2Map(po), whereSql);

	}

	public void update(String table, T po, String where) {
		this.update(table, ReflectionUtil.po2Map(po), where);

	}

	public void update(String table, Map fields, String where) {
		String sql = "";
		try {
			table = quoteCol(table);
			// 字段值
			Object[] cols = fields.keySet().toArray();
			Object[] values = new Object[cols.length];
			for (int i = 0; i < cols.length; i++) {
				if (fields.get(cols[i]) == null) {
					values[i] = null;
				} else {
					values[i] = fields.get(cols[i]).toString();
				}
				cols[i] = quoteCol(cols[i].toString()) + "=?";
			}
			sql = "UPDATE " + table + " SET " + StringUtil.implode(", ", cols)
					+ " WHERE " + where;
			this.simpleJdbcTemplate.update(sql, values);
		} catch (Exception e) {
			throw new DBRuntimeException(e, sql);
		}
	}

	public String buildPageSql(String sql, int page, int pageSize) {
		String sql_str = null;
		sql_str = sql + " LIMIT " + (page - 1) * pageSize + "," + pageSize;
		return sql_str.toString();

	}
	
	/**
	 * 格式化列名 只适用于Mysql
	 * 
	 * @param col
	 * @return
	 */
	private String quoteCol(String col) {
		if (col == null || col.equals("")) {
			return "";
		} else {
			return col;
		}
	}

	/**
	 * 格式化值 只适用于Mysql
	 * 
	 * @param value
	 * @return
	 */
	private String quoteValue(String value) {
		if (value == null || value.equals("")) {
			return "''";
		} else {
			return "'" + value.replaceAll("'", "''") + "'";
		}
	}

	private String getStr(int num, String str) {
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < num; i++) {
			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * 去除sql的select 子句，未考虑union的情况,用于pagedQuery.
	 */
	private String removeSelect(String sql){
		sql=sql.toLowerCase();
		Pattern p = Pattern.compile("\\(.*\\)",Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(sql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			int c = m.end()-m.start();
			m.appendReplacement(sb, getStr(c,"~"));
		}
		m.appendTail(sb);
		String replacedSql = sb.toString();
		return sql.substring(replacedSql.indexOf("from"));
	}
	
	/**
	 * 去除hql的orderby 子句，用于pagedQuery.
	 */
	private String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
		this.simpleJdbcTemplate = simpleJdbcTemplate;
	}

}
