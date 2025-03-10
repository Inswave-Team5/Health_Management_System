package com.healthmanage.service;

public class GymService {
	private CouponService couponService;
	private UserService userService;
	private AdminService adminService;
	public GymService(){
		couponService = CouponService.getInstance();
		userService = UserService.getInstance();
		adminService = AdminService.getInstance();
	}
	public void load() {
		this.couponService.load();
		this.userService.load();
		this.adminService.load();
	}
	
	public void save() {
		this.couponService.save();
		this.userService.save();
		this.adminService.save();
	}
}
