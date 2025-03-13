package com.healthmanage.model.Machines;

import com.healthmanage.model.Machine;

//유산소 운동기구
public class CardioMachine extends Machine {
    public CardioMachine(String machineId, String name) {

        super(machineId,"유산소", name);
    }
}
