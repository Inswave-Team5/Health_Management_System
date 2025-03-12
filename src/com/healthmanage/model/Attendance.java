package com.healthmanage.model;

import java.io.Serializable;

import com.healthmanage.utils.Time;

public class Attendance implements Serializable {
	private String userId;
	private String date;
	private String enterTime;
	private String leaveTime;
	private String workOutTime;
	private Time time;
	// private String status; - 현재 출근중인지 상태확인(부가기능)

	public Attendance(String userId, String date, String enterTime) {
		this.userId = userId;
		this.date = date;
		this.enterTime = enterTime;
		this.leaveTime = "";
		this.workOutTime = "";
		this.time = Time.getInstance();
	}

	public String getUserId() {
		return userId;
	}

	public String getDate() {
		return date;
	}

	public String getEnterTime() {
		return enterTime;
	}

	public String getLeaveTime() {
		return leaveTime;
	}

	public String getWorkOutTime() {
		return workOutTime;
	}

	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

	public void setWorkOutTime(String workOutTime) {
		this.workOutTime = workOutTime;
	}

	public String toStringWorkOut() {
		return "[날짜] " + date + "\t[운동시간] " + workOutTime;
	}

	public String toStringAttendacne() {
		return "[입장시간] " + time.getYearTimeFromString(enterTime) + "\t[퇴장시간] " + time.getYearTimeFromString(leaveTime);
	}
}
