package com.zop.platform.component.base.service.impl;



import com.zop.platform.component.base.bean.Auth;
import com.zop.platform.component.base.bean.Role;
import com.zop.platform.component.base.dao.IAuthDAO;
import com.zop.platform.component.base.dao.IRoleDAO;
import com.zop.platform.component.base.service.IRoleService;
import com.zop.utils.util.StringUtil;

import java.util.List;

public class RoleServiceImpl implements IRoleService {
    private IRoleDAO roleDAO;
    private IAuthDAO authDAO;

    public void setRoleDAO(IRoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    public void setAuthDAO(IAuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    /**
     * 保存
     */
    public void save(Role role){
        this.roleDAO.save(role);
    }

    /**
     * 更新
     */
    public void update(Role role){
        this.roleDAO.update(role);
    }

    /**
     * 获取
     */
    public Role get(int id){
        Role role = this.roleDAO.get(id);
        if(role == null){
            return null;
        }else{
            if(!StringUtil.isEmpty(role.getAuth_value())) {
                role.setAuthList(this.authDAO.listInIds(role.getAuth_value()));
            }
        }
        return role;
    }

    /**
     * 删除
     * @param id
     */
    public void delete(int id){
        this.roleDAO.delete(id);
    }

    /**
     * 列表
     */
    public List list(String orderby, String ascOrDesc){
        return this.roleDAO.list(orderby,ascOrDesc);
    }
}