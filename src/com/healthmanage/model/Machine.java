package com.healthmanage.model;

import com.healthmanage.view.View;

public abstract class Machine {
    protected String machineId;     //머신 id
    protected String name;          //머신 이름
    protected String type;          //머신 타입 - 유산소 or 근력
    protected boolean inUse;        //사용상태 - true(사용중) false(사용가능)

    public Machine(String machineId, String type,String name) {
        this.machineId = machineId;
        this.name = name;
        this.type = type;
        this.inUse = false; //처음엔 사용가능으로 초기화
    }

    public boolean getInUse() {
        return inUse;
    }

    public String getName(){
        return name;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    @Override
    public String toString() {
        return "[" + machineId + "] 이름: " + name + ", 상태: " + (inUse ? "사용 중" : "사용 가능") + "]";
    }
}

//유산소 운동기구
class CardioMachine extends Machine {
    public CardioMachine(String machineId, String name) {
        super(machineId,"유산소", name);
    }
}

//근력 운동기구
class StrengthMachine extends Machine {
    public StrengthMachine(String machineId, String name) {
        super(machineId, "근력", name);
    }
}

