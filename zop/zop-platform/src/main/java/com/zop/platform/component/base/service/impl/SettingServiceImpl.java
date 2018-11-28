package com.zop.platform.component.base.service.impl;



import com.zop.platform.component.base.bean.Setting;
import com.zop.platform.component.base.dao.ISettingDAO;
import com.zop.platform.component.base.service.ISettingService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SettingServiceImpl implements ISettingService {
    private ISettingDAO settingDAO;

    public void setSettingDAO(ISettingDAO settingDAO) {
        this.settingDAO = settingDAO;
    }

    /**
     * 保存
     */
    public void save(Setting setting){
        this.settingDAO.save(setting);
    }

    /**
     * 更新
     */
    public void update(Setting setting){
        this.settingDAO.update(setting);
    }

    /**
     * 更新全部
     */
    public void create(Map<String,String> setting_map){
        for(String key:setting_map.keySet()){
            Setting setting = this.get(key);
            if(setting == null){
                setting = new Setting();
                setting.setId(key);
                setting.setValue(setting_map.get(key));
                this.settingDAO.save(setting);
            }else{
                setting.setValue(setting_map.get(key));
                this.settingDAO.update(setting);
            }
        }
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id){
        this.settingDAO.delete(id);
    }

    /**
     * 获取
     */
    public Setting get(String id){
        return this.settingDAO.get(id);
    }

    /**
     * 列表
     */
    public List list(){
        return this.settingDAO.list();
    }
}