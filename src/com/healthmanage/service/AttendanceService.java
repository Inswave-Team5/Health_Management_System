package com.healthmanage.service;

import com.healthmanage.app.LoginUser;
import com.healthmanage.model.Attendance;
import com.healthmanage.utils.Time;
import com.healthmanage.view.View;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class AttendanceService {
    View view = new View();
    private static AttendanceService instance;
    private Map<String, List<Attendance>> attendanceList = new HashMap<>(); //user 의 출근시간 기록
    Time time = Time.getInstance();

    public static AttendanceService getInstance() {
        if (instance == null) {
            instance = new AttendanceService();
        }
        return instance;
    }

    //출근 시간 기록
    public void setEnterTime(String userId){
        String date = time.currentDay().substring(0, 10);
        String enterTime = time.currentDayAndTime().substring(13, 21);
        Attendance attendance = new Attendance(userId, date, enterTime);

        //user 의 Attendance 객체를 리스트에 담기
        attendanceList.putIfAbsent(userId, new ArrayList<Attendance>()); // 기존에 없으면 새로운 리스트 생성-맵에 추가
        attendanceList.get(userId).add(attendance);
    }

    //퇴근 시간 기록
    public void setLeaveTime(String userId){
        List<Attendance> userAttendanceList = attendanceList.get(userId);
        if(userAttendanceList == null || userAttendanceList.isEmpty()){
            view.showMessage("입장 기록이 없습니다.");
            view.showMessage("입장을 먼저 해주세요!");
        }
        if(!Objects.equals(userAttendanceList.get(userAttendanceList.size() - 1).getLeaveTime(), "")){
            view.showMessage("입장 기록이 없습니다.");
            view.showMessage("입장을 먼저 해주세요!");
        }

        String leaveTime = time.currentDayAndTime().substring(13, 21);
        Attendance lastAttendance = userAttendanceList.get(userAttendanceList.size()-1);
        lastAttendance.setLeaveTime(leaveTime);

        // 입장-퇴장 시간 차이 계산
        LocalTime leaveLocalTime = LocalTime.parse(leaveTime);
        LocalTime enterLocalTime = LocalTime.parse(leaveTime);
        Duration duration = Duration.between(enterLocalTime, leaveLocalTime);

        // 차이를 "HH:mm:ss" 형식의 문자열로 변환
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        String workoutTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        lastAttendance.setWorkOutTime(workoutTime);
    }


//    //월별 입/퇴장 기록 조회
//
//    //일별 입/퇴장 기록 조회

    //전체 운동시간 기록 조회(전체 출결 조회)
    public void listAttendanceAll(String userId){
        List<Attendance> userAttendanceList = attendanceList.get(userId);
        view.showMessage("[전체 운동시간 기록]");
        for(Attendance attendance : userAttendanceList){
            System.out.println(attendance.toString());
        }
    }

    //전체 누적 운동시간 조회
    public String getTotalWorkOutTime(String userId){
        Duration totalDuration = Duration.ZERO; // (계산을 위한)누적 시간 초기화
        List<Attendance> userAttendanceList = attendanceList.get(userId);
        if (userAttendanceList != null) {
            for (Attendance attendance : userAttendanceList) {
                String workOutTime = attendance.getWorkOutTime(); // "HH:mm:ss" 형식의 문자열
                LocalTime time = LocalTime.parse(workOutTime); // LocalTime 변환
                totalDuration = totalDuration.plusHours(time.getHour())
                        .plusMinutes(time.getMinute())
                        .plusSeconds(time.getSecond());
            }
        }
        // 누적된 Duration을 "HH:mm:ss" 형식의 문자열로 변환
        long hours = totalDuration.toHours();
        long minutes = totalDuration.toMinutesPart();
        long seconds = totalDuration.toSecondsPart();

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    //단일(날짜 선택) 운동시간 조회
    public String getDayWorkOutTime(String userId, String day){
        List<Attendance> userAttendanceList = attendanceList.get(userId);
        String dayWorkOutTime = "";
        if (userAttendanceList != null) {
            for (Attendance attendance : userAttendanceList) {
                if(attendance.getDate().equals(day)){
                    dayWorkOutTime = attendance.getWorkOutTime();
                }
            }
        }
        return dayWorkOutTime;
    }

    //월별 누적 운동시간 조회
    public String getMonthTotalWorkOutTime(String userId, String month){
        Duration totalDuration = Duration.ZERO; // (계산을 위한)누적 시간 초기화
        List<Attendance> userAttendanceList = attendanceList.get(userId);
        String monthWorkOutTime = "";
        if (userAttendanceList != null) {
            for (Attendance attendance : userAttendanceList) {
                if (attendance.getDate().substring(5, 7).equals(month)) {
                    monthWorkOutTime = attendance.getWorkOutTime();
                    LocalTime time = LocalTime.parse(monthWorkOutTime);
                    totalDuration = totalDuration.plusHours(time.getHour())
                            .plusMinutes(time.getMinute())
                            .plusSeconds(time.getSecond());
                }
            }

        }
        // 누적된 Duration을 "HH:mm:ss" 형식의 문자열로 변환
        long hours = totalDuration.toHours();
        long minutes = totalDuration.toMinutesPart();
        long seconds = totalDuration.toSecondsPart();

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

}
