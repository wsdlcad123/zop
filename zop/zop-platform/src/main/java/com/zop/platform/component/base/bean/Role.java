package com.zop.platform.component.base.bean;

import com.zop.utils.database.NotDbField;
import com.zop.utils.database.PrimaryKeyField;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable {

	public Role() {
	}

    private int id;				    //主键ID
    private String name;			//名称
    private String auth_value;		//菜单值

    private List authList;          //权限列表

    @PrimaryKeyField
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuth_value() {
        return auth_value;
    }

    public void setAuth_value(String auth_value) {
        this.auth_value = auth_value;
    }

    @NotDbField
    public List getAuthList() {
        return authList;
    }

    public void setAuthList(List authList) {
        this.authList = authList;
    }
}
