package com.healthmanage.service;

import com.healthmanage.model.Gym;
import com.healthmanage.model.User;

public class UserService {
	
	public boolean checkId(String userId) {
		if(Gym.users.containsKey(userId)) {
			return false;
		}
		return true;
	}
	
	public void addUser(String name, String password, String userId) {

		Gym.users.put(userId, new User(name, password, userId));
	}

}
