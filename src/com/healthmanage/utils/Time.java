package com.healthmanage.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;
import java.time.Duration;

public class Time implements Serializable {
	private static Time instance;

	LocalDateTime now;
	private transient DateTimeFormatter dtf; // ✅ 직렬화 제외 (transient 사용) dtf;

	private Time() {
	}

	public static Time getInstance() {
		if (instance == null) {
			instance = new Time();
		}
		return instance;
	}

	public String currentDayAndTime() {
		dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		now = LocalDateTime.now();
		return dtf.format(now);
	}

	public String currentDay() {
		dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		now = LocalDateTime.now();
		return dtf.format(now);
	}

	 // ✅ 역직렬화 후 `formatter` 필드 자동 복구
    private Object readResolve() {
        this.dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return this;
    }
    
	// 입력받은 날짜에서 년-월(yyyy-MM)만 추출
	public String getYearMonthByInput(String input) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(input, dtf);
		return date.format(DateTimeFormatter.ofPattern("yyyy-MM"));
	}

	// 시간만 추출
	public String getTimeFromString(String inputTime) {
		// 입력된 문자열에 맞는 포맷 설정
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

		try {
			// 문자열을 LocalDateTime으로 파싱
			LocalDateTime parsedDateTime = LocalDateTime.parse(inputTime, formatter);

			// 시간만 추출해서 반환
			return parsedDateTime.toLocalTime().toString(); // "19:09:38" 형식으로 반환
		} catch (Exception e) {
			System.out.println("시간 파싱 오류: " + e.getMessage());
			return null; // 오류 발생 시 null 반환
		}
	}

	// 날짜 시간 추출
	public String getYearTimeFromString(String inputTime) {
		// 입력된 문자열에 맞는 포맷 설정
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

		try {
			// 문자열을 LocalDateTime으로 파싱
			LocalDateTime parsedDateTime = LocalDateTime.parse(inputTime, formatter);

			// 시간만 추출해서 반환
			return parsedDateTime.toLocalTime().toString(); // "19:09:38" 형식으로 반환
		} catch (Exception e) {
			System.out.println("시간 파싱 오류: " + e.getMessage());
			return null; // 오류 발생 시 null 반환
		}
	}

	// 두 시간의 차이 계산
// 두 시간의 차이 계산
	public Duration getTimeDiff(String startTime, String endTime) {
		// 24시간 형식으로 포맷 설정 (AM/PM 제거)
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

		// '-'와 '_'를 공백으로 교체하여 구분
		startTime = startTime.replace("-", " ").replace("_", " ");
		endTime = endTime.replace("-", " ").replace("_", " ");

		try {
			// 날짜와 시간을 LocalTime 객체로 변환
			LocalTime startLocalTime = LocalTime.parse(startTime, formatter);
			LocalTime endLocalTime = LocalTime.parse(endTime, formatter);

			// 두 시간의 차이를 계산하여 반환
			return Duration.between(startLocalTime, endLocalTime);
		} catch (Exception e) {
			System.out.println("시간 파싱 오류: " + e.getMessage());
			return Duration.ZERO; // 오류 발생 시 0 반환
		}
	}

	// 시간 누적 계산
	public Duration totalDuration(String duration) {
		LocalTime time = LocalTime.parse(duration);

		return Duration.ofHours(time.getHour()).plusMinutes(time.getMinute()).plusSeconds(time.getSecond());
	}

	// 누적된 Duration을 "HH:mm:ss" 형식의 문자열로 변환
	public String transTimeFormat(Duration totalDuration) {
		long hours = totalDuration.toHours();
		long minutes = totalDuration.toMinutesPart();
		long seconds = totalDuration.toSecondsPart();

		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}

}
