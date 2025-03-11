package com.healthmanage.dao;

import java.util.Map;

import com.healthmanage.config.EnvConfig;
import com.healthmanage.model.Admin;
import com.healthmanage.model.Gym;
import com.healthmanage.utils.FileIO;

public class AdminDAO {
	public void saveAdmins() {
		FileIO.infoSave(Gym.admins, EnvConfig.get("ADMIN_FILE"));
	}

	public Map<String, Admin> loadAdmins(String filePath) {
		Gym.admins = (Map<String, Admin>) FileIO.infoLoad(filePath,Admin.class);
		return Gym.admins;
	}
}
