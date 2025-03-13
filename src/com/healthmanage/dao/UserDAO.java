package com.healthmanage.dao;

import java.util.Map;

import com.healthmanage.config.EnvConfig;
import com.healthmanage.model.Gym;
import com.healthmanage.model.User;
import com.healthmanage.utils.FileIO;

public class UserDAO {
	private static UserDAO instance;
	private UserDAO() {
		
	}
	public static UserDAO getInstance() {
		if(instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}
	public void saveUsers() {
		FileIO.infoSave(Gym.users, EnvConfig.get("USER_FILE"));
	}

	public Map<String, User> loadUsers(String filePath) {
		Gym.users = (Map<String, User>)FileIO.infoLoad(filePath,User.class);
		return Gym.users;
	}
}
