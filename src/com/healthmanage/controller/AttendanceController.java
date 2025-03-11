package com.healthmanage.controller;

import com.healthmanage.model.Gym;
import com.healthmanage.service.AttendanceService;
import com.healthmanage.view.View;


public class AttendanceController {
    AttendanceService attendanceService;
    String userId = Gym.getCurrentUser().getUserId();
    private View view;

    public AttendanceController() {
        attendanceService = AttendanceService.getInstance();
        this.view = new View();
    }

    public void start() {
        int key = 0;
        while ((key = Integer.parseInt(view.selectMenu())) != 0) {
            switch (key) {
                /*
                 * case 1: addBook(); break; case 2: removeBook(); break; case 3: searchBook();
                 * break; case 4: listBook(); break; case 5: listISBN(); break; case 6: save();
                 * break; case 7: load(); break;
                 */
                default:
                    System.out.println("잘못 선택하였습니다.");
                    break;
            }
        }
        System.out.println("종료합니다...");
    }

    public void setEnterTime(){
        if(Gym.isLoggedIn()){
            view.showMessage("로그인 후 이용가능합니다!");
            return;
        }
        attendanceService.setEnterTime(userId);
        view.showMessage("입장이 완료되었습니다!");
    }

    public void setLeaveTime(){
        String userId = Gym.getCurrentUser().getUserId();
        if(Gym.isLoggedIn()){
            view.showMessage("로그인 후 이용가능합니다!");
            return;
        }
        attendanceService.setLeaveTime(userId);
        view.showMessage("퇴장이 완료되었습니다!");
    }

    //전체 입/퇴장 기록 조회
    public void listAttendanceAll(){
        if(Gym.isLoggedIn()){
            view.showMessage("로그인 후 이용가능합니다!");
            return;
        }
        attendanceService.listAttendanceAll(userId);
    }

    //전체 누적 운동시간 조회
    public void getWorkOutTime(){
        if(Gym.isLoggedIn()){
            view.showMessage("로그인 후 이용가능합니다!");
            return;
        }
        String totalTime = attendanceService.getTotalWorkOutTime(userId);
        view.showMessage("[전체 누적 운동시간]" + totalTime);
    }

    //단일(날짜 선택) 운동시간 조회
    public void getDayWorkOutTime(){
        if(Gym.isLoggedIn()){
            view.showMessage("로그인 후 이용가능합니다!");
            System.exit(0);
        }
        String day = view.getInput("날짜 입력 (입력 형식 : MM-dd) : ");
        String workOutTimeByDay = attendanceService.getDayWorkOutTime(userId, day);
        view.showMessage("["+day+"]" + workOutTimeByDay);
    }

    //월별 누적 운동시간 조회
    public void getMonthTotalWorkOutTime(String userId){
        if(Gym.isLoggedIn()){
            view.showMessage("로그인 후 이용가능합니다!");
            System.exit(0);
        }
        String month = (view.getInput("월 입력 (입력 형식 : yyyy-MM) : "));
        String workOutTimeByMonth = attendanceService.getMonthTotalWorkOutTime(userId, month);
        view.showMessage("[" + month + "월 누적 운동시간]" + workOutTimeByMonth);
    }

}
