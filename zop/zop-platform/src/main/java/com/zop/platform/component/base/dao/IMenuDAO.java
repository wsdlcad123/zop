/**
 *  Copyright 2010 ShenYang voole Co. Ltd.
 *  All right reserved.
 */
package com.zop.platform.component.base.dao;

import com.zop.platform.component.base.bean.Admin;
import com.zop.platform.component.base.bean.Menu;

import java.util.List;

/**
 * 菜单操作
 */
public interface IMenuDAO {
	/**
	 * 保存
	 */
	public int save(Menu menu);

	/**
	 * 更新
	 */
	public void update(Menu menu);

    /**
     * 获取
     */
    public Menu get(int id);

    /**
     * 删除
     * @param id
     */
    public void delete(int id);

    /**
     * 个数
     * @param parent_id
     * @return
     */
    public int countByPid(int parent_id);

    /**
     * 列表
     */
    public List list(String orderby, String ascOrDesc);

    /**
     * 列表
     */
    public List listByPid(int parent_id, String orderby, String ascOrDesc);


}
