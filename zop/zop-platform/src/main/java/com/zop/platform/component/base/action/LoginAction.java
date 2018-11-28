package com.zop.platform.component.base.action;

import com.zop.framework.action.BaseAction;
import com.zop.platform.component.base.bean.Admin;
import com.zop.platform.component.base.service.IAdminService;
import com.zop.utils.util.StringUtil;
import com.zop.webutils.context.ThreadContextHolder;
import com.zop.webutils.context.WebSessionContext;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoginAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(LoginAction.class);
    private IAdminService adminService;

    public void setAdminService(IAdminService adminService) {
        this.adminService = adminService;
    }

    private String username;		//用户名
    private String password;		//密码
	private String verifycode;      //验证码

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getVerifycode() {
        return verifycode;
    }
    public void setVerifycode(String verifycode) {
        this.verifycode = verifycode;
    }


	/**
	 * 初始页
	 */
	public String index(){
		this.setAction("login");
		return "login";
	}

    public String login(){
        if(StringUtils.isBlank(this.username)){
            this.setAjaxError("用户名不能为空！","");
            return RESULT_AJAXJSON;
        }
        if(StringUtils.isBlank(this.password)){
            this.setAjaxError("密码不能为空！","");
            return RESULT_AJAXJSON;
        }
        //验证码正确
        if(!getSessionAuthCode().equalsIgnoreCase(this.verifycode)){
            this.setAjaxError("验证码错误！", "");
            return RESULT_AJAXJSON;
        }
        Admin a = this.adminService.getByUsername(this.username);
        if(a == null) {
            this.setAjaxError("用户名不存在！","");
            return RESULT_AJAXJSON;
        }
        //判断密码
        if(!StringUtil.md5(this.password).equalsIgnoreCase(a.getPassword())){
            this.setAjaxError("密码错误！", "");
            return RESULT_AJAXJSON;
        }
        //状态
        if(Admin.STATUS_VALIDATE != a.getStatus()){
            this.setAjaxError("用户未通过审核！", "");
            return RESULT_AJAXJSON;
        }
        //登录操作
        this.adminService.login(a.getId());

        this.setAjaxSuccess("登录验证成功！",getAjaxActionUrl("index","admin",""));
        return RESULT_AJAXJSON;
    }

    private String getSessionAuthCode() {
        String auth = (String) this.getSession("authCode");
        if(StringUtils.isBlank(auth)){
            return "";
        }else{
            return auth;
        }
    }
}
