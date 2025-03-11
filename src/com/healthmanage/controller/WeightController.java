package com.healthmanage.controller;

import com.healthmanage.model.Gym;
import com.healthmanage.service.WeightService;
import com.healthmanage.view.UserView;


public class WeightController {
    private final WeightService weightService;
    private final UserView view;
    UserController userController;

    public WeightController() {

        this.weightService = WeightService.getInstance();
        this.view = new UserView();
    }

    public void weightEntry() {
        int key = 0;
        while (Gym.isLoggedIn() && (key = Integer.parseInt(view.WeightSelectMenu())) != 0) {
            switch (key) {
                case 1:
                    addWeight();
                    break;
                case 2:
                    checkEntry();
                    break;
                case 0:
                    return;
                default:
                    view.showMessage("잘못 선택하였습니다.");
                    break;
            }
        }
    }

    public void checkEntry() {
        int key = 0;
        while (Gym.isLoggedIn() && (key = Integer.parseInt(view.WeightSelectCheckMenu())) != 0) {
            switch (key) {
                case 1:
                    listWeight();
                    break;
                case 2:
                    listWeightByMonth();
                    break;
                case 3:
                    listWeightByDay();
                    break;
                case 0:
                    weightEntry();
                    break;
                default:
                    view.showMessage("잘못 선택하였습니다.");
                    break;
            }
        }
    }

    public void addWeight(){
        //로그인 검증
        if(!Gym.isLoggedIn()){
            view.showMessage("로그인 후 이용가능합니다!");
            return;
        }

        String weight = view.getInput("몸무게 입력 : ");
        weightService.addWeight(Gym.getCurrentUser().getUserId(), weight);
    }

    public void listWeight(){
        //로그인 검증
        if(!Gym.isLoggedIn()){
            view.showMessage("로그인 후 이용가능합니다!");
            return;
        }
        weightService.ListWeight(Gym.getCurrentUser().getUserId());
    }

    public void listWeightByMonth(){
        //로그인 검증
        if(!Gym.isLoggedIn()){
            view.showMessage("로그인 후 이용가능합니다!");
            return;
        }
        String month = (view.getInput("월 입력 (입력 형식 : yyyy-MM) : "));
        weightService.ListWeightByMonth(Gym.getCurrentUser().getUserId(), month);
    }

    public void listWeightByDay(){
        //로그인 검증
        if(!Gym.isLoggedIn()){
            view.showMessage("로그인 후 이용가능합니다!");
            return;
        }
        String day = (view.getInput("날짜 입력 (입력 형식 : yyyy-MM-dd) : "));
        weightService.ListWeightByMonth(Gym.getCurrentUser().getUserId(), day);
    }

}
