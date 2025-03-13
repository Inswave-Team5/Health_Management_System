package com.healthmanage.view.enums;

public enum CoinMenu {
    COIN_CHARGE(1, "코인 충전"),
    COIN_TRANSFER(2, "코인 선물"),
    BACK(0, "뒤로 가기");

    private final int number;
    private final String description;

    CoinMenu(int number, String description) {
        this.number = number;
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public static CoinMenu fromNumber(int number) {
        for (CoinMenu option : values()) {
            if (option.getNumber() == number) {
                return option;
            }
        }
        return null;
    }
}
