package com.healthmanage.model;

import java.io.Serializable;

public class Coupon implements Serializable{
	private String number;
	private boolean isUsed;
	private int coinAmount;
	public Coupon(String number, int coinAmount){
		this.number = number;
		this.isUsed = false;
		this.coinAmount = coinAmount;
	}
	
	@Override
	public String toString() {
	    return String.format("│ %-10s │ %-6s │ %,8d 원 ",
	        number, (isUsed ? "✅ 사용됨" : "❌ 미사용"), coinAmount);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}


	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public int getCoinAmount() {
		return coinAmount;
	}

	public void setCoinAmount(int coinAmount) {
		this.coinAmount = coinAmount;
	}
}
