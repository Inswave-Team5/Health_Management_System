package com.healthmanage.service;

import com.healthmanage.config.EnvConfig;
import com.healthmanage.dao.MachineDAO;
import com.healthmanage.dao.MachineUsageDAO;
import com.healthmanage.dto.MachineUsageDTO;
import com.healthmanage.model.Gym;
import com.healthmanage.model.Machine;
import com.healthmanage.model.Machines.MachineFactory;
import com.healthmanage.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MachineService {
    private static MachineService instance;
    View view;
    private LogService logger;
    private MachineDAO machineDAO;
    private MachineUsageDAO machineUsageDAO;
    private int machineIdCounter; // ID를 자동으로 증가시킬 변수

    public static Map<String, List<MachineUsageDTO>> usageMap;

    private MachineService() {
        this.view = new View();
        this.logger = LogService.getInstance();
        this.usageMap = new HashMap<>();
        this.machineDAO = new MachineDAO();
        this.machineUsageDAO = new MachineUsageDAO();
    }

    public static MachineService getInstance() {
        if (instance == null) {
            instance = new MachineService();
        }
        return instance;
    }

    // Machine 추가(관리자) - controller에서 Machine 정보 입력 받음
    public void addMachine(String type, int input) {
        String machineId = generateMachineId(); // 자동으로 ID 생성
        MachineFactory machineFactory = MachineFactory.getFactory(type);

        Machine machine = machineFactory.createMachine(machineId, input);

        if (machine == null) {
            throw new IllegalArgumentException("잘못된 기구 입력값입니다: " + input);
        }

        Gym.machines.put(machineId, machine);
    }

    public void load() {
        machineDAO.loadMachines(EnvConfig.get("MACHINE_FILE"));
        logger.addLog(EnvConfig.get("MACHINE_FILE") + " File LOAD");

        machineUsageDAO.loadUsage(EnvConfig.get("USAGE_FILE"));
        logger.addLog(EnvConfig.get("USAGE_FILE") + " File LOAD");

    }

    public void save() {
        machineDAO.saveMachines();
        logger.addLog(EnvConfig.get("MACHINE_FILE") + " File SAVE");

        machineUsageDAO.saveUsage();
        logger.addLog(EnvConfig.get("USAGE_FILE") + " File SAVE");
    }



    // 머신 ID를 자동으로 증가시키는 메서드
    private String generateMachineId() {
    	int number = Gym.machines.size();
        return String.valueOf(++number); // 현재 ID를 반환하고 1 증가시킴
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

    public void stopUsingMachine(String machineId) {
        if (!Gym.machines.containsKey(machineId)) {
            view.showMessage("해당 머신이 존재하지 않습니다.");
            return;
        }

        Machine machine = Gym.machines.get(machineId);

        if (!machine.getInUse()) {
            view.showMessage("[" + machine.getName() + "]은 사용중인 머신이 아닙니다.");
            return;
        }

        // 사용 종료 처리
        machine.setInUse(false);

        // 상태 변경이 적용되었는지 즉시 확인
        if (!machine.getInUse()) {
            view.showMessage("[" + machine.getName() + "]을 사용 종료합니다.");
        } else {
            view.showMessage("상태 변경에 실패했습니다. 다시 시도해주세요.");
        }
    }

    //usage 기록 등록
    public void addMachineUsage(String machineId, String val1, String val2) {
        MachineUsageDTO usage  = new MachineUsageDTO(Gym.getCurrentUser().getUserId(), machineId, Gym.machines.get(machineId).getName(), val1, val2);
        usageMap.putIfAbsent(Gym.getCurrentUser().getUserId(), new ArrayList<>());
        usageMap.get(Gym.getCurrentUser().getUserId()).add(usage);

        logger.addLog(Gym.getCurrentUser().getName() +"님의" + machineId + "사용기록이 등록되었습니다.");
    }

    //usage 기록 조회
    public List<MachineUsageDTO> getMachineUsage() {

        List<MachineUsageDTO> usageList = usageMap.get(Gym.getCurrentUser().getUserId());
        if(!usageMap.containsKey(Gym.getCurrentUser().getUserId()) || usageMap.get(Gym.getCurrentUser().getUserId()) == null || usageMap.get(Gym.getCurrentUser().getUserId()).isEmpty()) {
            view.showMessage("아직 사용 기록이 없습니다!");
            view.showMessage("사용 기록을 입력해주세요.");
            return new ArrayList<>();
        }
            return usageList;
    }

    //머신 아이디로 머신 이름 찾기
    public static String findMachineName(String machineId) {
        return Gym.machines.get(machineId).getName();
    }
}
