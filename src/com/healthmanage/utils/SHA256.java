package com.healthmanage.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SHA256 {

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

	// 비밀번호 검증
	public static boolean verifyPassword(String inputPassword, String storedSalt, String storedHash) {
		String hashedInput = hashPassword(inputPassword, storedSalt);
		return storedHash.equals(hashedInput);
	}
}
