package com.healthmanage.service;

import com.healthmanage.app.LoginUser;
import com.healthmanage.model.Attendance;
import com.healthmanage.view.View;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class AttendanceSerive {
    View view = new View();
    private Map<String, List<Attendance>> attendanceList = new HashMap<>(); //user 의 출근시간 기록

    //출근 시간 기록
    public void setEnterTime(){
        String userId = LoginUser.getLoginUserId();
        if(LoginUser.isLogin()){
            view.showMessage("로그인 후 이용가능합니다!");
            return;
        }

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Date enterTime = new Date();
        Attendance attendance = new Attendance(userId, date, enterTime);

        //user 의 Attendance 객체를 리스트에 담기
        attendanceList.putIfAbsent(userId, new ArrayList<Attendance>()); // 기존에 없으면 새로운 리스트 생성-맵에 추가
        attendanceList.get(userId).add(attendance);

        view.showMessage("출근이 롼료되었습니다!");
    }

    //퇴근 시간 기록
    public void setLeaveTime(){
        String userId = LoginUser.getLoginUserId();
        if(LoginUser.isLogin()){
            view.showMessage("로그인 후 이용가능합니다!");
            return;
        }

        List<Attendance> userAttendanceList = attendanceList.get(userId);
        if(userAttendanceList == null || userAttendanceList.isEmpty()){
            view.showMessage("입장 기록이 없습니다.");
            view.showMessage("입장을 먼저 해주세요!");
        }
        if(userAttendanceList.get(userAttendanceList.size()-1).getLeaveTime() != null){
            view.showMessage("입장 기록이 없습니다.");
            view.showMessage("입장을 먼저 해주세요!");
        }

        Date leaveTime = new Date();
        Attendance lastAttendance = userAttendanceList.get(userAttendanceList.size()-1);
        lastAttendance.setLeaveTime(leaveTime);

        // 입장 시간과 퇴근 시간의 차이를 계산
        long durationMillis = leaveTime.getTime() - lastAttendance.getEnterTime().getTime();

        // Calendar를 사용하여 차이를 시간과 분으로 계산
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(durationMillis);
        int workOutHours = cal.get(Calendar.HOUR_OF_DAY); // 차이 시간 (시)
        int workOutMinutes = cal.get(Calendar.MINUTE); // 차이 분
        String workOutTime = String.format("%02d:%02d", workOutHours, workOutMinutes);

        lastAttendance.setWorkOutTime(workOutTime);
    }

    public List<Attendance> listAttendance(){
        String userId = LoginUser.getLoginUserId();

        return null;
    }
}
