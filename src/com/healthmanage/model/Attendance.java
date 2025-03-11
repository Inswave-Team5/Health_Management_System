package com.healthmanage.model;


import java.util.Date;

public class Attendance {
    private String userId;
    private String date;
    private String enterTime;
    private String leaveTime;
    private String workOutTime;
    //private String status; - 현재 출근중인지 상태확인(부가기능)

    public Attendance(String userId, String date, String enterTime) {
        this.userId = userId;
        this.date = date;
        this.enterTime = enterTime;
        this.leaveTime = "";
        this.workOutTime = "";
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

    public String toStringAttendacne(){ return "[입장시간] " + enterTime + "\t[퇴장시간] " + leaveTime;}
}
