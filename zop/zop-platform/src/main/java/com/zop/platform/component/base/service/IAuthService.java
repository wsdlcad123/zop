/**
 *  Copyright 2010 ShenYang voole Co. Ltd.
 *  All right reserved.
 */
package com.zop.platform.component.base.service;
import com.zop.platform.component.base.bean.Auth;
import com.zop.platform.component.base.bean.Menu;

import java.util.List;


/**
 * 权限操作
 */
public interface IAuthService {
    /**
     * 保存
     */
    public void save(Auth auth);

    /**
     * 更新
     */
    public void update(Auth auth);

    /**
     * 获取
     */
    public Auth get(int id);

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
