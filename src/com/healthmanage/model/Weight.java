package com.healthmanage.model;

public class Weight {

    public String userId;   // 로그인한 userId
    public String date;     // 날짜 - 입력당시 날짜 자동 기입
    public String weight;    // 몸무게

    public Weight(String userId, String date, String weight) {
        this.userId = userId;
        this.date = date;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "[날짜] " + date + "\t[몸무게] " + weight;
    }
}
