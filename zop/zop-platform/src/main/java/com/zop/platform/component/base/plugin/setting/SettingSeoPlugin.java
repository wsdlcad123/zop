package com.zop.platform.component.base.plugin.setting;

import com.zop.framework.plugin.AutoRegisterPlugin;
import com.zop.framework.plugin.Tab;
import com.zop.platform.component.base.bean.Setting;
import com.zop.platform.component.base.service.ISettingService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设置网站信息插件
 */
public class SettingSeoPlugin extends AutoRegisterPlugin implements ISettingOnInputEvent {
    private ISettingService settingService;

    public void setSettingService(ISettingService settingService) {
        this.settingService = settingService;
    }
    /**
     * 得到插件需要Tab
     */
    public Tab getTab(){
        Tab tab = new Tab();
        tab.setId("B");
        tab.setName("SEO设置");
        tab.setOrdres(2);
        tab.setUrl("../platform/plugin/setting_seo.jsp");
        tab.setActive("");
        return tab;
    }


    /**
     * 得到插件需要的数据
     * @return
     */
    public Map<String,Object> getData(){
        Map<String,Object> data = new HashMap<String,Object>();
        List l = this.settingService.list();
        if(l == null || l.isEmpty()){
            return data;
        }
        for(int i=0;i<l.size();i++){
            Setting s = (Setting) l.get(i);
            data.put(s.getId(),s.getValue());
        }
        return data;
    }
}