package com.healthmanage.model;

import java.util.HashMap;
import java.util.Map;

public class Gym {
	public static Map<String, User> users;
	public static Map<String, Admin> admins;
	public static Map<String, Coupon> coupons;
	//Map<String, Machine> machines;
	Person currentUser;
	
	Gym(){
		users = new HashMap<String, User>();
		admins = new HashMap<String, Admin>();
		coupons = new HashMap<String, Coupon>();
	}
}
