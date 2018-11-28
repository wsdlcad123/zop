package com.zop.platform.component.base.action;

import com.zop.framework.action.BaseAction;
import com.zop.platform.component.base.bean.Admin;
import com.zop.platform.component.base.bean.Role;
import com.zop.platform.component.base.service.IAdminService;
import com.zop.platform.component.base.service.IRoleService;
import com.zop.utils.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;

public class AdminAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(AdminAction.class);

    private IAdminService adminService;
    private IRoleService roleService;

    public void setAdminService(IAdminService adminService) {
        this.adminService = adminService;
    }
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }


    private List adminList;
    private Admin admin;
    private int id;

    public List getAdminList() {
        return adminList;
    }
    public void setAdminList(List adminList) {
        this.adminList = adminList;
    }
    public Admin getAdmin() {
        return admin;
    }
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    private String roleOption;

    public String getRoleOption() {
        return roleOption;
    }

    public void setRoleOption(String roleOption) {
        this.roleOption = roleOption;
    }

    private String auto_validate(){
        String error = "";
        if(StringUtil.isEmpty(this.admin.getUsername())){
            error = "用户名不能为空";
        }
        return error;
    }


    /**
	 * 初始页
	 */
	public String index(){
        this.adminList = this.adminService.list("id","desc");
		return SUCCESS;
	}

    /**
     * 添加页面
     */
    public String add(){
        this.setAction("add_post");
        List<Role> roleList = this.roleService.list("id","asc");
        StringBuffer sb = new StringBuffer();
        for(Role r:roleList){
            sb.append("<option value=\""+r.getId()+"\">"+r.getName()+"</option>");
        }
        this.roleOption = sb.toString();
        this.admin = new Admin();
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
        this.adminService.save(this.admin);
        this.setAjaxSuccess("添加成功！",getAjaxActionUrl("admin", "admin", ""));
        return RESULT_AJAXJSON;
    }

    public String edit(){
        this.setAction("edit_post");
        this.admin = this.adminService.get(this.id);
        List<Role> roleList = this.roleService.list("id","asc");
        StringBuffer sb = new StringBuffer();
        for(Role r:roleList){
            sb.append("<option value=\""+r.getId()+"\" "+getChecked(r.getId(),this.admin.getRole_id())+">"+r.getName()+"</option>");
        }
        this.roleOption = sb.toString();
        return "edit";
    }

    public String edit_post(){
        String error = auto_validate();
        if(!StringUtil.isEmpty(error)){
            this.setAjaxError(error,"");
            return RESULT_AJAXJSON;
        }
        this.adminService.update(this.admin);
        this.setAjaxSuccess("更新成功！","");
        return RESULT_AJAXJSON;
    }

    public String delete(){
        this.adminService.delete(this.id);
        this.setAjaxSuccess("管理员成功！","");
        return RESULT_AJAXJSON;
    }

    private String getChecked(int id,int checkId){
        if(id == checkId) {
            return " checked=\"checked\"";
        }
        return "";
    }
}
