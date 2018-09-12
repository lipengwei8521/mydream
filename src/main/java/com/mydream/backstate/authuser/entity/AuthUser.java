package com.mydream.backstate.authuser.entity;

/**
 * 用户表
 * @author LiPengWei
 * @date 2018-09-06
 */
public class AuthUser {
	
	// id
	private int id;
	// 姓名
	private String  name;
	// 用户名
	private String username;
	// 密码
	private String password;
	// 创建时间
	private String createTime;
	// 更新时间
	private String updateTime;
	
	// 空参构造函数
	public AuthUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	// 除id外构造函数
	public AuthUser(int id, String name, String username, String password, String createTime, String updateTime) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	
	// set和get方法
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	// toString方法
	@Override
	public String toString() {
		return "AuthUser [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
	
}
