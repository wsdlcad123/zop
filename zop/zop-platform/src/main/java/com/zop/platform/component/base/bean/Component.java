package com.zop.platform.component.base.bean;

import com.zop.utils.database.PrimaryKeyField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Component implements Serializable {

	public Component() {
	}

    private int id;                 //主键ID
    private String component_view_id;    //组件视图ID
	private String name;            //名称
    private String version;         //版本
    private String author;          //作者
    private int install_status;     //0未安装 1已安装
    private int enable_status;      // 0未启用 1已启用

    @PrimaryKeyField
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComponent_view_id() {
        return component_view_id;
    }

    public void setComponent_view_id(String component_view_id) {
        this.component_view_id = component_view_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getInstall_status() {
        return install_status;
    }

    public void setInstall_status(int install_status) {
        this.install_status = install_status;
    }

    public int getEnable_status() {
        return enable_status;
    }

    public void setEnable_status(int enable_status) {
        this.enable_status = enable_status;
    }
}
