package com.healthmanage.model.Equipments;

import com.healthmanage.model.Equipment;

// 무게별로 나누기..?
public class Dumbbell extends Equipment {
    private int weight;
    private int repetitions;

    public Dumbbell(int weight, int repetitions) {
        this.name = "Dumbbell";
        this.weight = weight;
        this.repetitions = repetitions;
    }

    @Override
    public String toString() {
        return name + " - 무게: " + weight + "kg, 횟수: " + repetitions + "회";
    }
}
