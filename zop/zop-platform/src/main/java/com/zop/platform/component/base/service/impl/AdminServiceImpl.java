package com.zop.platform.component.base.service.impl;


import com.zop.platform.component.base.bean.Admin;
import com.zop.platform.component.base.bean.Auth;
import com.zop.platform.component.base.bean.Role;
import com.zop.platform.component.base.dao.IAdminDAO;
import com.zop.platform.component.base.dao.IAuthDAO;
import com.zop.platform.component.base.dao.IRoleDAO;
import com.zop.platform.component.base.service.IAdminService;
import com.zop.utils.util.StringUtil;
import com.zop.webutils.context.ThreadContextHolder;
import com.zop.webutils.context.WebSessionContext;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminServiceImpl implements IAdminService {
    private IAdminDAO adminDAO;
    private IRoleDAO roleDAO;
    private IAuthDAO authDAO;


    public void setAdminDAO(IAdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }
    public void setRoleDAO(IRoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }
    public void setAuthDAO(IAuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    /**
     * 通过username获取
     * @param username
     * @return
     */
    public Admin getByUsername(String username){
        return this.adminDAO.getByUsername(username);
    }

    /**
     * 登录
     * @param id
     */
    public void login(int id){
        Admin a = this.get(id);
        // 记录session信息
        WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
        sessonContext.setAttribute("admin_id",a.getId());
        sessonContext.setAttribute("admin_role_id",a.getRole_id());
        sessonContext.setAttribute("admin_menu_ids",getMenuId(a.getRole_id()));

    }

    private Map getMenuId(int roleId){
        Map menuId = new HashMap();
        Role role = this.roleDAO.get(roleId);
        if(role != null){
            if(!StringUtil.isEmpty(role.getAuth_value())) {
                List<Auth> authList = this.authDAO.listInIds(role.getAuth_value());
                for(Auth a:authList){
                    String[] value = StringUtils.split(a.getMenu_value(), ",");
                    for(String v:value){
                        menuId.put(v,v);
                    }
                }
            }
        }
        return menuId;
    }

    /**
     * 保存
     */
    public void save(Admin admin){
        this.adminDAO.save(admin);
    }

    /**
     * 更新
     */
    public void update(Admin admin){
        this.adminDAO.update(admin);
    }

    /**
     * 获取
     */
    public Admin get(int id){
        return this.adminDAO.get(id);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(int id){
        this.adminDAO.delete(id);
    }

    /**
     * 列表
     */
    public List list(String orderby, String ascOrDesc){
        return this.adminDAO.list(orderby,ascOrDesc);
    }
}