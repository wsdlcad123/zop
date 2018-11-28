package com.zop.platform.component.base.dao.impl;

import com.zop.platform.component.base.bean.Setting;
import com.zop.platform.component.base.dao.ISettingDAO;
import com.zop.utils.database.BaseJdbcDaoSupport;

import java.util.List;

public class SettingDAOImpl implements ISettingDAO {

    private BaseJdbcDaoSupport baseJdbcDaoSupport;

    public void setBaseJdbcDaoSupport(BaseJdbcDaoSupport baseJdbcDaoSupport) {
        this.baseJdbcDaoSupport = baseJdbcDaoSupport;
    }

    /**
     * 保存
     */
    public void save(Setting setting){
        this.baseJdbcDaoSupport.insert("setting",setting);
    }

    /**
     * 更新
     */
    public void update(Setting setting){
        String sql = "update setting set value=? where id=?";
        this.baseJdbcDaoSupport.execute(sql,setting.getValue(),setting.getId());
    }

    /**
     * 获取
     */
    public Setting get(String id){
        String sql = "select * from setting where id=?";
        List<Setting> l = this.baseJdbcDaoSupport.queryForList(sql, Setting.class, id);
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
    public void delete(String id){
        String sql = "delete from setting where id=?";
        this.baseJdbcDaoSupport.execute(sql,id);
    }

    /**
     * 列表
     */
    public List list(){
        String sql = ("select * from setting");
        List<Setting> l = this.baseJdbcDaoSupport.queryForList(sql, Setting.class);
        if(l==null || l.isEmpty()){
            return null;
        }else{
            return l;
        }
    }
}