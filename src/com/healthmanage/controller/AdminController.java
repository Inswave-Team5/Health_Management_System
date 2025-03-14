package com.healthmanage.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.healthmanage.config.EnvConfig;
import com.healthmanage.dto.UserSignUpDTO;
import com.healthmanage.model.*;
import com.healthmanage.service.AdminService;
import com.healthmanage.service.AttendanceService;
import com.healthmanage.service.MachineService;
import com.healthmanage.utils.Validations;
import com.healthmanage.service.CouponService;
import com.healthmanage.service.UserService;
import com.healthmanage.view.AdminView;

import static com.healthmanage.utils.Validations.validNumber;
import static com.healthmanage.utils.Validations.validateMachineType;

public class AdminController {
	private AdminView view;
	private AdminService adminService;
	private CouponService couponService;
	private UserService userService;
	private AttendanceService attendanceService;
	private MachineService machineService;

	AdminController() {
		this.view = new AdminView();
		this.adminService = AdminService.getInstance();
		this.couponService = CouponService.getInstance();
		this.userService = UserService.getInstance();
		this.attendanceService = AttendanceService.getInstance();
		this.machineService = MachineService.getInstance();
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
		   // 🏋 GYM 회원 목록 출력
	    view.showMessage("┌────────────────────────────────────────────────");
	    view.showMessage("│                🏋 GYM 회원 목록 🏋              ");
	    view.showMessage("├────────────┬───────────┬─────────────┬─────────");
	    view.showMessage("│   🆔 ID       👤 이름       💰 코인      ⏳ 남은시간 ");
	    view.showMessage("├────────────────────────────────────────────────");
		for (User user : users) {
			view.showMessage(user.toString());
		}
	}

	public void entry() {
		int key = 0;
		while (!Gym.isLoggedIn()) {
			try {
				key = Integer.parseInt(view.selectEntryMenu());
			} catch (NumberFormatException e) {
				view.showAlert("숫자로된 메뉴 번호를 입력해주세요");
				continue;
			}
			switch (key) {
			case 1:
				loginAdmin();
				break;
			case 2:
				addAdmin();
				break;
			case 0:
				view.showAlert("종료합니다.");
				adminService.save();
				return;
			default:
				view.showAlert("잘못 선택하였습니다.");
				break;
			}
		}
		;
		adminService.save();// 회원가입된 관리자 저장
		start();
	}

