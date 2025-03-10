package com.healthmanage.model.Equipments;

import com.healthmanage.model.Equipment;

public class SmithMachine extends Equipment {
    private int weight;
    private int repetitions;

    public SmithMachine(int weight, int repetitions) {
        this.name = "SmithMachine";
        this.weight = weight;
        this.repetitions = repetitions;
    }

    @Override
    public String toString() {
        return name + " - 무게: " + weight + "kg, 횟수: " + repetitions + "회";
    }
}
