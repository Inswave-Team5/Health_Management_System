package com.healthmanage.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class Time {
	private static Time instance;
	
	LocalDateTime now;
	DateTimeFormatter dtf;
	
	private Time(){}
	
	public static Time getInstance() {
		if (instance == null) {
			instance =  new Time();
		}
		return instance;
	}
	
	public String currentDayAndTime() {
		dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd-a_HH:mm:ss" );
		now = LocalDateTime.now();
		return dtf.format(now);
	}
	
	public String currentDay() {
		dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		now = LocalDateTime.now();
		return dtf.format(now);
	}

	//입력받은 날짜에서 월(MM)만 추출
	public String getMonthByInput(String input) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(input, dtf);
		return date.format(DateTimeFormatter.ofPattern("MM"));
	}

	//시간만 추출
	public String currentTime() {
		dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		now = LocalDateTime.now();
		return dtf.format(now);
	}

	//두 시간의 차이 계산
	public Duration getTimeDiff(String startTime, String endTime) {
		LocalTime startLocalTime = LocalTime.parse(startTime);
		LocalTime endLocalTime = LocalTime.parse(endTime);

		return Duration.between(startLocalTime, endLocalTime);

	}


	//시간 누적 계산
	public Duration totalDuration(String duration) {
		LocalTime time = LocalTime.parse(duration);

		return Duration.ofHours(time.getHour())
				.plusMinutes(time.getMinute())
				.plusSeconds(time.getSecond());
	}

	// 누적된 Duration을 "HH:mm:ss" 형식의 문자열로 변환
	public String transTimeFormat(Duration totalDuration) {
		long hours = totalDuration.toHours();
		long minutes = totalDuration.toMinutesPart();
		long seconds = totalDuration.toSecondsPart();

		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}

}
