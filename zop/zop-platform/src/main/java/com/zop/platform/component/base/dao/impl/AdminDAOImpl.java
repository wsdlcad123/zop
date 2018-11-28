package com.zop.platform.component.base.dao.impl;


import com.zop.platform.component.base.bean.Admin;
import com.zop.platform.component.base.bean.Auth;
import com.zop.platform.component.base.bean.Component;
import com.zop.platform.component.base.bean.Setting;
import com.zop.platform.component.base.dao.IAdminDAO;
import com.zop.utils.database.BaseJdbcDaoSupport;
import com.zop.utils.util.StringUtil;

import java.util.List;

public class AdminDAOImpl implements IAdminDAO {

    private BaseJdbcDaoSupport baseJdbcDaoSupport;

    public void setBaseJdbcDaoSupport(BaseJdbcDaoSupport baseJdbcDaoSupport) {
        this.baseJdbcDaoSupport = baseJdbcDaoSupport;
    }

    /**
     * 保存
     */
    public void save(Admin admin){
        this.baseJdbcDaoSupport.insert("admin",admin);
    }

    /**
     * 更新
     */
    public void update(Admin admin){
        this.baseJdbcDaoSupport.update("admin",admin,"id=" + admin.getId());
    }

    /**
     * 获取
     */
    public Admin get(int id){
        String sql = "select * from admin where id=?";
        List<Admin> l = this.baseJdbcDaoSupport.queryForList(sql, Admin.class, id);
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
        String sql = "delete from admin where id=?";
        this.baseJdbcDaoSupport.execute(sql,id);
    }

    /**
     * 通过username获取
     * @param username
     * @return
     */
    public Admin getByUsername(String username){
        String sql = "select * from admin where username=?";
        List<Admin> l = this.baseJdbcDaoSupport.queryForList(sql, Admin.class, username);
        if(l==null || l.isEmpty()){
            return null;
        }else{
            return l.get(0);
        }
    }

    /**
     * 列表
     */
    public List list(String orderby, String ascOrDesc){
        StringBuffer sb = new StringBuffer();
        sb.append("select * from admin");
        if (!StringUtil.isEmpty(orderby)) {
            sb.append(" order by ");
            sb.append(orderby);
        }
        if (!StringUtil.isEmpty(ascOrDesc)) {
            sb.append(" " + ascOrDesc);
        }
        List<Admin> l = this.baseJdbcDaoSupport.queryForList(sb.toString(), Admin.class);
        if(l==null || l.isEmpty()){
            return null;
        }else{
            return l;
        }
    }
}