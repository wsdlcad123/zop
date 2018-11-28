package com.zop.platform.component.base.dao.impl;


import com.zop.platform.component.base.bean.Admin;
import com.zop.platform.component.base.bean.Auth;
import com.zop.platform.component.base.bean.Menu;
import com.zop.platform.component.base.dao.IAdminDAO;
import com.zop.platform.component.base.dao.IAuthDAO;
import com.zop.utils.database.BaseJdbcDaoSupport;
import com.zop.utils.util.StringUtil;

import java.util.List;

public class AuthDAOImpl implements IAuthDAO {

    private BaseJdbcDaoSupport baseJdbcDaoSupport;

    public void setBaseJdbcDaoSupport(BaseJdbcDaoSupport baseJdbcDaoSupport) {
        this.baseJdbcDaoSupport = baseJdbcDaoSupport;
    }

    /**
     * 保存
     */
    public void save(Auth auth){
        this.baseJdbcDaoSupport.insert("auth",auth);
    }

    /**
     * 更新
     */
    public void update(Auth auth){
        this.baseJdbcDaoSupport.update("auth",auth,"id=" + auth.getId());
    }

    /**
     * 获取
     */
    public Auth get(int id){
        String sql = "select * from auth where id=?";
        List<Auth> l = this.baseJdbcDaoSupport.queryForList(sql, Auth.class, id);
        if(l==null || l.isEmpty()){
            return null;
        }else{
            return l.get(0);
        }
    }
    /**
     * 删除
     * @param id
     */
    public void delete(int id){
        String sql = "delete from auth where id=?";
        this.baseJdbcDaoSupport.execute(sql,id);
    }

    /**
     * 列表
     */
    public List list(String orderby, String ascOrDesc){
        StringBuffer sb = new StringBuffer();
        sb.append("select * from auth");
        if (!StringUtil.isEmpty(orderby)) {
            sb.append(" order by ");
            sb.append(orderby);
        }
        if (!StringUtil.isEmpty(ascOrDesc)) {
            sb.append(" " + ascOrDesc);
        }
        List<Auth> l = this.baseJdbcDaoSupport.queryForList(sb.toString(), Auth.class);
        if(l==null || l.isEmpty()){
            return null;
        }else{
            return l;
        }
    }

    /**
     * 列表
     * @param ids
     * @return
     */
    public List listInIds(String ids){
        String sql = "select * from auth where id in("+ids+")";
        List<Auth> l = this.baseJdbcDaoSupport.queryForList(sql, Auth.class);
        if(l==null || l.isEmpty()){
            return null;
        }else{
            return l;
        }
    }
}