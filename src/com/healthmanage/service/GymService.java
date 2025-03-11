package com.healthmanage.service;

import com.healthmanage.config.EnvConfig;
import com.healthmanage.model.Admin;
import com.healthmanage.model.Gym;
import com.healthmanage.utils.SHA256;

public class GymService {
	private CouponService couponService;
	private UserService userService;
	private AdminService adminService;

	public GymService() {
		this.couponService = CouponService.getInstance();
		this.userService = UserService.getInstance();
		this.adminService = AdminService.getInstance();
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

	public void adminInit() {
		Gym.admins.put(EnvConfig.get("ADMIN_ID"),
				new Admin(EnvConfig.get("ADMIN_NAME"), SHA256.encrypt(EnvConfig.get("ADMIN_PASSWORD")), EnvConfig.get("ADMIN_ID")));
	}
}
