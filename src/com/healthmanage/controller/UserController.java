package com.healthmanage.controller;

import com.healthmanage.dto.UserSignUpDTO;
import com.healthmanage.model.Gym;
import com.healthmanage.model.User;
import com.healthmanage.service.UserService;
import com.healthmanage.utils.SHA256;
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
		String hashedPw = SHA256.encrypt(password);

		// DTO 생성 및 회원가입 진행
		UserSignUpDTO userDTO = new UserSignUpDTO(userId, hashedPw, name);
		User user = userService.addUser(userDTO);
		if (user != null) {
			userView.showMessage(user.getName()+"님 회원가입 완료되었습니다!");
		}
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
		// 🔹 Controller에서 입력값 검증 (Validation)
		if (!isValidCoinInput(inputMoney)) {
			userView.showMessage("숫자로 된 올바른 충전 금액을 입력해주세요. (1000원 이상)");
			return;
		}
		String resultMessage = userService.addCoin(Integer.parseInt(inputMoney));
		userView.showMessage(resultMessage);
	}

	// 🔹 숫자 여부 및 최소 금액 검증하는 함수
	private boolean isValidCoinInput(String money) {
		try {
			int coin = Integer.parseInt(money);
			return coin > 1000; // 1원 이상인지 확인
		} catch (NumberFormatException e) {
			return false; // 숫자가 아닌 경우 false 반환
		}
	}

	public void withdrawUser() {
		String senderId = userView.getInput("보내는 사람 ID 입력: ");
		String receiverId = userView.getInput("받는 사람 ID 입력: ");
		String coin = userView.getInput("이체할 코인 입력: ");
		userView.showMessage(userService.withdrawCoin(coin, senderId, receiverId));
	}

}
