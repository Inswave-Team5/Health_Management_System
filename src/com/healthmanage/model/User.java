package com.healthmanage.model;

public class User extends Person {
	private int coin;
	private int remainTime;
	
	public User(String userId, String password, String name) {
		super(name, password, userId);
		this.coin = 0;
		this.remainTime = 0;
	}
	

}
