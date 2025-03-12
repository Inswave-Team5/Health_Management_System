package com.healthmanage.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.healthmanage.model.Admin;
import com.healthmanage.model.Coupon;
import com.healthmanage.model.Gym;
import com.healthmanage.model.User;
import com.healthmanage.service.AdminService;
import com.healthmanage.service.CouponService;
import com.healthmanage.service.UserService;
import com.healthmanage.view.AdminView;

public class AdminController {
	private AdminView view;
	private AdminService adminService;
	private CouponService couponService;
	private UserService userService;

	AdminController() {
		this.view = new AdminView();
		this.adminService = AdminService.getInstance();
		this.couponService = CouponService.getInstance();
		this.userService = UserService.getInstance();
	}

	public void findUserId(String userId) {
		String user = userService.findUserId(userId);
		if (user == null) {
			view.showMessage("일치하는 회원이 없습니다! 다시 검색해주세요.");
			return;
		}
		view.showMessage(user);
	}

	/*----------유저 정보 조회----*/
	public void memberList() {
		List<User> users = userService.findAllUserSortName();
		if (users == null || users.isEmpty()) {
			view.showMessage("등록된 회원이 없습니다.");
			return;
		}
		for (User user : users) {
			view.showMessage(user.toString());
		}
	}
	
	public void entry() {
		int key = 0;
		while (!Gym.isLoggedIn() && (key = Integer.parseInt(view.selectEntryMenu())) != 0) {
			switch (key) {
			case 1:
				loginAdmin();
				break;
			case 2:
				//addAdmin();
				break;
			default:
				view.showMessage("잘못 선택하였습니다.");
				break;
			}
		};
		start();
	}

	public void start() {
		int key = 0;
		while (loginAdmin() && (key = Integer.parseInt(view.selectAdminMenu())) != 0) {
			switch (key) {
			case 1:
				userManage();
				break;
			case 2:
				couponManage();
				break;
//			case 3: 로그확인
//			case 4: 기구관리

			default:
				System.out.println("잘못 선택하였습니다.");
				break;
			}
		}
		Gym.logoutUser();
		System.out.println("종료합니다.");
	}

	public boolean loginAdmin() {
		String userId = view.getInput("ID 입력: ");
		String password = view.getInput("비밀번호 입력: ");

		// 유효성 검사
		if (!adminService.isValidId(userId) || !adminService.isValidPw(password)) {
			view.showMessage("ID 또는 비밀번호 형식이 올바르지 않습니다.");
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
			case 1: memberList();
					break;
			case 2: UserAttendanceByDay();
					break;
			case 3: listUserAttendanceAll();
					break;
			case 4: listUserAttendanceByDay();
					break;
			case 5: getRank();
					break;
			default:
				System.out.println("잘못 선택하였습니다.");
				break;
			}
		}
		Gym.logoutUser();
		System.out.println("종료합니다.");
	}
	
	// 쿠폰관리
	public void couponManage() {
		int key = 0;
		while (Gym.isLoggedIn() && (key = Integer.parseInt(view.selectCouponManageMenu())) != 0) {
			switch (key) {
			case 1: addCoupon();		//쿠폰발급
					break;
			case 2: findAllCoupon();	//쿠폰조회
					break;
			case 3: deleteCoupon();		//쿠폰삭제
					break;
			default:
				System.out.println("잘못 선택하였습니다.");
				break;
			}
		}
		Gym.logoutUser();
		System.out.println("종료합니다.");
	}

	public void findAllCoupon() {
		Collection<Coupon> coupons = couponService.findAllCoupons();

		if (coupons == null || coupons.isEmpty()) {
			view.showMessage("쿠폰정보가 없습니다.");
			return;
		}
		for (Coupon coupon : coupons) {
			view.showMessage(coupon.toString());
		}
	};

	public void addCoupon() {
		String couponNumber = view.getInput("생성할 쿠폰 번호 입력 : ");
		int coinAmount = Integer.parseInt(view.getInput("쿠폰 코인 입력 : "));
		// 트루면
		if (couponService.createCoupon(couponNumber, coinAmount) != null) {
			view.showMessage("쿠폰 생성이 완료됐습니다.");
			return;
		}
		view.showMessage("이미 존재하는 쿠폰번호입니다.");
	};

	public void deleteCoupon() {
		String delCouponNum = view.getInput("삭제할 쿠폰 번호 입력 : ");
		Coupon coupon = couponService.deleteCoupon(delCouponNum);
		if (coupon == null) {
			view.showMessage("삭제 실패 - 없는 쿠폰번호 입니다.");
			return;
		}
		view.showMessage(coupon.toString() + "삭제");
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
