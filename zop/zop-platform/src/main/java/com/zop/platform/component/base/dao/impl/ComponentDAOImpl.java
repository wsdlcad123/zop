package com.zop.platform.component.base.dao.impl;


import com.zop.platform.component.base.bean.Admin;
import com.zop.platform.component.base.bean.Component;
import com.zop.platform.component.base.dao.IComponentDAO;
import com.zop.utils.database.BaseJdbcDaoSupport;

import java.util.List;

public class ComponentDAOImpl implements IComponentDAO {

    private BaseJdbcDaoSupport baseJdbcDaoSupport;

    public void setBaseJdbcDaoSupport(BaseJdbcDaoSupport baseJdbcDaoSupport) {
        this.baseJdbcDaoSupport = baseJdbcDaoSupport;
    }

    /**
     * 保存
     */
    public void save(Component component){
        this.baseJdbcDaoSupport.insert("component",component);
    }

    /**
     * 更新
     */
    public void update(Component component){
        this.baseJdbcDaoSupport.update("component",component,"id=" + component.getId());
    }

    /**
     * 保存
     */
    public Component get(int id){
        String sql = "select * from component where id=?";
        List<Component> l = this.baseJdbcDaoSupport.queryForList(sql, Component.class, id);
        if(l==null || l.isEmpty()){
            return null;
        }else{
            return l.get(0);
        }
    }

    /**
     * 获取
     * @param component_view_id
     * @return
     */
    public Component getByViewId(String component_view_id){
        String sql = "select * from component where component_view_id=?";
        List<Component> l =  this.baseJdbcDaoSupport.queryForList(sql, Component.class, component_view_id);
        if(l==null || l.isEmpty()){
            return null;
        }else{
            return l.get(0);
        }
    }

    /**
     * 列表
     */
    public List list(){
        String sql = "select * from component";
        return this.baseJdbcDaoSupport.queryForList(sql,Component.class);
    }
}