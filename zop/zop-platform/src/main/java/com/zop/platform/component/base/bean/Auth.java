package com.zop.platform.component.base.bean;

import com.zop.utils.database.PrimaryKeyField;

import java.io.Serializable;

public class Auth implements Serializable {

	public Auth() {
	}

    private int id;				    //主键ID
    private String name;			//名称
    private String menu_value;		//菜单值

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

    public String getMenu_value() {
        return menu_value;
    }

    public void setMenu_value(String menu_value) {
        this.menu_value = menu_value;
    }
}
