package com.healthmanage.model;

public class Coupon {
	private String number;
	private boolean isUsed;
	private int coinAmount;
	Coupon(String number, int coinAmount){
		this.number = number;
		this.isUsed = false;
		this.coinAmount = coinAmount;
	}
}
