package com.healthmanage.model.Equipments;

import com.healthmanage.model.Equipment;

public class LegCurl extends Equipment {
    private int weight;
    private int repetitions;

    public LegCurl(int weight, int repetitions) {
        this.name = "LegCurl";
        this.weight = weight;
        this.repetitions = repetitions;
    }

    @Override
    public String toString() {
        return name + " - 무게: " + weight + "kg, 횟수: " + repetitions + "회";
    }
}
