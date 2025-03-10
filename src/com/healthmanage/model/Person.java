package com.healthmanage.model;

public class Person {
	public String userId;
	public String name;
	public String password;
	
	Person(String name, String password, String userId){
		this.name = name;
		this.userId = userId;
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
