package com.zop.platform.component.base.dao.impl;


import com.zop.platform.component.base.bean.Auth;
import com.zop.platform.component.base.bean.Role;
import com.zop.platform.component.base.dao.IAuthDAO;
import com.zop.platform.component.base.dao.IRoleDAO;
import com.zop.utils.database.BaseJdbcDaoSupport;
import com.zop.utils.util.StringUtil;

import java.util.List;

public class RoleDAOImpl implements IRoleDAO {

    private BaseJdbcDaoSupport baseJdbcDaoSupport;

    public void setBaseJdbcDaoSupport(BaseJdbcDaoSupport baseJdbcDaoSupport) {
        this.baseJdbcDaoSupport = baseJdbcDaoSupport;
    }

    /**
     * 保存
     */
    public void save(Role role){
        this.baseJdbcDaoSupport.insert("role",role);
    }

    /**
     * 更新
     */
    public void update(Role role){
        this.baseJdbcDaoSupport.update("role",role,"id=" + role.getId());
    }

    /**
     * 获取
     */
    public Role get(int id){
        String sql = "select * from role where id=?";
        List<Role> l = this.baseJdbcDaoSupport.queryForList(sql, Role.class, id);
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
        String sql = "delete from role where id=?";
        this.baseJdbcDaoSupport.execute(sql,id);
    }

    /**
     * 列表
     */
    public List list(String orderby, String ascOrDesc){
        StringBuffer sb = new StringBuffer();
        sb.append("select * from role");
        if (!StringUtil.isEmpty(orderby)) {
            sb.append(" order by ");
            sb.append(orderby);
        }
        if (!StringUtil.isEmpty(ascOrDesc)) {
            sb.append(" " + ascOrDesc);
        }
        List<Role> l = this.baseJdbcDaoSupport.queryForList(sb.toString(), Role.class);
        if(l==null || l.isEmpty()){
            return null;
        }else{
            return l;
        }
    }
}