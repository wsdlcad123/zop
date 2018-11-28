package com.zop.test.component.service.impl;

import com.zop.test.component.dao.IUserDAO;
import com.zop.test.component.service.IUserService;

public class UserServiceImpl implements IUserService {

    private IUserDAO userDAO;

    public int getId(){
        return this.userDAO.getId();
    }

    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }
}