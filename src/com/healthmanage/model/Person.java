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
}
