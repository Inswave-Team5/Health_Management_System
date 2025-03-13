package com.healthmanage.service;

import com.healthmanage.dto.MachineUsageDTO;
import com.healthmanage.model.Gym;
import com.healthmanage.model.Machine;
import com.healthmanage.model.Machines.MachineFactory;
import com.healthmanage.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MachineService {
    private static MachineService instance;
    View view;
    private LogService logger;
    private int machineIdCounter = 1; // ID를 자동으로 증가시킬 변수

    private HashMap<String, List<MachineUsageDTO>> usageMap;

    public MachineService() {
        this.view = new View();
        this.logger = LogService.getInstance();
        this.usageMap = new HashMap<>();
    }

    public static MachineService getInstance() {
        if (instance == null) {
            instance = new MachineService();
        }
        return instance;
    }

    //머신 추가(관리자) - controller에서 Machine정보 입력 받음
    public void addMachine(String name, String type) {
        String machineId = generateMachineId(); // 자동으로 ID 생성
        MachineFactory machineFactory = MachineFactory.getFactory(type);
        Machine machine = machineFactory.createMachine(machineId, name);
        Gym.machines.put(machineId, machine);
    }


    // 머신 ID를 자동으로 증가시키는 메서드
    private String generateMachineId() {
        return String.valueOf(machineIdCounter++); // 현재 ID를 반환하고 1 증가시킴
    }


    //머신 리스트 조회(이름, 타입, 상태) (관리자, 사용자)
    public List<Machine> listMachines() {
        List<Machine> machineList = new ArrayList<>();
        machineList.addAll(Gym.machines.values());

        return machineList;
    }

    //머신 삭제
    public void removeMachine(String machineId) {

        Gym.machines.remove(machineId);
    }

    //머신 사용(사용자)
    //한사람이 하나의 머신만 사용가능하게할지 고민..............
    public void startUsingMachine(String machineId) {
        if(!Gym.machines.get(machineId).getInUse()) {
            Gym.machines.get(machineId).setInUse(true);
            view.showMessage("["+ Gym.machines.get(machineId).getName() + "]을 사용합니다.");
            view.showMessage("사용이 끝나면 종료해주세요!");
        } else {
            view.showMessage("["+ Gym.machines.get(machineId).getName() + "]은 이미 사용중인 머신 입니다.");
        }
    }

    //머신 사용 종료(사용자) + usage 기록 등록
    public void stopUsingMachine(String machineId) {
        if(Gym.machines.get(machineId).getInUse()) {
            Gym.machines.get(machineId).setInUse(false);
            view.showMessage("["+ Gym.machines.get(machineId).getName() + "]을 사용 종료합니다.");
        } else {
            view.showMessage("["+ Gym.machines.get(machineId).getName() + "]은 사용중인 머신이 아닙니다.");
        }
    }

    //usage 기록 등록
    public void addMachineUsage(String machineId, String val1, String val2) {
        MachineUsageDTO usage  = new MachineUsageDTO(Gym.getCurrentUser().getUserId(), machineId, val1, val2);
        usageMap.putIfAbsent(machineId, new ArrayList<>());
        usageMap.get(machineId).add(usage);

        logger.addLog(Gym.getCurrentUser().getName() +"님의" + machineId + "사용기록이 등록되었습니다.");
    }

    //usage 기록 조회
    public List<MachineUsageDTO> getMachineUsage() {

        List<MachineUsageDTO> usageList = usageMap.get(Gym.getCurrentUser().getUserId());
        if(usageList == null || usageList.isEmpty() || usageMap==null || usageMap.isEmpty()) {
            view.showMessage("아직 사용 기록이 없습니다!");
            view.showMessage("사용 기록을 입력해주세요.");
            return new ArrayList<>();
        }
            return usageList;
    }
}
