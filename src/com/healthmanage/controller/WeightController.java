package com.healthmanage.controller;

import com.healthmanage.app.LoginUser;
import com.healthmanage.service.WeightService;
import com.healthmanage.view.View;

public class WeightController {
    private WeightService weightService;
    private View view;
    //로그인한 userid 불러오기
    String userId = LoginUser.getLoginUserId();

    public WeightController() {
        this.weightService = WeightService.getInstance();
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

    public void addWeight(){
        //로그인 검증
        if(LoginUser.isLogin()){
            view.showMessage("로그인 후 이용가능합니다!");
            return;
        }

        String weight = view.getInput("몸무게 입력 : ");
        weightService.addWeight(userId, weight);
    }

    public void ListWeight(){
        //로그인 검증
        if(LoginUser.isLogin()){
            view.showMessage("로그인 후 이용가능합니다!");
            return;
        }
        weightService.ListWeight(userId);
    }

    public void ListWeightByMonth(){
        //로그인 검증
        if(LoginUser.isLogin()){
            view.showMessage("로그인 후 이용가능합니다!");
            return;
        }
        String month = (view.getInput("월 입력 (입력 형식 : MM) : "));
        weightService.ListWeightByMonth(userId, month);
    }

    public void ListWeightByDay(){
        //로그인 검증
        if(LoginUser.isLogin()){
            view.showMessage("로그인 후 이용가능합니다!");
            return;
        }
        String day = (view.getInput("날짜 입력 (입력 형식 : MM-dd) : "));
        weightService.ListWeightByMonth(userId, day);
    }

}
