package com.zop.platform.component.base.service.impl;



import com.zop.platform.component.base.bean.Auth;
import com.zop.platform.component.base.bean.Menu;
import com.zop.platform.component.base.dao.IAuthDAO;
import com.zop.platform.component.base.dao.IMenuDAO;
import com.zop.platform.component.base.service.IAuthService;
import com.zop.platform.component.base.service.IMenuService;

import java.util.ArrayList;
import java.util.List;

public class AuthServiceImpl implements IAuthService {
    private IAuthDAO authDAO;

    public void setAuthDAO(IAuthDAO authDAO) {
        this.authDAO = authDAO;
    }
    /**
     * 保存
     */
    public void save(Auth auth){
        this.authDAO.save(auth);
    }

    /**
     * 更新
     */
    public void update(Auth auth){
        this.authDAO.update(auth);
    }

    /**
     * 获取
     */
    public Auth get(int id){
        return this.authDAO.get(id);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(int id){
        this.authDAO.delete(id);
    }

    /**
     * 列表
     */
    public List list(String orderby, String ascOrDesc){
        return this.authDAO.list(orderby,ascOrDesc);
    }
}