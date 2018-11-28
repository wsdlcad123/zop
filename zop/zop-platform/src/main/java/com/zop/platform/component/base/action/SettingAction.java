package com.zop.platform.component.base.action;

import com.opensymphony.xwork2.ActionContext;
import com.zop.framework.action.BaseAction;
import com.zop.framework.plugin.Tab;
import com.zop.platform.component.base.plugin.setting.SettingPluginBundle;
import com.zop.platform.component.base.service.ISettingService;
import com.zop.utils.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(SettingAction.class);

    private ISettingService settingService;
    private SettingPluginBundle settingPluginBundle;

    public void setSettingService(ISettingService settingService) {
        this.settingService = settingService;
    }
    public void setSettingPluginBundle(SettingPluginBundle settingPluginBundle) {
        this.settingPluginBundle = settingPluginBundle;
    }

    private List<Tab> pluginTab;
    private Map pluginData;

    public List<Tab> getPluginTab() {
        return pluginTab;
    }
    public void setPluginTab(List<Tab> pluginTab) {
        this.pluginTab = pluginTab;
    }
    public Map getPluginData() {
        return pluginData;
    }
    public void setPluginData(Map pluginData) {
        this.pluginData = pluginData;
    }

    private String auto_validate(){
        String error = "";

        return error;
    }


    /**
	 * 站点信息
	 */
	public String index(){
        this.setAction("post");
        this.pluginTab = settingPluginBundle.getTabs();
        this.pluginData = settingPluginBundle.getDatas();
		return SUCCESS;
	}

    /**
     * 修改站点信息
     * @return
     */
    public String post(){
        ActionContext context = ActionContext.getContext();
        Map<String,Object> params = (Map<String,Object>)context.getParameters();
        Map setting_map = new HashMap();
        for(String key:params.keySet()){
            String[] value = (String[]) params.get(key);
            setting_map.put(key,value[0]);
            logger.info(key+":"+value[0]);
        }
        setting_map.remove("action");
        this.settingService.create(setting_map);
        this.setAjaxSuccess("更新成功！",getAjaxActionUrl("setting", "admin", ""));
        return RESULT_AJAXJSON;
    }

}
