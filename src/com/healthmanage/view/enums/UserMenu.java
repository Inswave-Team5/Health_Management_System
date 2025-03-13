package com.healthmanage.view.enums;

public enum UserMenu {
    CHECK_IN(1, "나의 출결"),
    WORKOUT_TIME(2, "운동 시간"),
    WEIGHT_MANAGEMENT(3, "체중 관리"),
    MACHINE_USE(4, "운동 기구"),
    COUPON_USE(5, "쿠폰 사용"),
    COIN_MANAGEMENT(6, "코인 관리"),
    PASSWORD_CHANGE(7, "비밀번호 변경"),
    LOGOUT(0, "로그아웃");

    private final int number;
    private final String description;

    UserMenu(int number, String description) {
        this.number = number;
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public static UserMenu fromNumber(int number) {
        for (UserMenu option : values()) {
            if (option.getNumber() == number) {
                return option;
            }
        }
        return null;
    }
}
