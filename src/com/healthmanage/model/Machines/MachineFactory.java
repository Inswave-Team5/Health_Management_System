package com.healthmanage.model.Machines;

import com.healthmanage.model.Machine;

public abstract class MachineFactory {
    public abstract Machine createMachine(String machineId, int input);

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
    public Machine createMachine(String machineId, int input) {
        switch(input) {
            case 8:
                return new Treadmill(machineId, "러닝머신");
            case 7:
                return new StairClimber(machineId, "계단오름");
            case 9:
                return new StationaryBike(machineId, "실내자전거");

            default:
                return null;
        }
    }
}

// 구체적인 팩토리 (Strength)
class StrengthMachineFactory extends MachineFactory {
    @Override
    public Machine createMachine(String machineId, int input) {
        switch(input) {
            case 1:
                return new BenchPress(machineId, "벤치프레스");
            case 2:
                return new LatPullDown(machineId, "덤벨");  // 무게, 반복 횟수
            case 3:
                return new Dumbbell(machineId, "렛풀다운");  // 무게, 반복 횟수
            case 4:
                return new LegCurl(machineId, "레그컬");  // 무게, 반복 횟수
            case 10:
                return new LegPress(machineId, "레그프레스");  // 무게, 반복 횟수
            case 5:
                return new ShoulderPress(machineId, "숄더프레스");  // 무게, 반복 횟수
            case 6:
                return new SmithMachine(machineId, "스미스머신");  // 무게, 반복 횟수
            default:
                return null;
        }
    }
}