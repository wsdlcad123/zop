/**
 *  Copyright 2010 ShenYang voole Co. Ltd.
 *  All right reserved.
 */
package com.zop.platform.component.base.dao;
import com.zop.platform.component.base.bean.Component;

import java.util.List;

/**
 * 组件操作
 */
public interface IComponentDAO {
	/**
	 * 保存
	 */
	public void save(Component component);

	/**
	 * 更新
	 */
	public void update(Component component);

    /**
     * 获取
     */
    public Component get(int id);

    /**
     * 获取
     * @param component_view_id
     * @return
     */
    public Component getByViewId(String component_view_id);

    /**
     * 列表
     */
    public List list();


}
