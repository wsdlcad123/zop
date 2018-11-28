package com.zop.platform.component.base.action;

import com.zop.framework.action.BaseAction;
import com.zop.platform.component.base.bean.Menu;
import com.zop.platform.component.base.service.IMenuService;
import com.zop.platform.component.base.util.MenuTreeUtil;
import com.zop.utils.util.ReflectionUtil;
import com.zop.utils.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.dispatcher.mapper.ActionMapper;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.dispatcher.mapper.DefaultActionMapper;

import java.util.*;

public class MenuAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(MenuAction.class);

    private IMenuService menuService;

    public void setMenuService(IMenuService menuService) {
        this.menuService = menuService;
    }

    private Menu menu;
    private int parent_id;
    private int id;

    public Menu getMenu() {
        return menu;
    }
    public void setMenu(Menu menu) {
        this.menu = menu;
    }
    public int getParent_id() {
        return parent_id;
    }
    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    private String menuTree;

    public String getMenuTree() {
        return menuTree;
    }

    public void setMenuTree(String menuTree) {
        this.menuTree = menuTree;
    }

    private String auto_validate(){
        String error = "";
        if(StringUtil.isEmpty(this.menu.getName())){
            error = "菜单名称不能为空";
        }
        if(StringUtil.isEmpty(this.menu.getComponent_view_id())){
            error = "组件视图不能为空";
        }
        if(this.menu.getParent_id()!=0) {
            Menu m = this.menuService.get(menu.getParent_id());
            if(m != null){
                if(m.getDepth()==4){
                    error = "菜单只支持四级";
                }
            }
        }
        return error;
    }


    /**
	 * 初始页
	 */
	public String index(){
        List<Menu> menuTreeList = this.menuService.listTreeByPid(0,"orders","asc");
        String manage = "<a href=\""+getActionUrl("menu","admin","action=add&parent_id={id}")+"\">添加子菜单</a> | <a href=\""+getActionUrl("menu","admin","action=edit&id={id}")+"\">修改</a> | <a class=\"J_ajax_del\" href=\""+getActionUrl("menu","admin","action=delete&id={id}")+"\">删除</a>";
        StringBuffer sb = new StringBuffer();
        sb.append("<tr>");
        sb.append("<td>{id}</td>");
        sb.append("<td>{component_view_id}</td>");
        sb.append("<td>{url}</td>");
        sb.append("<td>{spacer}{name}</td>");
        sb.append("<td>{status}</td>");
        sb.append("<td>"+manage+"</td>");
        sb.append("</tr>");

        MenuTreeUtil menuTreeUtil = new MenuTreeUtil(menuTreeList,sb.toString());
        menuTreeUtil.setIcon(Arrays.asList("&nbsp;&nbsp;&nbsp;│ ", "&nbsp;&nbsp;&nbsp;├─ ", "&nbsp;&nbsp;&nbsp;└─ "));
        this.menuTree = menuTreeUtil.getTree(0);
		return SUCCESS;
	}

    /**
     * 添加页面
     */
    public String add(){
        this.setAction("add_post");
        this.menu = new Menu();
        List<Menu> menuTreeList = this.menuService.listTreeByPid(0,"orders","asc");
        String template = "<option value='{id}' {selected}>{spacer} {name}</option>";
        MenuTreeUtil menuTreeUtil = new MenuTreeUtil(menuTreeList,template);
        this.menuTree = menuTreeUtil.getTree(this.parent_id);
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
        this.menuService.save(this.menu);
        this.setAjaxSuccess("添加成功！",getAjaxActionUrl("menu", "admin", ""));
        return RESULT_AJAXJSON;
    }

    public String edit(){
        this.setAction("edit_post");
        this.menu = this.menuService.get(this.id);
        List<Menu> menuTreeList = this.menuService.listTreeByPid(0,"orders","asc");
        String template = "<option value='{id}' {selected}>{spacer} {name}</option>";
        MenuTreeUtil menuTreeUtil = new MenuTreeUtil(menuTreeList,template);
        this.menuTree = menuTreeUtil.getTree(this.menu.getParent_id());
        return "edit";
    }

    public String edit_post(){
        String error = auto_validate();
        if(!StringUtil.isEmpty(error)){
            this.setAjaxError(error,"");
            return RESULT_AJAXJSON;
        }
        this.menuService.update(this.menu);
        this.setAjaxSuccess("更新成功！","");
        return RESULT_AJAXJSON;
    }

    public String delete(){
        int count = this.menuService.countByPid(this.id);
        if(count>0){
            this.setAjaxError("该菜单下还有子菜单，无法删除！","");
            return RESULT_AJAXJSON;
        }
        this.menuService.delete(this.id);
        this.setAjaxSuccess("删除菜单成功！","");
        return RESULT_AJAXJSON;
    }
}
