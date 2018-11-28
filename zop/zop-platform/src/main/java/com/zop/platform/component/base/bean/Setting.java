package com.zop.platform.component.base.bean;

import com.zop.utils.database.PrimaryKeyField;

import java.io.Serializable;

public class Setting implements Serializable {

	public Setting() {
	}

    private String id;                 //主键ID
    private String value;              //值

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
