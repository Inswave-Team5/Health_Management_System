package com.healthmanage.controller;

import com.healthmanage.dto.UserSignUpDTO;
import com.healthmanage.model.Gym;
import com.healthmanage.model.User;
import com.healthmanage.service.UserService;
import com.healthmanage.utils.SHA256;
import com.healthmanage.view.UserView;

public class UserController {
	private final UserService userService;
	private final UserView userView;
	AttendanceController attendanceController;
	WeightController weightController;
	EquipmentController equipmentController;

	public UserController() {
        this.userService = UserService.getInstance();
		this.userView = new UserView();
		this.attendanceController = new AttendanceController();
		this.weightController = new WeightController();
		this.equipmentController = new EquipmentController();
	}

	public void entry() {
		int key = 0;
		while (!Gym.isLoggedIn() && (key = Integer.parseInt(userView.selectLogin())) != 0) {
			switch (key) {
			case 1:
				loginUser();
				break;
			case 2:
				registerUser();
				break;
			default:
				userView.showMessage("잘못 선택하였습니다.");
				break;
			}
		};
		start();
	}

	public void start() {
		int key = 0;
		while (Gym.isLoggedIn() && (key = Integer.parseInt(userView.selectMenu())) != 0) {

			userView.showMessage(key + "번 입력되었습니다.");

			switch (key) {
			case 1:
				attendanceController.attendanceEntry();

				break;
			case 2:
				attendanceController.timeEntry();
				break;
			case 3:
				weightController.weightEntry();
				break;
			case 4:
				equipmentController.equipmentEntry();
				break;
			case 5:
				//쿠폰등록
				couponUser();
				break;
			case 6:
				//코인
				coinEntry();
				break;
			case 7:
				//비밀번호 변경
				break;
			case 0:
				//로그아웃
				Gym.logoutUser();
				break;
			default:
				userView.showMessage("잘못 선택하였습니다.");
				break;
			}
		}
		Gym.logoutUser();
		System.out.println("종료합니다...");
	}

	public void coinEntry() {
		int key = 0;
		while (Gym.isLoggedIn() && (key = Integer.parseInt(userView.coinSelectMenu())) != 0) {
			userView.showMessage(key + "번 입력되었습니다.");
			switch (key) {
				case 1:
					addCoinUser();
					break;
				case 2:
					withdrawUser();
					break;
				case 0:
					start();
					break;
				default:
					userView.showMessage("잘못 선택하였습니다.");
					break;
			}
		}
		System.out.println("종료합니다...");
	}



	public void registerUser() {
		String userId;
		while (true) {
			// 🔹 View에서 아이디 입력 받기
			userId = userView.getInput("ID 입력: ");

			// 🔹 아이디 중복 검사
			if (userService.checkId(userId)) {
				break;
			}
			userView.showMessage("이미 존재하는 ID입니다. 다시 입력해주세요.");
		}

		// 나머지 회원 정보 입력
		String name = userView.getInput("이름 입력: ");
		String password = userView.getInput("비밀번호 입력: ");
		String hashedPw = SHA256.encrypt(password);

		// DTO 생성 및 회원가입 진행
		UserSignUpDTO userDTO = new UserSignUpDTO(userId, hashedPw, name);
		userService.addUser(userDTO);
		userView.showMessage("회원가입 완료!");
	}

	public boolean loginUser() {
		String userId = userView.getInput("ID 입력: ");
		String password = userView.getInput("비밀번호 입력: ");
		String hashedPw = SHA256.encrypt(password);
		User loginSuccess = userService.userLogin(userId, hashedPw);
		if (loginSuccess != null) {
			userView.showMessage("로그인 성공!");
			Gym.setCurrentUser(loginSuccess);
			return true;
		} else {
			userView.showMessage("로그인 실패. 아이디 또는 비밀번호를 확인하세요.");
			return false;
		}
	}

	public void couponUser() {
		String couponNumber = userView.getInput("쿠폰번호 입력: ");
		userView.showMessage(userService.useCoupon(couponNumber));
	}

	public void addCoinUser() {
		String inputMoney = userView.getInput("충전금액 입력: ");
		userView.showMessage(userService.addCoin(inputMoney));
	}

	public void withdrawUser() {
		String senderId = userView.getInput("보내는 사람 ID 입력: ");
		String receiverId = userView.getInput("받는 사람 ID 입력: ");
		String coin = userView.getInput("이체할 코인 입력: ");
		userView.showMessage(userService.withdrawCoin(coin, senderId, receiverId));
	}

}
