package com.healthmanage.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.healthmanage.config.EnvConfig;
import com.healthmanage.model.Attendance;
import com.healthmanage.model.Gym;
import com.healthmanage.service.AttendanceService;
import com.healthmanage.utils.FileIO;

public class AttDAO {
	private static AttDAO instance;
	private AttDAO() {
		
	}
	public static AttDAO getInstance() {
		if(instance == null) {
			instance = new AttDAO(); 
		}
		return instance;
	}
	
	public void saveAtts() {
		FileIO.infoSave(AttendanceService.attendanceList, EnvConfig.get("ATT_FILE"));
	}

	public Map<String, List<Attendance>> loadAtts(String filePath) {
		AttendanceService.attendanceList = (Map<String, List<Attendance>>) FileIO.infoAttLoad(filePath);
		return AttendanceService.attendanceList;
	}
}
