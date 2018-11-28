package com.zop.platform.component.base.action;

import com.zop.framework.action.BaseAction;
import com.zop.platform.component.base.bean.Auth;
import com.zop.platform.component.base.bean.Menu;
import com.zop.platform.component.base.bean.Role;
import com.zop.platform.component.base.service.IAuthService;
import com.zop.platform.component.base.service.IMenuService;
import com.zop.platform.component.base.service.IRoleService;
import com.zop.utils.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(RoleAction.class);

    private IRoleService roleService;
    private IAuthService authService;

    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    public void setAuthService(IAuthService authService) {
        this.authService = authService;
    }

    private String authCheckbox;
    private List roleList;
    private Role role;

    public String getAuthCheckbox() {
        return authCheckbox;
    }

    public void setAuthCheckbox(String authCheckbox) {
        this.authCheckbox = authCheckbox;
    }

    public List getRoleList() {
        return roleList;
    }

    public void setRoleList(List roleList) {
        this.roleList = roleList;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    private int id;
    private Integer[] authId;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Integer[] getAuthId() {
        return authId;
    }

    public void setAuthId(Integer[] authId) {
        this.authId = authId;
    }

    private String auto_validate(){
        String error = "";
        if(StringUtil.isEmpty(this.role.getName())){
            error = "名称不能为空";
        }
        return error;
    }


    /**
	 * 初始页
	 */
	public String index(){
        this.roleList = this.roleService.list("id","asc");
		return SUCCESS;
	}

    /**
     * 添加页面
     */
    public String add(){
        this.setAction("add_post");
        List<Auth> authList = this.authService.list("id","asc");
        StringBuffer sb = new StringBuffer();
        for(Auth a:authList){
            sb.append("<input type=\"checkbox\" value=\""+a.getId()+"\" name=\"authId\"> "+a.getName()+"<br />");
        }
        this.authCheckbox = sb.toString();
        return "add";
    }

    /**
     * 添加处理
     * @return
     */
    public String add_post(){
        String error = auto_validate();
        if(!StringUtil.isEmpty(error)){
            this.setAjaxError(error,"");
            return RESULT_AJAXJSON;
        }
        this.role.setAuth_value(StringUtil.arrayToString(this.authId, ","));
        this.roleService.save(this.role);
        this.setAjaxSuccess("添加成功！",getAjaxActionUrl("role", "admin", ""));
        return RESULT_AJAXJSON;
    }

    public String edit(){
        this.setAction("edit_post");
        this.role = this.roleService.get(this.id);
        Map authIdMap = getAuthId(this.role.getAuth_value());
        List<Auth> authList = this.authService.list("id","asc");
        StringBuffer sb = new StringBuffer();
        for(Auth a:authList){
            sb.append("<input type=\"checkbox\" value=\""+a.getId()+"\" name=\"authId\" "+getChecked(a.getId(),authIdMap)+"> "+a.getName()+"<br />");
        }
        this.authCheckbox = sb.toString();
        return "edit";
    }

    public String edit_post(){
        String error = auto_validate();
        if(!StringUtil.isEmpty(error)){
            this.setAjaxError(error,"");
            return RESULT_AJAXJSON;
        }
        this.role.setAuth_value(StringUtil.arrayToString(this.authId, ","));
        this.roleService.update(this.role);
        this.setAjaxSuccess("更新成功！","");
        return RESULT_AJAXJSON;
    }

    public String delete(){
        this.roleService.delete(this.id);
        this.setAjaxSuccess("删除权限成功！","");
        return RESULT_AJAXJSON;
    }

    private String getChecked(int id,Map authIdMap){
        String checkedId = (String) authIdMap.get(String.valueOf(id));
        if(StringUtil.isEmpty(checkedId)){
            return "";
        }else{
            return " checked=\"checked\"";
        }
    }

    private Map getAuthId(String authValue){
        String[] value = StringUtils.split(authValue, ",");
        Map menuId = new HashMap();
        for(String v:value){
            menuId.put(v,v);
        }
        return menuId;
    }
}
