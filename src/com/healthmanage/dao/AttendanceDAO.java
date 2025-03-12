package com.healthmanage.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.healthmanage.config.EnvConfig;
import com.healthmanage.model.Attendance;
import com.healthmanage.service.AttendanceService;
import com.healthmanage.utils.FileIO;

public class AttendanceDAO {
	Map<String, List<Attendance>> attendances = AttendanceService.attendanceList;
	public void saveAttendances() {
		FileIO.infoSave(attendances, EnvConfig.get("ATTENDANCE_FILE"));
	}

//	public Map<String, List<Attendance>> loadAttendances(String filePath) {
//		
//		attendances = (Map<String, List<Attendance>>)FileIO.infoAttLoad(filePath, new ArrayList<Attendance>());
//		return attendances;
//	}

}
