package com.healthmanage.controller;

import com.healthmanage.dto.UserSignUpDTO;
import com.healthmanage.model.Gym;
import com.healthmanage.model.User;
import com.healthmanage.service.UserService;
import com.healthmanage.view.UserView;

public class UserController {
	private UserService userService;

	private UserView userView;

	public UserController() {
		this.userService = UserService.getInstance();
		this.userView = new UserView();
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
		}
		;
		start();
	}

	public void start() {
		int key = 0;
		while (Gym.isLoggedIn() && (key = Integer.parseInt(userView.selectMenu())) != 0) {
			switch (key) {
			case 1:
				System.out.println(key + "번 입력되었습니다.");
				break;
			case 2:
				System.out.println(key + "번 입력되었습니다.");
				break;
			case 3:
				System.out.println(key + "번 입력되었습니다.");
				break;
			case 4:
				System.out.println(key + "번 입력되었습니다.");
				break;
			case 5:
				System.out.println(key + "번 입력되었습니다.");
				break;
			case 6:
				System.out.println(key + "번 입력되었습니다.");
				break;
			case 7:
				System.out.println(key + "번 입력되었습니다.");
				break;
			case 8:
				System.out.println(key + "번 입력되었습니다.");
				break;
			/*
			 * case 1: addBook(); break; case 2: removeBook(); break; case 3: searchBook();
			 * break; case 4: listBook(); break; case 5: listISBN(); break; case 6: save();
			 * break; case 7: load(); break;
			 */
			default:
				userView.showMessage("잘못 선택하였습니다.");
				break;
			}
		}
		Gym.logoutUser();
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

		// DTO 생성 및 회원가입 진행
		UserSignUpDTO userDTO = new UserSignUpDTO(userId, password, name);
		userService.addUser(userDTO);
		userView.showMessage("회원가입 완료!");
	}

	public boolean loginUser() {
		String userId = userView.getInput("ID 입력: ");
		String password = userView.getInput("비밀번호 입력: ");

		// 유저 정보 가져오기
		User user = Gym.users.get(userId);
		if (user == null) {
			userView.showMessage("로그인 실패. 존재하지 않는 아이디입니다.");
			return false;
		}

		// 로그인 검증
		User loginSuccess = userService.userLogin(userId, password);

		if (loginSuccess != null) {
			userView.showMessage("로그인 성공!");
			Gym.setCurrentUser(loginSuccess);
			return true;
		} else {
			userView.showMessage("로그인 실패. 비밀번호를 확인하세요.");
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
