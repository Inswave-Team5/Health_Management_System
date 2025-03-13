package com.healthmanage.model;

import java.util.HashMap;
import java.util.Map;

public class Gym {
	public static Map<String, User> users;
	public static Map<String, Admin> admins;
	public static Map<String, Coupon> coupons;
	public static Map<String, Machine> machines;
	private static Person currentUser;

	Gym() {}

	public static void setCurrentUser(Person user) {
		currentUser = user;
	}

	public static Person getCurrentUser() {
		return currentUser;
	}

	public static boolean isLoggedIn() {
		return currentUser != null;
	}

	public static void logoutUser() {
		setCurrentUser(null);
	}
}
