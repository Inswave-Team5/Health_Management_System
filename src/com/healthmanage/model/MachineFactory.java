package com.healthmanage.model;

public abstract class MachineFactory {
    public abstract Machine createMachine(String machineId, String name);

    public static MachineFactory getFactory(String type) {
        if (type.equals("유산소")) {
            return new CardioMachineFactory();
        } else if (type.equals("근력")) {
            return new StrengthMachineFactory();
        }
        throw new IllegalArgumentException("알 수 없는 기구 타입: " + type);
    }
}

// 🎯 구체적인 팩토리 (Cardio)
class CardioMachineFactory extends MachineFactory {
    @Override
    public Machine createMachine(String machineId, String name) {
        return new CardioMachine(machineId, name);
    }
}

// 🎯 구체적인 팩토리 (Strength)
class StrengthMachineFactory extends MachineFactory {
    @Override
    public Machine createMachine(String machineId, String name) {
        return new StrengthMachine(machineId, name);
    }
}