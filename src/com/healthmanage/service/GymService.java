package com.healthmanage.service;

import com.healthmanage.config.EnvConfig;
import com.healthmanage.model.Admin;
import com.healthmanage.model.Gym;
import com.healthmanage.utils.MockupCreator;
import com.healthmanage.utils.SHA256;

public class GymService {
	private CouponService couponService;
	private UserService userService;
	private AdminService adminService;
	private AttendanceService attendanceService;
	private WeightService weightService;
	private MachineService machineService;

	public GymService() {
		this.couponService = CouponService.getInstance();
		this.userService = UserService.getInstance();
		this.adminService = AdminService.getInstance();
		this.attendanceService = AttendanceService.getInstance();
		this.weightService = WeightService.getInstance();
		this.machineService = MachineService.getInstance();

	}

	public void load() {
		this.couponService.load();
		this.userService.load();
		this.adminService.load();
		this.machineService.load();
		
		
		this.attendanceService.load();
		this.weightService.load();
	}

	public void save() {
		this.couponService.save();
		this.userService.save();
		this.adminService.save();
		this.attendanceService.save();
		this.weightService.save();
		this.machineService.save();
	}

	public void adminInit() {
		String adminId = EnvConfig.get("ADMIN_ID");
		String adminName = EnvConfig.get("ADMIN_NAME");
		String adminPassword = EnvConfig.get("ADMIN_PASSWORD");

		String salt = SHA256.generateSalt();
		String hashedPassword = SHA256.hashPassword(adminPassword, salt);

		Gym.admins.put(adminId, new Admin(adminName, hashedPassword, adminId, salt));
	}

	public void UserInfoInit() {
		MockupCreator.generateMockUsers();
	}

	public void CouponInit() {
		MockupCreator.generateMockCoupons();
	}

	public void AttandanceInit() {
		MockupCreator.generateMockAttendanceData();
	}

	public void WeightInit() {
		MockupCreator.generateMockWeightData();
	}
}
