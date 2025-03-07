package com.healthmanage.controller;

import com.healthmanage.dto.UserSignUpDTO;
import com.healthmanage.service.UserService;
import com.healthmanage.view.UserView;

public class UserController {
	private UserService userService;
	private UserView userView;

	public UserController() {
		this.userService = UserService.getInstance();
		this.userView = new UserView();
	}

	public void start() {
		int key = 0;
		while ((key = Integer.parseInt(userView.selectMenu())) != 0) {
			switch (key) {
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
            UserSignUpDTO userDTO = new UserSignUpDTO(userId, name, password);
            userService.addUser(userDTO);
            userView.showMessage("회원가입 완료!");
        }
	
	public void loginUser() {
        String userId = userView.getInput("ID 입력: ");
        String password = userView.getInput("비밀번호 입력: ");
        boolean loginSuccess = userService.userLogin(userId, password);
        if (loginSuccess) {
            userView.showMessage("로그인 성공!");
        } else {
            userView.showMessage("로그인 실패. 아이디 또는 비밀번호를 확인하세요.");
        }
    }
	
}
