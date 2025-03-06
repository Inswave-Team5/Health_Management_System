package com.healthmanage.service;

public class AdminService {
	private CouponService couponservice;
	public AdminService() {
		this.couponservice = CouponService.getInstance();
	}
	
	
	
	
	public void findAllCoupon() {
		couponservice.findAllCoupons();
	}
	
	public void addCoupon(String number, int coinAmount) {
		couponservice.createCoupon(number, coinAmount);
	}
	public void findCoupon(String number) {
		couponservice.findCoupon(number);
		
	}
}
