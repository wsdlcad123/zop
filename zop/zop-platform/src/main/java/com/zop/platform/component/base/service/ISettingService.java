/**
 *  Copyright 2010 ShenYang voole Co. Ltd.
 *  All right reserved.
 */
package com.zop.platform.component.base.service;
import com.zop.platform.component.base.bean.Setting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 配置项操作
 */
public interface ISettingService {
    /**
     * 保存
     */
    public void save(Setting setting);

    /**
     * 更新
     */
    public void update(Setting setting);

    /**
     * 更新全部
     */
    public void create(Map<String, String> setting_map);

    /**
     * 获取
     */
    public Setting get(String id);

    /**
     * 删除
     * @param id
     */
    public void delete(String id);

    /**
     * 列表
     */
    public List list();


}
