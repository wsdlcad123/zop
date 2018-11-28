package com.zop.utils.database;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zop.utils.database.impl.JdbcDaoSupport;
import com.zop.utils.util.ReflectionUtil;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

/**
 * 数据库操作类，包装了JdbcDaoSupport<br/>
 * 自动设置的表名
 * 
 * @author kingapex 2010-1-10下午07:30:59
 * @param <T>
 */
public class BaseJdbcDaoSupport<T> extends JdbcDaoSupport<T> {
	private IDBRouter dbRouter;

	public void setDbRouter(IDBRouter dbRouter) {
		this.dbRouter = dbRouter;
	}

    /**
     * 插入数据
     * @param table
     * @param po
     */
	public void insert(String table, Object po) {
		Map poMap = ReflectionUtil.po2Map(po);
		table = this.dbRouter.getTableName(table);
		super.insert(table, poMap);
	}

    public void insert(String table, Map fields) {
        table = this.dbRouter.getTableName(table);
        super.insert(table, fields);
    }

    /**
     * 执行操作
     * @param sql
     * @param args
     */
	public void execute(String sql, Object... args) {
		sql = wrapExeSql(sql);
		super.execute(sql, args);
	}

    /**
     * 获取最后插入ID
     * @param table
     * @return
     */
	public int getLastId(String table) {
		table = dbRouter.getTableName(table);
		return super.getLastId(table);
	}


	public int queryForInt(String sql, Object... args) {
		sql = wrapSelSql(sql);
		return super.queryForInt(sql, args);
	}

    public long queryForLong(String sql, Object... args) {
        sql = wrapSelSql(sql);
        return super.queryForLong(sql, args);
    }

    public String queryForString(String sql) {
        sql = wrapSelSql(sql);
        return super.queryForString(sql);
    }

	public List<Map> queryForList(String sql, Object... args) {
		sql = wrapSelSql(sql);
		return super.queryForList(sql, args);
	}

	public List<T> queryForList(String sql, RowMapper mapper, Object... args) {
		sql = wrapSelSql(sql);
		return super.queryForList(sql, mapper, args);
	}

	public List<T> queryForList(String sql, Class mappedClass, Object... args) {
		sql = wrapSelSql(sql);
		return super.queryForList(sql, mappedClass, args);
	}

	public List<Map> queryForListPage(String sql, int pageNo, int pageSize,
			Object... args) {
		sql = wrapSelSql(sql);
		return super.queryForListPage(sql, pageNo, pageSize, args);
	}

	public List<T> queryForListPage(String sql, int pageNo, int pageSize,
			RowMapper mapper) {
		sql = wrapSelSql(sql);
		return super.queryForList(sql, pageNo, pageSize, mapper);
	}


	public Map queryForMap(String sql, Object... args) {
		sql = wrapSelSql(sql);
		return super.queryForMap(sql, args);
	}

	public T queryForObject(String sql, Class mappedClass, Object... args) {
		sql = wrapSelSql(sql);
		// System.out.println(sql);
		return super.queryForObject(sql, mappedClass, args);
	}

	public T queryForObject(String sql, ParameterizedRowMapper mapper,
			Object... args) {
		sql = wrapSelSql(sql);
		return super.queryForObject(sql, mapper, args);
	}

	public Page queryForPage(String sql, int pageNo, int pageSize,
			Object... args) {
		sql = wrapSelSql(sql);
		return super.queryForPage(sql, pageNo, pageSize, args);
	}

	public Page queryForPage(String sql, int pageNo, int pageSize,
			RowMapper rowMapper, Object... args) {
		sql = wrapSelSql(sql);
		return super.queryForPage(sql, pageNo, pageSize, rowMapper, args);
	}

	public Page queryForPage(String sql, int pageNo, int pageSize,
			Class<T> mappedClass, Object... args) {
		sql = wrapSelSql(sql);
		return super.queryForPage(sql, pageNo, pageSize, mappedClass, args);
	}

	public void update(String table, Map fields, Map where) {
		table = this.dbRouter.getTableName(table);
		super.update(table, fields, where);
	}

	public void update(String table, Map fields, String where) {
		table = this.dbRouter.getTableName(table);
		super.update(table, fields, where);
	}

	public void update(String table, T po, Map where) {
		table = this.dbRouter.getTableName(table);
		super.update(table, po, where);
	}

	public void update(String table, T po, String where) {
		table = this.dbRouter.getTableName(table);
		super.update(table, po, where);
	}

    /**
     * 替换表名
     * @param sql
     * @return
     */
	public String wrapExeSql(String sql) {
		sql=sql.toLowerCase();
		String pattern;
		if (sql.indexOf("update") >= 0) {
			pattern = "(update\\s+)(\\w+)(.+)";

		} else if (sql.indexOf("delete") >= 0) {
			pattern = "(delete\\s+from\\s+)(\\w+)(.+)";
		} else if (sql.indexOf("insert") >= 0) {
			pattern = "(insert\\s+into\\s+)(\\w+)(.+)";
		} else if (sql.indexOf("truncate") >= 0) {
			pattern = "(truncate\\s+table\\s+)(\\w+)(.*?)";
		} else {
			return sql;
		}
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(sql);
		if (m.find()) {
			String tname = m.group(2);
			sql = m.replaceAll("$1 " + this.dbRouter.getTableName(tname)
					+ " $3");
		}
		return sql;
	}

    /**
     * 将select语句包装为相应的saas sql
     *
     * @param sql
     * @return
     */
    public String wrapSelSql(String sql) {
        sql = rpSelTbName(sql);
        return sql;
    }
    /**
     * 替换join句里的表名
     *
     * @param sql
     * @return
     */
    public String rpJoinTbName(String sql) {

        String pattern = "(join\\s+)(\\w+)(\\s+)";
        Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
        Matcher m = p.matcher(sql);
        StringBuffer sb = new StringBuffer();

        if (m.find()) {
            String tname = m.group(2);

            m.appendReplacement(sb, "join " + this.dbRouter.getTableName(tname)
                    + " ");
        }
        m.appendTail(sb);

        return sb.toString();
    }


    /**
     * 替换from句里的表名
     *
     * @param sql
     * @return
     */
    public String rpFromTbName(String sql) {

        String pattern = "(from\\s+)(\\w+)(\\s*)";
        Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
        Matcher m = p.matcher(sql);
        StringBuffer sb = new StringBuffer();

        if (m.find()) {
            String tname = m.group(2);
            m.appendReplacement(sb, "from " + this.dbRouter.getTableName(tname)
                    + " ");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 替换select语句里的表名
     *
     * @param sql
     * @return
     */
    public String rpSelTbName(String sql) {
        sql = rpJoinTbName(sql);
        sql = rpFromTbName(sql);
        return sql;
    }


	public static void main(String args[]) {

	}


}
