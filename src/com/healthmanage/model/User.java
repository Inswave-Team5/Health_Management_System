package com.healthmanage.model;

public class User extends Person {
	private int coin;
	private int remainTime;
	
	public User(String name, String password, String userId) {
		super(name, password, userId);
		this.coin = 0;
		this.remainTime =0;
	}

}
