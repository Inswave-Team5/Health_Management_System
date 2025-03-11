package com.healthmanage.model;

import java.util.HashMap;
import java.util.Map;

public class Gym {
	public static Map<String, User> users;
	public static Map<String, Admin> admins;
	public static Map<String, Coupon> coupons;
	// Map<String, Machine> machines;
	public static Person currentUser;

	Gym() {
		/*users = new HashMap<String, User>();
		admins = new HashMap<String, Admin>();
		coupons = new HashMap<String, Coupon>();*/
	}

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
