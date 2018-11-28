/**
 *  Copyright 2010 ShenYang voole Co. Ltd.
 *  All right reserved.
 */
package com.zop.platform.component.base.service;
import com.zop.platform.component.base.bean.Component;

import java.util.List;

/**
 * 组件操作
 */
public interface IComponentService {

    public void start();

    public List list();

    public void install(String component_view_id);

    public void unInstall(String component_view_id);

    public void enable(String component_view_id);

    public void disable(String component_view_id);
}
