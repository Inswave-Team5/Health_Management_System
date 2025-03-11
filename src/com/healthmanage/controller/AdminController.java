package com.healthmanage.controller;

import java.util.Collection;
import java.util.Map;

import com.healthmanage.model.Admin;
import com.healthmanage.model.Coupon;
import com.healthmanage.model.Gym;
import com.healthmanage.model.User;
import com.healthmanage.service.AdminService;
import com.healthmanage.utils.SHA256;
import com.healthmanage.view.AdminView;

public class AdminController {
	private AdminView view;
	private AdminService adminService;

	AdminController() {
		this.view = new AdminView();
		this.adminService = AdminService.getInstance();
	}
	/*----------유저 정보 조회----*/

	public void memberList() {
		Collection<User> users = adminService.memberList();
		if (users == null) {
			view.showMessage("등록된 회원이 없습니다.");
		} else {
			for (User user : users) {
				view.showMessage(user.toString());
			}
		}
	}

	public void entry() {
		while (!Gym.isLoggedIn()) {
			loginAdmin();
		}
		;
		start();
	}

	public void start() {
		int key = 0;
		while (loginAdmin() && (key = Integer.parseInt(view.selectAdminMenu())) != 0) {
			switch (key) {
			case 1:
				userManage();
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
		Gym.logoutUser();
		System.out.println("종료합니다...");
	}

	public boolean loginAdmin() {
		String userId = view.getInput("ID 입력: ");
		String password = view.getInput("비밀번호 입력: ");

		// 유저 정보 가져오기
		Admin admin = Gym.admins.get(userId);
		if (admin == null) {
			view.showMessage("로그인 실패. 존재하지 않는 아이디입니다.");
			return false;
		}
		
		// 로그인 검증
		Admin loginSuccess = adminService.adminLogin(userId, password);
		
		if (loginSuccess != null) {
			view.showMessage("로그인 성공!");
			Gym.setCurrentUser(loginSuccess);
			return true;
		} else {
			view.showMessage("로그인 실패. 아이디 또는 비밀번호를 확인하세요.");
			return false;
		}
	}

	public void userManage() {
		int key = 0;
		while (Gym.isLoggedIn() && (key = Integer.parseInt(view.selectUserManageMenu())) != 0) {
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
		Gym.logoutUser();
		System.out.println("종료합니다...");
	}

	/*----------쿠폰 조회------*/
	public void findAllCoupon() {
		Collection<Coupon> coupons = adminService.findAllCoupon();
		if (coupons == null) {
			view.showMessage("쿠폰정보가 없습니다.");
		} else {
			for (Coupon coupon : coupons) {
				view.showMessage(coupon.toString());
			}
		}
	};

	public void addCoupon() {
		// 쿠폰넘버, 코인 입력받고
		String couponNumber = view.getInput("생성할 쿠폰 번호 입력 : ");
		int coinAmount = Integer.parseInt(view.getInput("쿠폰 코인 입력 : "));

		// 트루면
		if (adminService.addCoupon(couponNumber, coinAmount)) {
			view.showMessage("쿠폰 생성이 완료됐습니다.");
		} else {
			view.showMessage("이미 존재하는 쿠폰번호입니다.");
		}
	};

	public void deleteCoupon() {
		String delCouponNum = view.getInput("삭제할 쿠폰 번호 입력 : ");
		String result = adminService.deleteCoupon(delCouponNum);
		view.showMessage(result);

	};

	public void getRank() {
		Map<String, String> ranks = adminService.getRank();
		if (ranks == null) {
			view.showMessage("랭킹정보가 없습니다.");
		} else {
			int cnt = 1;
			for (Map.Entry<String, String> entry : ranks.entrySet()) {
				view.showRank(cnt, entry.getKey(), entry.getValue());
				cnt++;
			}
		}
	}

	// 개인 회원 출결 조회 (날짜 별로) xxx - 입장 . 퇴근. //날짜 입력 받고 회원 출결 출력
	public void UserAttendanceByDay() {
		String id = view.getInput("검색할 회원의 아이디를 입력해주세요: ");
		String date = view.getInput("조회할 날짜를 입력해주세요 (입력형식:yyyy-MM-dd): ");

		adminService.UserAttendanceByDay(id, date);
	}

	// 개인 회원 출결 조회 (전체) xxx - 입장 . 퇴근. //회원 아이디 입력 받고 회원 출결 출력
	public void listUserAttendanceAll() {
		String id = view.getInput("검색할 회원의 아이디를 입력해주세요: ");

		adminService.listUserAttendanceAll(id);
	}

	// 전체 회원 출결 조회 (날짜 별로) xxx - 입장 . 퇴근. //날짜 입력 받고 회원 출결 출력
	public void listUserAttendanceByDay() {
		String date = view.getInput("조회할 날짜를 입력해주세요 (입력형식:yyyy-MM-dd): ");

		adminService.listAllUsersAttendanceByDay(date);
	}
}
