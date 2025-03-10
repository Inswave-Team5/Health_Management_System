package com.healthmanage.controller;

import com.healthmanage.app.LoginUser;
import com.healthmanage.service.EquipmentService;
import com.healthmanage.view.View;

public class EquipmentController {
    private EquipmentService equipmentService;
    private View view;

    String userId = LoginUser.getLoginUserId();

    public EquipmentController() {
        equipmentService = EquipmentService.getInstance();
        view = new View();
    }

    public void useEquipment() {
        if (LoginUser.isLogin()) {
            view.showMessage("로그인이 필요합니다!");
            return;
        }

        String equipmentType = view.getInput("사용할 운동 기구 입력: ");
        int value1 = Integer.parseInt(view.getInput("속도 or 무게 입력: "));
        int value2 = Integer.parseInt(view.getInput("시간 or 횟수 입력: "));

        equipmentService.addWorkoutRecord(userId, equipmentType, value1, value2);

        view.showMessage("운동기구 사용이 정상 기록되었습니다!");
    }

    public void viewWorkoutHistory() {
        if (LoginUser.isLogin()) {
            view.showMessage("로그인이 필요합니다!");
            return;
        }
        view.showMessage("[운동기구 사용기록]");
        equipmentService.getUserWorkoutHistory(userId);
    }
}
