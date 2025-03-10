package com.healthmanage.model.Equipments;

import com.healthmanage.model.Equipment;

public class LegPress extends Equipment {
    private int weight;
    private int repetitions;

    public LegPress(int weight, int repetitions) {
        this.name = "LegPress";
        this.weight = weight;
        this.repetitions = repetitions;
    }

    @Override
    public String toString() {
        return name + " - 무게: " + weight + "kg, 횟수: " + repetitions + "회";
    }
}
