package com.healthmanage.model;

import java.io.Serializable;

public class Person implements Serializable{
	public String userId;
	public String name;
	public String password;
	
	Person(String name, String password, String userId){
		this.name = name;
		this.userId = userId;
		this.password = password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	@Override
	public String toString() {
		return "[ userId=" + userId + ", name=" + name + ", password=" + password + "]";
	}
	
}
