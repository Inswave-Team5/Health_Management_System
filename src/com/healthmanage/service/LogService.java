package com.healthmanage.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.healthmanage.config.EnvConfig;
import com.healthmanage.utils.Time;

public class LogService {
	private Time time;
	private static LogService instance;

	private LogService() {
		time = Time.getInstance();
	}

	public static LogService getInstance() {
		if (instance == null) {
			instance = new LogService();
		}
		return instance;
	}

	public void addLog(String message) {

		String logDir = "logs"; // 로그 디렉토리
		String logFileName = time.currentDay() + EnvConfig.get("LOG_FILE_PATH");

		// 로그 디렉토리가 없다면 생성
		File dir = new File(logDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		File name = new File(logDir+"\\"+logFileName);
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(name, true))) {
			writer.write(time.currentDayAndTime()+" " +message);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
