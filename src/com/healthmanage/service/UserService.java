package com.healthmanage.service;

import com.healthmanage.model.Gym;
import com.healthmanage.model.Person;

public class UserService {
	public boolean userLogin(String userId, String pw) {
		if (!Gym.users.containsKey(userId)) {
			System.out.println("없는 아이디입니다.");
			return false;
		}else {
			if(!Gym.users.get(userId).getPassword().equals(pw)) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				return false;
			}else {
				System.out.println("로그인 성공");
				return true;
			}
		}
	}
	
	
}
