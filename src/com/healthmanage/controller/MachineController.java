package com.healthmanage.controller;

import com.healthmanage.dto.MachineUsageDTO;
import com.healthmanage.model.Gym;
import com.healthmanage.model.Machine;
import com.healthmanage.service.MachineService;
import static com.healthmanage.utils.Validations.*;
import com.healthmanage.view.UserView;

import java.util.List;

public class MachineController {
    private final MachineService machineService;
    private UserView view;


    public MachineController() {
        this.machineService = MachineService.getInstance();
        this.view = new UserView();
    }

    public void machineEntry() {
        int key = 0;
        while (Gym.isLoggedIn()) {
        	try {
        		key = Integer.parseInt(view.machineSelectMenu());        		
        	}catch (NumberFormatException e) {
        		view.showAlert("숫자로된 메뉴 번호를 입력해주세요");
				continue;
        	}
            view.showAlert(key + "번 입력되었습니다.");
            switch (key) {
                case 1:
                    if (listMachine()) { // 기구가 있을 때만 machineListEntry 호출
                        machineListEntry();
                    }
                    break;
                case 2:
                    getMachineUsage();
                    break;
                case 0:
                	view.showAlert("종료합니다.");
                	machineService.save();
    				return;
                default:
                    view.showAlert("잘못 선택하였습니다.");
                    break;
            }
        }
    }

    public void machineListEntry() {
        int key = 0;
        while (Gym.isLoggedIn()) {
        	try {
        		key = Integer.parseInt(view.machineListInsideMenu());        		        		
        	}catch(NumberFormatException e) {
        		view.showAlert("숫자로된 메뉴 번호를 입력해주세요");
				continue;
        	}
            view.showAlert(key + "번 입력되었습니다.");
            switch (key) {
                case 1:
                    startUsingMachine();
                    break;
                case 2:
                    stopUsingMachine();
                    break;
                case 0:
                	machineService.save();
                	view.showAlert("종료합니다.");
    				return;
                default:
                    view.showAlert("잘못 선택하였습니다.");
                    break;
            }
        }
    }

    public boolean listMachine() {
        view.showMessage("[헬스장 머신 현황]");
        List<Machine> machines = machineService.listMachines();
        if (machines.isEmpty()) {
            view.showAlert("등록된 머신이 없습니다.");
            return false; // 머신이 없을 경우 false 반환
        }
        for (Machine machine : machines) {
            view.showMessage(machine.toString());
        }
        return true; // 머신이 있을 경우 true 반환
    }

    public void startUsingMachine(){
        listMachine();
        String id;
        while (true) {
            id = view.getInput("사용할 머신의 번호를 입력해주세요 : ");
            if(validNumber(id) && Gym.machines.containsKey(id)) {
                machineService.startUsingMachine(id);
                break;
            }else{
                view.showAlert("잘못된 머신 번호입니다. 다시 입력해주세요.");
            }
        }
    }

    public void stopUsingMachine() {
        listMachine();
        String id;
        while (true) {
            id = view.getInput("사용 종료할 머신의 번호를 입력해주세요 : ");
            if (validNumber(id)) {
                machineService.stopUsingMachine(id);
                break;
            }else{
                view.showAlert("잘못된 머신 번호입니다. 다시 입력해주세요.");
            }
        }

        view.showMessage("[머신 사용 기록하기]");
        String val1;
        String val2;
        while (true) {
            String tmp = view.getInput("[무게/속도]를 입력해주세요 : ");
            if (validatePositiveDecimal(tmp)) {
                val1 = tmp;
                break;
            }else{
                view.showAlert("잘못된 입력입니다. 양의 실수를 입력해주세요.");

            }
        }
        while (true) {
            String tmp = view.getInput("[횟수/시간]를 입력해주세요 : ");
            if (validatePositiveDecimal(tmp)) {
                val2 = tmp;
                break;
            }else{
                view.showAlert("잘못된 입력입니다. 양의 실수를 입력해주세요.");
            }
        }

        // 사용 기록 등록
        machineService.addMachineUsage(id, val1, val2);
        view.showAlert("사용기록이 정상 기록되었습니다.");
    }

    public void getMachineUsage(){
        view.showMessage("[나의 머신 사용기록]");
        List<MachineUsageDTO> usageList = machineService.getMachineUsage();
        for(MachineUsageDTO usage : usageList) {

            if(Gym.machines.get(usage.machineId).getType().equals("유산소")){
                view.showMessage("[머신 이름] " + usage.machineName + ", [무게/속도] " + usage.val1 + "km/h, [횟수/시간] " + usage.val2 + "분");
            }else {
                view.showMessage("[머신 이름] " + usage.machineName + ", [무게/속도] " + usage.val1 + "kg, [횟수/시간] " + usage.val2 + "회");
            }
        }
    }
}