	public void start() {
		int key = 0;
		while (Gym.isLoggedIn()) {
			try {
				key = Integer.parseInt(view.selectAdminMenu());
			} catch (NumberFormatException e) {
				view.showAlert("숫자로된 메뉴 번호를 입력해주세요");
				continue;
			}
			switch (key) {
			case 1:
				userManage();
				break;
			case 2:
				couponManage();
				break;

			case 3: 
				machineManage();
				break;
			case 0:
				Gym.logoutUser();
				view.showAlert("종료합니다.");
				return;
			default:
				System.out.println("잘못 선택하였습니다.");
				break;
			}
		}
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
		while (Gym.isLoggedIn()) {
			try {
				key = Integer.parseInt(view.selectUserManageMenu());
			} catch (NumberFormatException e) {
				view.showAlert("숫자로된 메뉴 번호를 입력해주세요");
				continue;
			}
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
			case 0:
				view.showAlert("종료합니다.");
				return;
			default:
				System.out.println("잘못 선택하였습니다.");
				break;
			}
		}
	}

	public void addAdmin() {
		String code = view.getInput("관리자 코드 입력: ");
		if (!code.equals(EnvConfig.get("ADMIN_CODE"))) {
			view.showAlert("관리자 코드가 틀렸습니다. 다시 시도해주세요.");
			return;
		}

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

		// DTO 생성 및 회원가입 진행
		UserSignUpDTO userDTO = new UserSignUpDTO(adminId, password, name);
		adminService.addAdmin(userDTO);
		view.showAlert("회원가입 완료!");
	}

	// 쿠폰관리
	public void couponManage() {
		int key = 0;
		while (Gym.isLoggedIn()) {
			try {
				key = Integer.parseInt(view.selectCouponManageMenu());
			} catch (NumberFormatException e) {
				view.showAlert("숫자로된 메뉴 번호를 입력해주세요");
				continue;
			}
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
			case 0:
				view.showAlert("종료합니다.");
				couponService.save(); // 쿠폰관리 끝날 시 자동저장
				return;
			default:
				System.out.println("잘못 선택하였습니다.");
				break;
			}
		}
	}

	public void findAllCoupon() {
		Collection<Coupon> coupons = couponService.findAllCoupons();

		if (coupons == null || coupons.isEmpty()) {
			view.showAlert("쿠폰정보가 없습니다.");
			return;
		}
	    // 📌 쿠폰 목록 출력 (헤더)
	    view.showMessage("┌────────────────────────────────────");
	    view.showMessage("│          🎟 쿠폰 목록 🎟         ");
	    view.showMessage("├────────────┬──────────┬────────────");
	    view.showMessage("│  쿠폰번호       사용여부       보상코인  ");
	    view.showMessage("├────────────────────────────────────");
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
		if (Gym.coupons.containsKey(couponNumber)) {
			view.showAlert("이미 존재하는 쿠폰번호입니다.");
			return; // 이미 존재하는 경우 null 반환
		}
		int coinAmount = Integer.parseInt(view.getInput("쿠폰 코인 입력 : "));

		if (couponService.createCoupon(couponNumber, coinAmount) != null) {
			view.showAlert("쿠폰 생성이 완료됐습니다.");
		}
	};

	public void deleteCoupon() {
		String delCouponNum = view.getInput("삭제할 쿠폰 번호 입력 : ");
		Coupon coupon = couponService.deleteCoupon(delCouponNum);
		if (coupon == null) {
			view.showAlert("삭제 실패 - 없는 쿠폰번호 입니다.");
			return;
		}
		view.showAlert(coupon.toString() + "\n쿠폰 삭제가 완료되었습니다.");
	};

	public void getRank() {
		Map<String, String> ranks = attendanceService.getRank();
		if (ranks == null || ranks.isEmpty()) {
			view.showAlert("랭킹 정보가 없습니다.");
			return;
		}

		if (ranks.size() == 1) { // 회원이 한 명만 있을 경우
			Map.Entry<String, String> entry = ranks.entrySet().iterator().next();
			view.showRank(1, entry.getKey(), entry.getValue());
			view.showAlert("현재 랭킹에 등록된 회원이 1명뿐입니다.");
			return;
		}

		int cnt = 1;
		System.out.println("\n===============================================================");
		for (Map.Entry<String, String> entry : ranks.entrySet()) {
			view.showRank(cnt, entry.getKey(), entry.getValue());
			cnt++;
		}
		System.out.println("\n===============================================================");
	}

	// 개인 회원 출결 조회 (날짜 별로) xxx - 입장 . 퇴근. //날짜 입력 받고 회원 출결 출력
	public void UserAttendanceByDay() {
		String id;
		while (true) {
			String tmp = view.getInput("검색할 회원의 아이디를 입력해주세요: ");
			if (Gym.users.containsKey(tmp)) {
				id = tmp;
				break;
			} else {
				view.showMessage("없는 아이디입니다. 확인 후 다시 입력해주세요.");
				return;
			}
		}
		while (true) {
			String date = view.getInput("조회할 날짜를 입력해주세요 (입력형식:yyyy-MM-dd): ");

			if (Validations.validateYearMonthDay(date)) {
				view.showMessage(adminService.UserAttendanceByDay(id, date));
				break;
			} else {
				view.showMessage("잘못된 입력입니다. 다시 입력해주세요.");
				return;
			}
		}
	}

	// 개인 회원 출결 전체 조회 (전체) xxx - 입장 . 퇴근. //회원 아이디 입력 받고 회원 출결 출력
	public void listUserAttendanceAll() {

		while (true) {
			String id = view.getInput("검색할 회원의 아이디를 입력해주세요: ");

			if (Gym.users.containsKey(id)) {
				adminService.listUserAttendanceAll(id);
				break;
			} else {
				view.showMessage("없는 아이디입니다. 확인 후 다시 입력해주세요.");
				return;
			}
		}
	}

	// 전체 회원 출결 조회 (날짜 별로) xxx - 입장 . 퇴근. //날짜 입력 받고 회원 출결 출력
	public void listUserAttendanceByDay() {
		while (true) {
			String date = view.getInput("조회할 날짜를 입력해주세요 (입력형식:yyyy-MM-dd): ");

			if (Validations.validateYearMonthDay(date)) {
				adminService.listAllUsersAttendanceByDay(date);
				break;
			} else {
				view.showMessage("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
	}

	public void machineManage() {
		int key = 0;
		while (Gym.isLoggedIn()) {
			try {
				key = Integer.parseInt(view.selectMachineManageMenu());
			} catch (NumberFormatException e) {
				view.showAlert("숫자로된 메뉴 번호를 입력해주세요");
				continue;
			}
			switch (key) {
			case 1:
				listMachine();
				break;
			case 2:
				addMachine();
				break;
			case 3:
				deleteMachine();
				break;
			case 0:
				machineService.save();
				view.showAlert("종료합니다.");
				return;
			default:
				System.out.println("잘못 선택하였습니다.");
				break;
			}
		}
	}

	// 머신 현황
	public void listMachine() {
		List<Machine> machineList = machineService.listMachines();
		for (Machine machine : machineList) {
			view.showMessage(machine.toString());
		}
	}

	public void addMachine() {
		view.showMessage("1.벤치프레스    2.덤벨       3.렛풀다운    4.레그컬        5.숄더프레스");
		view.showMessage("6.스미스머신    7.계단오름    8.러닝머신    9.실내자전거    10.레그프레스");
		view.showMessage("");

		while(true) {
			// 번호 입력받기
			int input = Integer.parseInt(view.getInput("추가할 머신의 번호를 선택해주세요: "));

			// 타입 자동 결정
			String type = determineMachineType(input);

			if (type == null) {
				view.showMessage("잘못된 입력입니다. 번호를 다시 입력해주세요.");
				continue;  // 잘못된 입력이면 다시 번호 선택하도록
			}

			// 머신 서비스에서 해당 번호에 맞는 머신 추가
			machineService.addMachine(type, input);  // 타입과 번호 전달

			view.showMessage("등록이 완료되었습니다.");
			break;  // 등록 완료 후 종료
		}
	}

	// 머신 타입 자동 결정
	private String determineMachineType(int input) {
		if (input >= 1 && input <= 6 || input == 10) {
			return "근력";  // 번호 1~7은 근력
		} else if (input >= 7 && input <= 9) {
			return "유산소";  // 번호 8~10은 유산소
		}
		return null;  // 유효하지 않은 번호
	}

	public void deleteMachine() {
		machineService.listMachines();
		String id;
		while (true) {
			id = view.getInput("삭제할 머신의 번호를 입력해주세요 : ");
			if(validNumber(id)){
				machineService.removeMachine(id);
				view.showMessage("삭제가 완료되었습니다.");
				break;
			}else{
				view.showMessage("잘못된 머신 번호입니다. 다시 입력해주세요.");
			}
		}
	}


	// 쿠폰 번호는 8자리의 영문 대문자와 숫자로 구성되어야 함
	public boolean isValidCouponNumber(String couponNumber) {
		String regex = "^[A-Z0-9]{8}$";
		return couponNumber != null && couponNumber.matches(regex);
	}
}
