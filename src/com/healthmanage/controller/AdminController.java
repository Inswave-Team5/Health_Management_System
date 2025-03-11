package com.healthmanage.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.healthmanage.model.Coupon;
import com.healthmanage.model.User;
import com.healthmanage.service.AdminService;
import com.healthmanage.view.View;
import com.healthmanage.view.AdminView;

public class AdminController {
	private AdminView view;
	private AdminService adminService;
	AdminController(){
		this.view = new AdminView();
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

	
	public void start() {
		int key = 0;
		while ((key = Integer.parseInt(view.selectMenu())) != 0) {
			switch (key) {
			case 1:
				/*
				 * case 1: addBook(); break; case 2: removeBook(); break; case 3: searchBook();
				 * break; case 4: listBook(); break; case 5: listISBN(); break; case 6: save();
				 * break; case 7: load(); break;
				 */
			default:
				System.out.println("잘못 선택하였습니다.");
				break;
			}
		}
		System.out.println("종료합니다...");
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
	
	public void getRank() {
		Map<String, String> ranks = adminService.getRank();
		if (ranks == null) {
			view.showMessage("랭킹정보가 없습니다.");
		}
		else {
			int cnt = 1;
			for (Map.Entry<String, String> entry : ranks.entrySet()) {
				view.showRank(cnt, entry.getKey(), entry.getValue());
				cnt ++;
			}
		}
	}
}
