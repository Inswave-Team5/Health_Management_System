package com.healthmanage.dto;

import java.io.Serializable;

public class MachineUsageDTO implements Serializable {
    public String userId;
    public String machineId;
    public String machineName;
    public String val1;
    public String val2;

    public MachineUsageDTO(String userId, String machineId, String machineName, String val1, String val2) {
        this.userId = userId;
        this.machineId = machineId;
        this.machineName = machineName;
        this.val1 = val1;
        this.val2 = val2;
    }

}

