package com.healthmanage.dto;

public class MachineUsageDTO {
    public String userId;
    public String machineId;
    public String val1;
    public String val2;

    public MachineUsageDTO(String userId, String machineId, String val1, String val2) {
        this.userId = userId;
        this.machineId = machineId;
        this.val1 = val1;
        this.val2 = val2;
    }

    @Override
    public String toString() {
        return "[machineId] " + machineId + ", [무게/속도] " + val1 + ", [횟수/시간] " + val2;
    }
}

