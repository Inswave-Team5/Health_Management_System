package com.healthmanage.model;

import java.io.Serializable;

public class Person implements Serializable{
	private String userId;
	private String name;
	private String password;
	private String salt;

	Person(String name, String password, String userId, String salt) {
		this.name = name;
		this.userId = userId;
		this.password = password;
		this.salt = salt;
	}

	public void setPassword(String password, String salt) {
		this.password = password;
		this.salt = salt;
	}

	public String getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getSalt() {
		return salt;
	}	

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "[ userId=" + userId + ", name=" + name + ", password=" + password + " ]";
	}
}
