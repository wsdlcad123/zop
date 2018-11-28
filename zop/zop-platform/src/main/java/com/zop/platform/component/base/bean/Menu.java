package com.zop.platform.component.base.bean;

import com.zop.utils.database.NotDbField;
import com.zop.utils.database.PrimaryKeyField;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {

	public Menu() {
	}

    private int id;                 //主键ID
    private int parent_id;			//父ID
    private String component_view_id;    //组件视图ID
    private String url;             //地址
    private String name;           //名称
    private String icon;            //图标
    private int status;                 //状态
    private int type;                 //类型
    private int orders;				//排序
    private int depth;				//深度

    private List children;			//子菜单
    private boolean hasChildren;    //是否有子菜单

    public static final int STATUS_HIDE = 0;   //0:隐藏1:显示
    public static final int STATUS_SHOW = 1;

    public static final int TYPE_MENU = 0;   //0:菜单1:权限认证+菜单
    public static final int TYPE_AUTH = 1;

    @PrimaryKeyField
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getComponent_view_id() {
        return component_view_id;
    }

    public void setComponent_view_id(String component_view_id) {
        this.component_view_id = component_view_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @NotDbField
    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    @NotDbField
    public boolean getHasChildren() {
        hasChildren = this.children == null || this.children.isEmpty() ? false
                : true;
        return hasChildren;
    }
}
