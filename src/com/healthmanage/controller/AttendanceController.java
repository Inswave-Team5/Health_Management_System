package com.healthmanage.controller;

import com.healthmanage.app.LoginUser;
import com.healthmanage.service.AttendanceService;
import com.healthmanage.view.View;


public class AttendanceController {
    AttendanceService attendanceService;
    String userId = LoginUser.getLoginUserId();
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
        if(LoginUser.isLogin()){
            view.showMessage("로그인 후 이용가능합니다!");
            return;
        }
        attendanceService.setEnterTime(userId);
        view.showMessage("입장이 완료되었습니다!");
    }

    public void setLeaveTime(){
        String userId = LoginUser.getLoginUserId();
        if(LoginUser.isLogin()){
            view.showMessage("로그인 후 이용가능합니다!");
            return;
        }
        attendanceService.setLeaveTime(userId);
        view.showMessage("퇴장이 완료되었습니다!");
    }

    //전체 입/퇴장 기록 조회
    public void listAttendanceAll(){
        String userId = LoginUser.getLoginUserId();
        if(LoginUser.isLogin()){
            view.showMessage("로그인 후 이용가능합니다!");
            return;
        }
        attendanceService.listAttendanceAll(userId);
    }

    //전체 누적 운동시간 조회
    public void getWorkOutTime(){
        String userId = LoginUser.getLoginUserId();
        if(LoginUser.isLogin()){
            view.showMessage("로그인 후 이용가능합니다!");
            return;
        }
        String totalTime = attendanceService.getTotalWorkOutTime(userId);
        view.showMessage("[전체 누적 운동시간]" + totalTime);
    }


}
