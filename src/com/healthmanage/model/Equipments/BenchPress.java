package com.healthmanage.model.Equipments;

import com.healthmanage.model.Equipment;

public class BenchPress extends Equipment {
    private int weight;
    private int repetitions;

    public BenchPress(int weight, int repetitions) {
        this.name = "BenchPress";
        this.weight = weight;
        this.repetitions = repetitions;
    }

    @Override
    public String toString() {
        return name + " - 무게: " + weight + "kg, 횟수: " + repetitions + "회";
    }
}