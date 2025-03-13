package com.healthmanage.controller;

import com.healthmanage.dto.UserSignUpDTO;
import com.healthmanage.model.Gym;
import com.healthmanage.model.User;
import com.healthmanage.service.UserService;
import com.healthmanage.view.UserView;
import com.healthmanage.view.enums.CoinMenu;
import com.healthmanage.view.enums.LoginOption;
import com.healthmanage.view.enums.UserMenu;

public class UserController {
	private final UserService userService;
	private final UserView userView;
	AttendanceController attendanceController;
	WeightController weightController;
	MachineController machineController;

	public UserController() {
		this.userService = UserService.getInstance();
		this.userView = new UserView();
		this.attendanceController = new AttendanceController();
		this.weightController = new WeightController();
		this.machineController = new MachineController();
	}

    public void entry() {
        while (!Gym.isLoggedIn()) {
            try {
                int key = Integer.parseInt(userView.selectLogin());
                LoginOption selectedOption = LoginOption.fromNumber(key); // 🔹 Enum 변환

                if (selectedOption == null) {
                    userView.showAlert("잘못된 선택입니다. 다시 입력해주세요.");
                    continue;
                }
                userView.showAlert("선택한 메뉴: " + selectedOption.getDescription());

                switch (selectedOption) {
                    case LOGIN:
                        loginUser();
                        break;
                    case SIGN_UP:
                        registerUser();
                        break;
                    case EXIT:
                        userView.showAlert("종료합니다.");
                        return;
                }
            } catch (NumberFormatException e) {
                userView.showAlert("숫자로 된 메뉴 번호를 입력해주세요.");
            }
        }
        userService.save();
        start();
    }


	public void start() {
	    while (Gym.isLoggedIn()) {
	        try {
	            int key = Integer.parseInt(userView.selectMenu()); // 기존 방식 그대로 입력 받음
	            UserMenu selectedMenu = UserMenu.fromNumber(key); // 입력값을 Enum으로 변환

	            if (selectedMenu == null) {
	                userView.showAlert("잘못된 선택입니다. 다시 입력해주세요.");
	                continue;
	            }
	            userView.showAlert(selectedMenu.getNumber() + "번 입력되었습니다.");

	            switch (selectedMenu) {
	                case CHECK_IN:
	                    attendanceController.attendanceEntry();
	                    break;
	                case WORKOUT_TIME:
	                    attendanceController.timeEntry();
	                    break;
	                case WEIGHT_MANAGEMENT:
	                    weightController.weightEntry();
	                    break;
	                case MACHINE_USE:
	                    machineController.machineEntry();
	                    break;
	                case COUPON_USE:
	                    couponUser();
	                    break;
	                case COIN_MANAGEMENT:
	                    coinEntry();
	                    break;
	                case PASSWORD_CHANGE:
	                    passwordChange();
	                    break;
	                case LOGOUT:
	                    Gym.logoutUser();
	                    System.out.println("종료합니다...");
	                    return;
	            }
	        } catch (NumberFormatException e) {
	            userView.showAlert("숫자로된 메뉴 번호를 입력해주세요");
	        }
	    }
	}

	public void coinEntry() {
	    while (Gym.isLoggedIn()) {
	        try {
	        	int key = Integer.parseInt(userView.coinSelectMenu(Gym.users.get(Gym.getCurrentUser().getUserId()).getCoin()));
	            CoinMenu selectedMenu = CoinMenu.fromNumber(key);  // 🔹 Enum 변환

	            if (selectedMenu == null) {
	                userView.showAlert("잘못된 선택입니다. 다시 입력해주세요.");
	                continue;
	            }

	            userView.showAlert("선택한 메뉴: " + selectedMenu.getDescription());

	            switch (selectedMenu) {
	                case COIN_CHARGE:
	                    addCoinUser();
	                    break;
	                case COIN_TRANSFER:
	                    withdrawUser();
	                    break;
	                case BACK:
	                    userService.save();
	                    userView.showAlert("이전 메뉴로 돌아갑니다.");
	                    return;
	            }
	        } catch (NumberFormatException e) {
	            userView.showAlert("숫자로 된 메뉴 번호를 입력해주세요.");
	        }
	    }
	}

	public void registerUser() {
		String userId;
		while (true) {
			// 🔹 View에서 아이디 입력 받기
			userId = userView.getInput("ID 입력: ");

			// ID 유효성 검사
			if (!userService.isValidId(userId)) {
				userView.showAlert("ID는 5~12자의 영어 소문자와 숫자만 가능합니다.");
				continue;
			}

			// 🔹 아이디 중복 검사
			if (userService.checkId(userId)) {
				break;
			}
			userView.showAlert("이미 존재하는 ID입니다. 다시 입력해주세요.");
		}

		// 나머지 회원 정보 입력
		String name = userView.getInput("이름 입력: ");
		String password;

		while (true) {
			password = userView.getInput("비밀번호 입력: ");

			// 비밀번호 유효성 검사
			if (!userService.isValidPw(password)) {
				userView.showAlert("비밀번호는 8~16자이며, 대문자, 소문자, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다.");
				continue;
			}

			break;
		}

		// DTO 생성 및 회원가입 진행
		UserSignUpDTO userDTO = new UserSignUpDTO(userId, password, name);
		userService.addUser(userDTO);
		userView.showAlert("회원가입 완료!");
	}

