package com.zop.platform.component.base.action;

import com.opensymphony.xwork2.ActionContext;
import com.zop.framework.action.BaseAction;
import com.zop.framework.plugin.Tab;
import com.zop.platform.component.base.plugin.setting.SettingPluginBundle;
import com.zop.platform.component.base.service.IComponentService;
import com.zop.platform.component.base.service.ISettingService;
import com.zop.utils.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Map;

public class ComponentAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(ComponentAction.class);

    private IComponentService componentService;

    public void setComponentService(IComponentService componentService) {
        this.componentService = componentService;
    }

    private List componentList;

    public List getComponentList() {
        return componentList;
    }

    public void setComponentList(List componentList) {
        this.componentList = componentList;
    }

    private String view_id;

    public String getView_id() {
        return view_id;
    }

    public void setView_id(String view_id) {
        this.view_id = view_id;
    }

    /**
     * 初始页
     */
    public String index(){
        this.componentList = this.componentService.list();
        return SUCCESS;
    }

    public String install(){
        this.componentService.install(this.view_id);
        this.setAjaxSuccess("安装成功","");
        return RESULT_AJAXJSON;
    }
    public String un_install(){
        this.componentService.unInstall(this.view_id);
        this.setAjaxSuccess("卸载成功","");
        return RESULT_AJAXJSON;
    }
    public String enable(){
        this.componentService.enable(this.view_id);
        this.setAjaxSuccess("启用成功","");
        return RESULT_AJAXJSON;
    }
    public String disable(){
        this.componentService.disable(this.view_id);
        this.setAjaxSuccess("停用成功","");
        return RESULT_AJAXJSON;
    }
}
