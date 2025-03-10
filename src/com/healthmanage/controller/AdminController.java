package com.healthmanage.controller;

import java.util.Collection;
import java.util.List;

import com.healthmanage.model.Coupon;
import com.healthmanage.model.User;
import com.healthmanage.service.AdminService;
import com.healthmanage.view.View;

public class AdminController {
	private View view;
	private AdminService adminService;
	AdminController(){
		this.view = new View();
		this.adminService = AdminService.getInstance();
	}
	/*----------유저 정보 조회----*/
	public void memberList() {
		Collection<User> users = adminService.memberList();
		if ( users == null) {
			view.showMessage("등록된 회원이 없습니다.");
		}
		else {
			for (User user : users) {
				view.showMessage(user.toString());
			}
		}
	}
	
	/*----------쿠폰 조회------*/
	public void findAllCoupon() {
		Collection<Coupon> coupons = adminService.findAllCoupon();
		if (coupons == null) {
			view.showMessage("쿠폰정보가 없습니다.");
		}else {
			for(Coupon coupon : coupons ) {
				view.showMessage(coupon.toString());
			}
		}
	};
	
	public void addCoupon() {
		
	};
	
	public void deleteCoupon() {
		
	};
}
