package com.healthmanage.model.Machines;

import com.healthmanage.model.Machine;

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

// 구체적인 팩토리 (Cardio)
class CardioMachineFactory extends MachineFactory {
    @Override
    public Machine createMachine(String machineId, String name) {
        switch(name.toLowerCase()) {
            case "treadmill":
                return new Treadmill(machineId, name);
            case "stairclimber":
                return new StairClimber(machineId, name);
            case "stationarybike":
                return new StationaryBike(machineId, name);

            default:
                return null;
        }
    }
}

// 구체적인 팩토리 (Strength)
class StrengthMachineFactory extends MachineFactory {
    @Override
    public Machine createMachine(String machineId, String name) {
        switch(name.toLowerCase()) {
            case "benchpress":
                return new BenchPress(machineId, name);
            case "dumbbell":
                return new LatPullDown(machineId, name);  // 무게, 반복 횟수
            case "latpulldown":
                return new Dumbbell(machineId, name);  // 무게, 반복 횟수
            case "legcurl":
                return new LegCurl(machineId, name);  // 무게, 반복 횟수
            case "legpress":
                return new LegPress(machineId, name);  // 무게, 반복 횟수
            case "shoulderpress":
                return new ShoulderPress(machineId, name);  // 무게, 반복 횟수
            case "smithmachine":
                return new SmithMachine(machineId, name);  // 무게, 반복 횟수
            default:
                return null;
        }
    }
}