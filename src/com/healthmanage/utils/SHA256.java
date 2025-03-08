package com.healthmanage.utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {

	public static String encrypt(String text) {
	    try {
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        md.update(text.getBytes());
	        return bytesToHex(md.digest());
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException("암호화 알고리즘 오류", e);
	    }
	}

	    private static String bytesToHex(byte[] bytes) {
	        StringBuilder builder = new StringBuilder();
	        for (byte b : bytes) {
	            builder.append(String.format("%02x", b));
	        }
	        return builder.toString();
	    }
}
