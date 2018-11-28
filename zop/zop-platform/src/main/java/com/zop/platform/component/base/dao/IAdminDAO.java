/**
 *  Copyright 2010 ShenYang voole Co. Ltd.
 *  All right reserved.
 */
package com.zop.platform.component.base.dao;
import com.zop.platform.component.base.bean.Admin;
import java.util.List;

/**
 * 管理员操作
 */
public interface IAdminDAO {
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
     * 通过username获取
     * @param username
     * @return
     */
    public Admin getByUsername(String username);

    /**
     * 列表
     */
    public List list(String orderby, String ascOrDesc);


}
