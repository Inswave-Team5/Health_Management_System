package com.healthmanage.model;

public class Coupon {
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
		return "Coupon [쿠폰번호=" + number + ", 사용여부=" + isUsed + ", 보상코인=" + coinAmount + "]";
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
