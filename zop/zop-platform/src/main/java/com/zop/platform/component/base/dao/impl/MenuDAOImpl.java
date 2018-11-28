package com.zop.platform.component.base.dao.impl;

import com.zop.platform.component.base.bean.Menu;
import com.zop.platform.component.base.bean.Setting;
import com.zop.platform.component.base.dao.IMenuDAO;
import com.zop.utils.database.BaseJdbcDaoSupport;
import com.zop.utils.util.StringUtil;

import java.util.List;

public class MenuDAOImpl implements IMenuDAO {

    private BaseJdbcDaoSupport baseJdbcDaoSupport;

    public void setBaseJdbcDaoSupport(BaseJdbcDaoSupport baseJdbcDaoSupport) {
        this.baseJdbcDaoSupport = baseJdbcDaoSupport;
    }

    /**
     * 保存
     */
    public int save(Menu menu){
        this.baseJdbcDaoSupport.insert("menu",menu);
        return this.baseJdbcDaoSupport.getLastId("menu");
    }

    /**
     * 更新
     */
    public void update(Menu menu){
        this.baseJdbcDaoSupport.update("menu",menu,"id=" + menu.getId());
    }

    /**
     * 获取
     */
    public Menu get(int id){
        String sql = "select * from menu where id=?";
        List<Menu> l = this.baseJdbcDaoSupport.queryForList(sql, Menu.class, id);
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
        String sql = "delete from menu where id=?";
        this.baseJdbcDaoSupport.execute(sql,id);
    }

    /**
     * 个数
     * @param parent_id
     * @return
     */
    public int countByPid(int parent_id){
        String sql = "select count(0) from menu where parent_id=?";
        return this.baseJdbcDaoSupport.queryForInt(sql,parent_id);
    }

    /**
     * 列表
     */
    public List list(String orderby, String ascOrDesc){
        StringBuffer sb = new StringBuffer();
        sb.append("select * from menu");
        if (!StringUtil.isEmpty(orderby)) {
            sb.append(" order by ");
            sb.append(orderby);
        }
        if (!StringUtil.isEmpty(ascOrDesc)) {
            sb.append(" " + ascOrDesc);
        }
        List<Menu> l = this.baseJdbcDaoSupport.queryForList(sb.toString(), Menu.class);
        if(l==null || l.isEmpty()){
            return null;
        }else{
            return l;
        }
    }

    /**
     * 列表
     */
    public List listByPid(int parent_id,String orderby, String ascOrDesc){
        StringBuffer sb = new StringBuffer();
        sb.append("select * from menu");
        if (parent_id != -1) {
            sb.append(" where  parent_id=?");
        }
        if (!StringUtil.isEmpty(orderby)) {
            sb.append(" order by ");
            sb.append(orderby);
        }
        if (!StringUtil.isEmpty(ascOrDesc)) {
            sb.append(" " + ascOrDesc);
        }
        List<Menu> l = this.baseJdbcDaoSupport.queryForList(sb.toString(), Menu.class,parent_id);
        if(l==null || l.isEmpty()){
            return null;
        }else{
            return l;
        }
    }
}