package com.healthmanage.model;

import java.io.Serializable;

public class Weight implements Serializable {

	private String userId;   // 로그인한 userId
    private String date;     // 날짜 - 입력당시 날짜 자동 기입
    private String weight;    // 몸무게

    public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Weight(String userId, String date, String weight) {
        this.userId = userId;
        this.date = date;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "[날짜] " + date + "\t[몸무게] " + weight + "kg";
    }
}
