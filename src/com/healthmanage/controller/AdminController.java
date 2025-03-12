package com.healthmanage.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.healthmanage.config.EnvConfig;
import com.healthmanage.dto.UserSignUpDTO;
import com.healthmanage.model.Admin;
import com.healthmanage.model.Coupon;
import com.healthmanage.model.Gym;
import com.healthmanage.model.User;
import com.healthmanage.service.AdminService;
import com.healthmanage.utils.Validations;
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
			view.showAlert("일치하는 회원이 없습니다! 다시 검색해주세요.");
			return;
		}
		view.showMessage(user);
	}

	/*----------유저 정보 조회----*/

	public void memberList() {
		List<User> users = userService.findAllUserSortName();
		if (users == null || users.isEmpty()) {
			view.showAlert("등록된 회원이 없습니다.");
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
				addAdmin();
				break;
			default:
				view.showAlert("잘못 선택하였습니다.");
				break;
			}
		};
		adminService.save();// 회원가입된 관리자 저장
		start();
	}

	public void start() {
		int key = 0;
		while (Gym.isLoggedIn() && (key = Integer.parseInt(view.selectAdminMenu())) != 0) {
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
				view.showAlert("종료합니다.");
				break;
			}
		}
		Gym.logoutUser();
		view.showAlert("종료합니다.");
	}

	public boolean loginAdmin() {
		String userId = view.getInput("ID 입력: ");
		String password = view.getInput("비밀번호 입력: ");

		//유효성 검사
		if (!adminService.isValidId(userId) || !adminService.isValidPw(password)) {
            view.showMessage("ID 또는 비밀번호 형식이 올바르지 않습니다.");
            return false;
        }
		
		
		// 로그인 검증
		Admin loginSuccess = adminService.adminLogin(userId, password);

		if (loginSuccess != null) {
			view.showAlert("로그인 성공!");
			Gym.setCurrentUser(loginSuccess);
			return true;
		} else {
			view.showAlert("로그인 실패. 아이디 또는 비밀번호를 확인하세요.");
			return false;
		}
	}

	public void userManage() {
		int key = 0;
		while (Gym.isLoggedIn() && (key = Integer.parseInt(view.selectUserManageMenu())) != 0) {
			switch (key) {
			case 1:
				memberList();
				break;
			case 2:
				UserAttendanceByDay();
				break;
			case 3:
				listUserAttendanceAll();
				break;
			case 4:
				listUserAttendanceByDay();
				break;
			case 5:
				getRank();
				break;
			default:
				view.showAlert("종료합니다.");
				break;
			}
		}
//		Gym.logoutUser();
		view.showAlert("종료합니다.");
	}

	public void addAdmin() {
		String adminId;
		while (true) {
			// 🔹 View에서 아이디 입력 받기
			adminId = view.getInput("ID 입력: ");

			// ID 유효성 검사
			if (!userService.isValidId(adminId)) {
				view.showAlert("ID는 5~12자의 영어 소문자와 숫자만 가능합니다.");
				continue;
			}

			// 🔹 아이디 중복 검사
			if (userService.checkId(adminId)) {
				break;
			}
			view.showAlert("이미 존재하는 ID입니다. 다시 입력해주세요.");
		}

		// 나머지 회원 정보 입력
		String name = view.getInput("이름 입력: ");
		String password;

		while (true) {
			password = view.getInput("비밀번호 입력: ");

			// 비밀번호 유효성 검사
			if (!userService.isValidPw(password)) {
				view.showAlert("비밀번호는 8~16자이며, 대문자, 소문자, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다.");
				continue;
			}
			break;
		}

		String code = view.getInput("관리자 코드 입력: ");
		if (!code.equals(EnvConfig.get("ADMIN_CODE"))) {
			view.getInput("관리자 코드가 틀렸습니다.");
			return;
		}

		// DTO 생성 및 회원가입 진행
		UserSignUpDTO userDTO = new UserSignUpDTO(adminId, password, name);
		adminService.addAdmin(userDTO);
		view.showAlert("회원가입 완료!");
	}

	// 쿠폰관리
	public void couponManage() {
		int key = 0;
		while (Gym.isLoggedIn() && (key = Integer.parseInt(view.selectCouponManageMenu())) != 0) {
			switch (key) {
			case 1:
				addCoupon(); // 쿠폰발급
				break;
			case 2:
				findAllCoupon(); // 쿠폰조회
				break;
			case 3:
				deleteCoupon(); // 쿠폰삭제
				break;
			default:
				view.showAlert("종료합니다.");
				break;
			}
		}
//		Gym.logoutUser();
		couponService.save(); // 쿠폰관리 끝날 시 자동저장
		view.showAlert("종료합니다.");
	}

	public void findAllCoupon() {
		Collection<Coupon> coupons = couponService.findAllCoupons();

		if (coupons == null || coupons.isEmpty()) {
			view.showAlert("쿠폰정보가 없습니다.");
			return;
		}
		for (Coupon coupon : coupons) {
			view.showMessage(coupon.toString());
		}
	};

	public void addCoupon() {
		String couponNumber = view.getInput("생성할 쿠폰 번호 입력 : ");
		if (!isValidCouponNumber(couponNumber)) {
			view.showAlert("유효하지 않은 쿠폰번호 형식입니다. 8자리의 영문 대문자와 숫자로 입력해주세요.");
			return;
		}
		int coinAmount = Integer.parseInt(view.getInput("쿠폰 코인 입력 : "));
		// 트루면
		if (couponService.createCoupon(couponNumber, coinAmount) != null) {
			view.showAlert("쿠폰 생성이 완료됐습니다.");
			return;
		}
		view.showAlert("이미 존재하는 쿠폰번호입니다.");
	};

	public void deleteCoupon() {
		String delCouponNum = view.getInput("삭제할 쿠폰 번호 입력 : ");
		Coupon coupon = couponService.deleteCoupon(delCouponNum);
		if (coupon == null) {
			view.showAlert("삭제 실패 - 없는 쿠폰번호 입니다.");
			return;
		}
		view.showAlert(coupon.toString() + "삭제");
	};

	public void getRank() {
		Map<String, String> ranks = adminService.getRank();
		if (ranks == null) {
			view.showAlert("랭킹정보가 없습니다.");
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
		String id;
		while(true){
				String tmp = view.getInput("검색할 회원의 아이디를 입력해주세요: ");
			if(Gym.users.containsKey(tmp)){
				id = tmp;
				break;
			}else{
				view.showMessage("없는 아이디입니다. 확인 후 다시 입력해주세요.");
				return;
			}
		}
		while(true){
			String date = view.getInput("조회할 날짜를 입력해주세요 (입력형식:yyyy-MM-dd): ");

			if(Validations.validatePositiveDecimal(date)){
				view.showMessage(adminService.UserAttendanceByDay(id, date));
				break;
			}else{
				view.showMessage("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
	}

	// 개인 회원 출결 전체 조회 (전체) xxx - 입장 . 퇴근. //회원 아이디 입력 받고 회원 출결 출력
	public void listUserAttendanceAll() {

		while(true){
			String id = view.getInput("검색할 회원의 아이디를 입력해주세요: ");

			if(Gym.users.containsKey(id)){
				adminService.listUserAttendanceAll(id);
				break;
			}else{
				view.showMessage("없는 아이디입니다. 확인 후 다시 입력해주세요.");
				return;
			}
		}

	}

	// 전체 회원 출결 조회 (날짜 별로) xxx - 입장 . 퇴근. //날짜 입력 받고 회원 출결 출력
	public void listUserAttendanceByDay() {
		while(true){
			String date = view.getInput("조회할 날짜를 입력해주세요 (입력형식:yyyy-MM-dd): ");

			if(Validations.validatePositiveDecimal(date)){
				adminService.listAllUsersAttendanceByDay(date);
				break;
			}else{
				view.showMessage("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}

	}

	// 쿠폰 번호는 8자리의 영문 대문자와 숫자로 구성되어야 함
	public boolean isValidCouponNumber(String couponNumber) {
		String regex = "^[A-Z0-9]{8}$";
		return couponNumber != null && couponNumber.matches(regex);
	}
}
