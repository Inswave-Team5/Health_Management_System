package com.healthmanage.controller;

import com.healthmanage.model.Gym;
import com.healthmanage.service.EquipmentService;
import com.healthmanage.view.UserView;
import com.healthmanage.view.View;

public class EquipmentController {
    private final EquipmentService equipmentService;
    private final UserView view;
    UserController userController;

    public EquipmentController() {
        equipmentService = EquipmentService.getInstance();
        view = new UserView();
    }

    public void equipmentEntry() {
        int key = 0;
        while (Gym.isLoggedIn() && (key = Integer.parseInt(view.EquipmentSelectMenu())) != 0) {
            System.out.println(key + "번 입력되었습니다.");
            switch (key) {
                case 1:
                    useEquipment();
                    break;
                case 2:
                    viewWorkoutHistory();
                    break;
                case 0:
                    break;
                default:
                    view.showMessage("잘못 선택하였습니다.");
                    break;
            }
        }
    }

    public void useEquipment() {
        if (Gym.isLoggedIn()) {
            view.showMessage("로그인이 필요합니다!");
            return;
        }

        String equipmentType = view.getInput("사용할 운동 기구 입력: ");
        int value1 = Integer.parseInt(view.getInput("속도 or 무게 입력: "));
        int value2 = Integer.parseInt(view.getInput("시간 or 횟수 입력: "));

        equipmentService.addWorkoutRecord(Gym.getCurrentUser().getUserId(), equipmentType, value1, value2);

        view.showMessage("운동기구 사용이 정상 기록되었습니다!");
    }

    public void viewWorkoutHistory() {
        if (!Gym.isLoggedIn()) {
            view.showMessage("로그인이 필요합니다!");
            return;
        }
        view.showMessage("[운동기구 사용기록]");
        equipmentService.getUserWorkoutHistory(Gym.getCurrentUser().getUserId());
    }
}
