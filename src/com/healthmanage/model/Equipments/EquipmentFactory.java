package com.healthmanage.model.Equipments;

import com.healthmanage.model.Equipment;

public class EquipmentFactory {

    public static Equipment createEquipment(String type, int value1, int value2) {
        switch (type.toLowerCase()) { //대소문자 구분x - 니증에 목록으로 번호입력...
            case "treadmill":
                return new Treadmill(value1, value2);  // 속도, 시간
            case "benchpress":
                return new BenchPress(value1, value2);  // 무게, 반복 횟수
            case "dumbbell":
                return new LatPullDown(value1, value2);  // 무게, 반복 횟수
            case "latpulldown":
                return new LatPullDown(value1, value2);  // 무게, 반복 횟수
            case "legcurl":
                return new LegCurl(value1, value2);  // 무게, 반복 횟수
            case "legpress":
                return new LegPress(value1, value2);  // 무게, 반복 횟수
            case "shoulderpress":
                return new ShoulderPress(value1, value2);  // 무게, 반복 횟수
            case "smithmachine":
                return new SmithMachine(value1, value2);  // 무게, 반복 횟수
            case "stairclimber":
                return new StairClimber(value1, value2);  // 속도, 시간
            case "stationarybike":
                return new StationaryBike(value1, value2);  // 속도, 시간
            default:
                return null;
        }
    }
}
