package com.zop.platform.component.base.action;

import com.zop.framework.action.BaseAction;
import com.zop.platform.component.base.bean.Auth;
import com.zop.platform.component.base.bean.Menu;
import com.zop.platform.component.base.service.IAuthService;
import com.zop.platform.component.base.service.IMenuService;
import com.zop.platform.component.base.util.MenuTreeUtil;
import com.zop.utils.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(AuthAction.class);

    private IAuthService authService;
    private IMenuService menuService;

    public void setAuthService(IAuthService authService) {
        this.authService = authService;
    }

    public void setMenuService(IMenuService menuService) {
        this.menuService = menuService;
    }

    private String menuTree;
    private List authList;
    private Auth auth;

    public String getMenuTree() {
        return menuTree;
    }

    public void setMenuTree(String menuTree) {
        this.menuTree = menuTree;
    }

    public List getAuthList() {
        return authList;
    }

    public void setAuthList(List authList) {
        this.authList = authList;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    private int id;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    private String auto_validate(){
        String error = "";
        if(StringUtil.isEmpty(this.auth.getName())){
            error = "名称不能为空";
        }
        return error;
    }


    /**
	 * 初始页
	 */
	public String index(){
        this.authList = this.authService.list("id","asc");
		return SUCCESS;
	}

    /**
     * 添加页面
     */
    public String add(){
        this.setAction("add_post");
        List<Menu> menuList = this.menuService.list("orders","asc");
        StringBuffer sb = new StringBuffer();
        for(Menu m:menuList){
            sb.append("{ id:"+m.getId()+", pId:"+m.getParent_id()+", name:\""+m.getName()+"\"},");
        }
        this.menuTree = sb.toString();
        this.menuTree = this.menuTree.substring(0, this.menuTree.length() - 1);
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
        this.authService.save(this.auth);
        this.setAjaxSuccess("添加成功！",getAjaxActionUrl("auth", "admin", ""));
        return RESULT_AJAXJSON;
    }

    public String edit(){
        this.setAction("edit_post");
        this.auth = this.authService.get(this.id);
        Map menuIdMap = getMenuId(this.auth.getMenu_value());
        List<Menu> menuList = this.menuService.list("orders","asc");
        StringBuffer sb = new StringBuffer();
        for(Menu m:menuList){
            sb.append("{ id:"+m.getId()+", pId:"+m.getParent_id()+", name:\""+m.getName()+"\""+getChecked(m.getId(),menuIdMap)+"},");
        }
        this.menuTree = sb.toString();
        this.menuTree = this.menuTree.substring(0, this.menuTree.length() - 1);
        return "edit";
    }

    public String edit_post(){
        String error = auto_validate();
        if(!StringUtil.isEmpty(error)){
            this.setAjaxError(error,"");
            return RESULT_AJAXJSON;
        }
        this.authService.update(this.auth);
        this.setAjaxSuccess("更新成功！","");
        return RESULT_AJAXJSON;
    }

    public String delete(){
        this.authService.delete(this.id);
        this.setAjaxSuccess("删除权限成功！","");
        return RESULT_AJAXJSON;
    }

    private String getChecked(int id,Map menuIdMap){
        String checkedId = (String) menuIdMap.get(String.valueOf(id));
        if(StringUtil.isEmpty(checkedId)){
            return "";
        }else{
            return ", checked:true";
        }
    }

    private Map getMenuId(String menuValue){
        String[] value = StringUtils.split(menuValue, ",");
        Map menuId = new HashMap();
        for(String v:value){
            menuId.put(v,v);
        }
        return menuId;
    }
}
