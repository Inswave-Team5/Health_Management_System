package com.healthmanage.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
	

}
