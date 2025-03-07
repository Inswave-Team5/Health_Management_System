package com.healthmanage.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
	
	public void logCleanUp() {
		System.out.println("로그파일 정리 시작중,,");
		File[] logFiles = new File("logs").listFiles();
		
		if(logFiles.length >= 14) {
			for(File file : logFiles) {
				String fileName = getFileName(file.getName());
				
				LocalDate dateTime = convertLocalDateTime(fileName);
				LocalDate cutoffDate = convertLocalDateTime(time.currentDay()).minusDays(13);

				if(dateTime.isBefore(cutoffDate)) {
					System.out.println(file.getName()+" 로그 파일 삭제");
					file.delete();
				}
			}
		}
	}
	private String getFileName(String fullName) {
		return fullName.split("\\.")[0];
	}
	private LocalDate convertLocalDateTime(String fileName) {
		DateTimeFormatter  dateFormatParser = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(fileName, dateFormatParser);
	}
}
