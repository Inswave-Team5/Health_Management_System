package com.healthmanage.dao;

import java.util.Map;

import com.healthmanage.config.EnvConfig;
import com.healthmanage.model.Gym;
import com.healthmanage.model.User;
import com.healthmanage.utils.FileIO;

public class UserDAO {
	public void saveUsers() {
		FileIO.infoSave(Gym.users, EnvConfig.get("USER_FILE"));
	}

	public Map<String, User> loadUsers(String filePath) {
		Gym.users = (Map<String, User>)FileIO.infoLoad(filePath,User.class);
		return Gym.users;
	}

}
