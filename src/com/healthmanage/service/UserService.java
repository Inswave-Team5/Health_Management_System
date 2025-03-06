package com.healthmanage.service;

import com.healthmanage.model.Gym;

import com.healthmanage.model.User;

public class UserService {

	public boolean checkId(String userId) {
		if (Gym.users.containsKey(userId)) {
			return false;
		}
		return true;
	}

	public void addUser(String name, String password, String userId) {

		Gym.users.put(userId, new User(name, password, userId));
	}

	public boolean userLogin(String userId, String pw) {
		if (!Gym.users.containsKey(userId)) {
			System.out.println("없는 아이디입니다.");
			return false;
		} else {
			if (!Gym.users.get(userId).getPassword().equals(pw)) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				return false;
			} else {
				System.out.println("로그인 성공");
				return true;
			}
		}
	}
}
