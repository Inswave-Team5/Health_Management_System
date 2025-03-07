package com.healthmanage.dto;

public class UserSignUpDTO {
	private String userId;
	private String password;
	private String name;
	
	public UserSignUpDTO(String userId, String password, String name) {
		this.userId = userId;
		this.password = password;
		this.name = name;
	}
	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	
}
