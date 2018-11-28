/**
 *  Copyright 2010 ShenYang voole Co. Ltd.
 *  All right reserved.
 */
package com.zop.platform.component.base.dao;

import com.zop.platform.component.base.bean.Auth;
import com.zop.platform.component.base.bean.Role;

import java.util.List;

/**
 * 角色操作
 */
public interface IRoleDAO {
	/**
	 * 保存
	 */
	public void save(Role role);

	/**
	 * 更新
	 */
	public void update(Role role);

    /**
     * 获取
     */
    public Role get(int id);

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
