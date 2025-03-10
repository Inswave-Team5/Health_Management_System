package com.healthmanage.model.Equipments;

import com.healthmanage.model.Equipment;

public class StairClimber extends Equipment {
    private int speed;
    private int duration;

    public StairClimber(int speed, int duration) {
        this.name = "StairClimber";
        this.speed = speed;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return name + " - 속도: " + speed + "km/h, 시간: " + duration + "분";
    }
}
