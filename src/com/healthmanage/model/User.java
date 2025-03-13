package com.healthmanage.model;

import java.io.Serializable;

public class User extends Person implements Serializable{
	
	private int coin;
	private int remainTime;
	
	public User(String userId, String password, String name, String salt) {
		super(name, password, userId, salt);
		this.coin = 0;
		this.remainTime = 0;
	}
	public User(User user) {
		super(user.name, user.password, user.userId, user.salt);
		this.coin = 0;
		this.remainTime = 0;
	}
	
	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId +  ", name=" + name + ", coin=" + coin + ",  remainTime=" + remainTime +"]";
	}
}
