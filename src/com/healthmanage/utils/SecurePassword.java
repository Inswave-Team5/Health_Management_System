package com.healthmanage.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.healthmanage.config.EnvConfig;

public class SecurePassword {

	// 비밀번호 해싱 (SHA-256 + Salt)
	public static String hashPassword(String password, String salt) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(salt.getBytes());
			byte[] hashedBytes = md.digest(password.getBytes());
			return Base64.getEncoder().encodeToString(hashedBytes);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("SHA-256 해싱 오류", e);
		}
	}

	// 랜덤 Salt 생성
	public static String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	/*
	// 비밀번호 및 Salt 저장
	public static void savePassword(String salt, String hashedPassword, boolean isAdmin) {
		/*
		 * Map<String, String> data = new HashMap<>(); //왜 map을 사용해야하는지?
		 * data.put("salt", salt); //salt라고 키를 지정하고, salt값을 value로 넣은 것?
		 * data.put("hashedPassword", hashedPassword);
		 * 
		 * //if 문?으로 user , admin 구분 FileIO.createFile(EnvConfig.get("USER_FILE"));
		 * //user_File에만 저장? ADMIN_FILE도 저장할 수 있도록 해야할듯 FileIO.infoSave(data,
		 * EnvConfig.get("USER_FILE"));
		 
		Map<String, String> data = new HashMap<>();
		data.put("salt", salt);
		data.put("hashedPassword", hashedPassword);

		// isAdmin 여부에 따라 저장할 파일 결정
		String filePath = isAdmin ? EnvConfig.get("ADMIN_FILE") : EnvConfig.get("USER_FILE");

		// 파일 생성 후 저장
		FileIO.createFile(filePath);
		FileIO.infoSave(data, filePath);

	}

	// 저장된 데이터 불러오기
	public static Map<String, String> loadPassword(boolean isAdmin) {
		// 이것도 admin이랑 user 구분해야 할듯
		String filePath = isAdmin ? EnvConfig.get("ADMIN_FILE") : EnvConfig.get("USER_FILE");
		return FileIO.infoLoad(filePath, String.class);
	}
	
	*/

	// 비밀번호 검증
	public static boolean verifyPassword(String inputPassword, String storedSalt, String storedHash) {
		String hashedInput = hashPassword(inputPassword, storedSalt);
		return storedHash.equals(hashedInput);
	}
}
