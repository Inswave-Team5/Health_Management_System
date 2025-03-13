package com.healthmanage.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {

	// 양의 실수 및 정수 입력을 검증
	public static boolean validatePositiveDecimal(String input) {
		String regex = "^[1-9]\\d*(\\.\\d+)?$"; // 정수 또는 소수
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}

	// 숫자 형식 입력을 검증
	public static boolean validNumber(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// 년도-월 yyyy-MM 입력을 검증
	public static boolean validateYearMonth(String input) {
		String regex = "^(19|20)\\d{2}-(0[1-9]|1[0-2])$"; // 정수 또는 소수
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}

	// 년도-월 yyyy-MM-dd 입력을 검증
	public static boolean validateYearMonthDay(String input) {
		String regex = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$"; // 정수 또는 소수
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}

	// 머신 타입 입력 검증 (유산소/근력)
	public static boolean validateMachineType(String input) {
		String regex = "^(유산소|근력)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}

}
