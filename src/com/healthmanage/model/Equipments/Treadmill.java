package com.healthmanage.model.Equipments;

import com.healthmanage.model.Equipment;

public class Treadmill extends Equipment {
    private int speed;
    private int duration;

    public Treadmill(int speed, int duration) {
        this.name = "Treadmill";
        this.speed = speed;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return name + " - 속도: " + speed + "km/h, 시간: " + duration + "분";
    }
}