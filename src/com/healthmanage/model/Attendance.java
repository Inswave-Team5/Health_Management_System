package com.healthmanage.model;


import java.util.Date;

public class Attendance {
    private String userId;
    private String date;
    private Date enterTime;
    private Date leaveTime;
    private String workOutTime;
    //private String status; - 현재 출근중인지 상태확인(부가기능)

    public Attendance(String userId, String date, Date enterTime) {
        this.userId = userId;
        this.date = date;
        this.enterTime = enterTime;
        this.leaveTime = null;
        this.workOutTime = null;
    }

    public String getUserId() {
        return userId;
    }

    public String getDate() {
        return date;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public String getWorkOutTime() {
        return workOutTime;
    }


    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public void setWorkOutTime(String workOutTime) {
        this.workOutTime = workOutTime;
    }
}
