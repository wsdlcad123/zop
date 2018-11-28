/**
 *  Copyright 2010 ShenYang voole Co. Ltd.
 *  All right reserved.
 */
package com.zop.platform.component.base.service;
import com.zop.platform.component.base.bean.Admin;
import com.zop.platform.component.base.bean.Auth;

import java.util.List;


/**
 * 组件操作
 */
public interface IAdminService {
    /**
     * 通过username获取
     * @param username
     * @return
     */
    public Admin getByUsername(String username);

    /**
     * 登录
     * @param id
     */
    public void login(int id);

    /**
     * 保存
     */
    public void save(Admin admin);

    /**
     * 更新
     */
    public void update(Admin admin);

    /**
     * 获取
     */
    public Admin get(int id);

    /**
     * 删除
     * @param id
     */
    public void delete(int id);

    /**
     * 列表
     */
    public List list(String orderby, String ascOrDesc);
}
