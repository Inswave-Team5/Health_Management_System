package com.healthmanage.model.Equipments;

import com.healthmanage.model.Equipment;

public class ShoulderPress extends Equipment {
    private int weight;
    private int repetitions;

    public ShoulderPress(int weight, int repetitions) {
        this.name = "ShoulderPress";
        this.weight = weight;
        this.repetitions = repetitions;
    }

    @Override
    public String toString() {
        return name + " - 무게: " + weight + "kg, 횟수: " + repetitions + "회";
    }
}
