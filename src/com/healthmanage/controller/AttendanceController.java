package com.healthmanage.controller;

import com.healthmanage.model.Gym;
import com.healthmanage.service.AttendanceService;
import com.healthmanage.utils.Time;
import com.healthmanage.view.UserView;

public class AttendanceController {
    private final AttendanceService attendanceService;
    private final UserView view;
    Time time;


    public AttendanceController() {
        attendanceService = AttendanceService.getInstance();
        this.view = new UserView();
        this.time = Time.getInstance();
    }

    public void attendanceEntry() {
        int key=0;
        while (Gym.isLoggedIn() && (key = Integer.parseInt(view.AttendanceSelectMenu())) != 0) {
            view.showMessage(key + "번 입력되었습니다.");
            switch (key) {
                case 1:
                    setEnterTime();
                    break;
                case 2:
                    setLeaveTime();
                    break;
                case 3:
                    listAttendanceAll();
                    break;
                case 0:
                    return;
                default:
                    view.showMessage("잘못 선택하였습니다.");
                    break;
            }
        }
    }


    public void timeEntry() {
        int key=0;
        while (Gym.isLoggedIn() && (key = Integer.parseInt(view.TimeSelectMenu())) != 0) {

            view.showMessage(key + "번 입력되었습니다.");

            switch (key) {
                case 1:
                    getTodayWorkOutTime();
                    break;
                case 2:
                    getDayWorkOutTime();
                    break;
                case 3:
                    getMonthTotalWorkOutTime();
                    break;
                case 4:
                    getWorkOutTime();
                    break;
                case 0:
                    return;
                default:
                    view.showMessage("잘못 선택하였습니다.");
                    break;
            }
        }
    }



    public void setEnterTime(){
        if(!Gym.isLoggedIn()) {
            view.showMessage("로그인 후 이용가능합니다!");
            return;
        }
        attendanceService.setEnterTime(Gym.getCurrentUser().getUserId());
        view.showMessage("입장이 완료되었습니다!");
    }

    public void setLeaveTime(){
        if(!Gym.isLoggedIn()){
            view.showMessage("로그인 후 이용가능합니다!");
            return;
        }
        attendanceService.setLeaveTime(Gym.getCurrentUser().getUserId());
        view.showMessage("퇴장이 완료되었습니다!");
    }

    //전체 입/퇴장 기록 조회
    public void listAttendanceAll(){
        if(!Gym.isLoggedIn()){
            view.showMessage("로그인 후 이용가능합니다!");
            return;
        }
        attendanceService.listAttendanceAll(Gym.getCurrentUser().getUserId());
    }

    //전체 누적 운동시간 조회
    public void getWorkOutTime(){
        if(!Gym.isLoggedIn()){
            view.showMessage("로그인 후 이용가능합니다!");
            return;
        }
        String totalTime = attendanceService.getTotalWorkOutTime(Gym.getCurrentUser().getUserId());
        view.showMessage("[전체 누적 운동시간]" + totalTime);
    }

    //오늘 운동시간 조회
    public void getTodayWorkOutTime(){
        if(!Gym.isLoggedIn()){
            view.showMessage("로그인 후 이용가능합니다!");
            System.exit(0);
        }
        String today = time.currentDay();
        view.showMessage("[오늘의 운동시간] " + attendanceService.getDayWorkOutTime(Gym.getCurrentUser().getUserId(), today)); ;
    }

    //단일(날짜 선택) 운동시간 조회
    public void getDayWorkOutTime(){
        if(!Gym.isLoggedIn()){
            view.showMessage("로그인 후 이용가능합니다!");
            System.exit(0);
        }
        String day = view.getInput("날짜 입력 (입력 형식 : MM-dd) : ");
        String workOutTimeByDay = attendanceService.getDayWorkOutTime(Gym.getCurrentUser().getUserId(), day);
        view.showMessage("["+day+"]" + workOutTimeByDay);
    }

    //월별 누적 운동시간 조회
    public void getMonthTotalWorkOutTime(){
        if(!Gym.isLoggedIn()){
            view.showMessage("로그인 후 이용가능합니다!");
            System.exit(0);
        }
        String month = (view.getInput("월 입력 (입력 형식 : yyyy-MM) : "));
        String workOutTimeByMonth = attendanceService.getMonthTotalWorkOutTime(Gym.getCurrentUser().getUserId(), month);
        view.showMessage("[" + month + "월 누적 운동시간]" + workOutTimeByMonth);
    }

}