	public boolean loginUser() {
		String userId = userView.getInput("ID 입력: ");
		String password = userView.getInput("비밀번호 입력: ");

		// 유효성 검사
		if (!userService.isValidId(userId) || !userService.isValidPw(password)) {
			userView.showAlert("ID 또는 비밀번호 형식이 올바르지 않습니다.");
			return false;
		}

		// 유저 정보 가져오기
		User user = Gym.users.get(userId);
		if (user == null) {
			userView.showAlert("로그인 실패. 존재하지 않는 아이디입니다.");
			return false;
		}

		// 로그인 검증
		User loginSuccess = userService.userLogin(userId, password);

		if (loginSuccess != null) {
			userView.showAlert("로그인 성공!");
			Gym.setCurrentUser(loginSuccess);
			return true;
		} else {
			userView.showAlert("로그인 실패. 비밀번호를 확인하세요.");
			return false;
		}
	}

	public void passwordChange() {
		User currentUser = (User) Gym.getCurrentUser();

		if (currentUser == null) {
			userView.showMessage("로그인된 사용자가 없습니다.");
			return;
		}

		String currentPw = userView.getInput("현재 비밀번호를 입력하세요:");
		changeUserPassword(currentUser.getUserId(), currentPw);
	}

	public void changeUserPassword(String memberNum, String pw) {

		if (!userService.verifyPassword(memberNum, pw)) {
			userView.showMessage("비밀번호가 올바르지 않습니다.");
			return;
		}

		String newPw = userView.getInput("새로운 비밀번호를 입력하세요:");
		String newPw2 = userView.getInput("새로운 비밀번호를 다시 한번 입력하세요:");

		if (!newPw.equals(newPw2)) {
			userView.showMessage("비밀번호가 일치하지 않습니다. 다시 시도하세요.");
			return;
		}

		userService.updatePassword(memberNum, newPw);
		userView.showMessage("비밀번호가 성공적으로 변경되었습니다.");

	}

	public void couponUser() {
		try {
			userView.showMessage("📢 8자리의 쿠폰번호를 입력해주세요.");
			String couponNumber = userView.getInput("쿠폰번호 입력: ");
			String resultMessage = userService.useCoupon(couponNumber);
			userView.showMessage(resultMessage);
		} catch (Exception e) {
			userView.showAlert("오류가 발생했습니다: " + e.getMessage());
		}
	}

	public void addCoinUser() {
		String inputMoney = userView.getInput("충전금액 입력: ");
		// 🔹 Controller에서 입력값 검증 (Validation)
		if (!isValidMoneyInput(inputMoney)) {
			userView.showAlert("숫자로 된 올바른 충전 금액을 입력해주세요. (1000원 이상)");
			return;
		}
		String resultMessage = userService.addCoin(Integer.parseInt(inputMoney));
		userView.showMessage(resultMessage);
	}

	// 아이디 입력 안했을 경우
	private boolean isValidIdInput(String userId) {
		return userService.isValidId(userId);
	}

	// 패스워드 입력 안했을 경우
	private boolean isValidPasswordInput(String password) {
		return userService.isValidPw(password);
	}

	// 쿠폰 번호는 8자리의 영문 대문자와 숫자로 구성되어야 함
	public boolean isValidCouponNumber(String couponNumber) {
		String regex = "^[A-Z0-9]{8}$";
		return couponNumber != null && couponNumber.matches(regex);
	}

	// 🔹 숫자 여부 및 최소 금액 검증하는 함수
	private boolean isValidMoneyInput(String money) {
		try {
			int vaildatedMoney = Integer.parseInt(money);
			return vaildatedMoney >= 1000; // 1원 이상인지 확인
		} catch (NumberFormatException e) {
			return false; // 숫자가 아닌 경우 false 반환
		}
	}

	// 🔹 숫자 여부 및 최소 금액 검증하는 함수
	private boolean isValidCoinInput(String coin) {
		try {
			int vaildatedCoin = Integer.parseInt(coin);
			return vaildatedCoin >= 1; // 1원 이상인지 확인
		} catch (NumberFormatException e) {
			return false; // 숫자가 아닌 경우 false 반환
		}
	}

	public void withdrawUser() {
		String receiverId = userView.getInput("받는 사람 ID 입력: ");
		User receiver = Gym.users.get(receiverId);
		if (receiver == null) {
			userView.showAlert("수신자를 찾을 수 없습니다.");
			return;
		}

		String coin = userView.getInput("이체할 코인 입력: ");
		if (!isValidCoinInput(coin)) {
			userView.showAlert("숫자로 된 올바른 코인을 입력해주세요. (1개 이상)");
			return;
		}
		userView.showMessage(userService.withdrawCoin(Integer.parseInt(coin), receiver));
	}
}
