package com.zop.test.component.dao.impl;


import com.zop.test.component.dao.IUserDAO;
import com.zop.utils.database.BaseJdbcDaoSupport;

public class UserDAOImpl implements IUserDAO {

    private BaseJdbcDaoSupport baseJdbcDaoSupport;

    public int getId(){
        return this.baseJdbcDaoSupport.queryForInt("select id from user where account='admin'");
    }

    public void setBaseJdbcDaoSupport(BaseJdbcDaoSupport baseJdbcDaoSupport) {
        this.baseJdbcDaoSupport = baseJdbcDaoSupport;
    }
}