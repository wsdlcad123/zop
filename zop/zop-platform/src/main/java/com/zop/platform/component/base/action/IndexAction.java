package com.zop.platform.component.base.action;

import com.zop.framework.action.BaseAction;
import com.zop.platform.component.base.bean.Admin;
import com.zop.platform.component.base.bean.Auth;
import com.zop.platform.component.base.bean.Menu;
import com.zop.platform.component.base.bean.Role;
import com.zop.platform.component.base.service.IAdminService;
import com.zop.platform.component.base.service.IAuthService;
import com.zop.platform.component.base.service.IMenuService;
import com.zop.platform.component.base.service.IRoleService;
import com.zop.utils.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class IndexAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(IndexAction.class);

    private IMenuService menuService;
    private IRoleService roleService;
    private IAuthService authService;

    public void setMenuService(IMenuService menuService) {
        this.menuService = menuService;
    }

    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    public void setAuthService(IAuthService authService) {
        this.authService = authService;
    }

    private List menuList;

    public List getMenuList() {
        return menuList;
    }

    public void setMenuList(List menuList) {
        this.menuList = menuList;
    }

    /**
	 * 初始页
	 */
	public String index(){
        List tempMenuList = this.menuService.list("orders","asc");
        Role role = this.roleService.get(2);
        List mList = new ArrayList();
        for(int i=0;i<tempMenuList.size();i++){
            Menu menu = (Menu) tempMenuList.get(i);
            if (!checkPermssion(menu, role.getAuthList())) {
                continue;
            }
            mList.add(menu);
        }
        this.menuList = getMenuList(mList);
		return SUCCESS;
	}


    private boolean checkPermssion(Menu menu, List<Auth> authList) {
        if(authList == null || authList.isEmpty()){
            return false;
        }
        //只做菜单
        if(menu.getType()==Menu.TYPE_MENU){
            return true;
        }
        //隐藏菜单
        if(menu.getStatus()==Menu.STATUS_HIDE){
            return false;
        }
        //在权限以内的
        for(Auth auth:authList){
            String values = auth.getMenu_value();
            if (values != null) {
                String[] value_ar = values.split(",");//StringUtils.split(values, ",");// values.split(",");
                for (String v : value_ar) {
                    if (v.equals(String.valueOf(menu.getId()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public List getMenuList( List menuList) {
        List mlist = new ArrayList();
        for (int i=0;i<menuList.size();i++) {
            Menu menu = (Menu) menuList.get(i);
            if ( menu.getParent_id() == 0) {
                List children = this.getChildren(menuList, menu.getId());
                menu.setChildren(children);
                mlist.add(menu);
            }
        }
        return mlist;
    }


    /*
* 在一个集合中查找子集合
*/
    private List getChildren(List menuList,int parent_id) {
        List children = new ArrayList();
        for(int i=0;i<menuList.size();i++){
            Menu menu = (Menu) menuList.get(i);
            if (menu.getParent_id()==parent_id) {
                menu.setChildren(this.getChildren(menuList, menu.getId()));
                children.add(menu);
            }
        }
        return children;
    }

}
