package com.zop.platform.component.base.bean;

import com.zop.utils.database.PrimaryKeyField;

import java.io.Serializable;

public class Admin  implements Serializable {

	public Admin() {
	}

    private int id;                 //主键ID
    private String username;        //用户名
	private String password;        //密码
    private int status;             //状态
    private int role_id;         //角色ID
    private String last_login_ip;   //最后登录IP
    private long last_login_time;   // 最后登录时间
    private long add_time;          // 添加时间

    public static final int STATUS_VERIFY = 0;   //0:待审核1:己通过
    public static final int STATUS_VALIDATE = 1;

    @PrimaryKeyField
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getLast_login_ip() {
        return last_login_ip;
    }

    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }

    public long getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(long last_login_time) {
        this.last_login_time = last_login_time;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }
}
