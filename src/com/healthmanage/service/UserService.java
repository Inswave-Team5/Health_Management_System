package com.healthmanage.service;

import com.healthmanage.dto.UserSignUpDTO;
import com.healthmanage.model.Gym;
import com.healthmanage.model.User;

public class UserService {
	private static UserService instance;

	private UserService() {

	}

	public static UserService getInstance() {
		if (instance == null) {
			instance = new UserService();
		}
		return instance;
	}

	public boolean checkId(String userId) {
		if (Gym.users.containsKey(userId)) {
			return false;
		}
		return true;
	}

	public void addUser(UserSignUpDTO userDTO) {
		Gym.users.put(userDTO.getUserId(), new User(userDTO.getUserId(), userDTO.getPassword(), userDTO.getName()));
	}

	public boolean userLogin(String userId, String pw) {
		if (Gym.users.containsKey(userId) && Gym.users.get(userId).getPassword().equals(pw)) {
			return true;
		} else {
			return false;
		}

	}
}
