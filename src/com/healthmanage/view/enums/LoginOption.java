package com.healthmanage.view.enums;

public enum LoginOption {
    LOGIN(1, "로그인"),
    SIGN_UP(2, "회원가입"),
    EXIT(0, "종료");

    private final int number;
    private final String description;

    LoginOption(int number, String description) {
        this.number = number;
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public static LoginOption fromNumber(int number) {
        for (LoginOption option : values()) {
            if (option.getNumber() == number) {
                return option;
            }
        }
        return null;
    }
}
