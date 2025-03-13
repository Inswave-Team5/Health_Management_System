package com.healthmanage.dao;

import com.healthmanage.config.EnvConfig;
import com.healthmanage.model.Gym;
import com.healthmanage.model.Machine;
import com.healthmanage.utils.FileIO;

import java.util.Map;

public class MachineDAO {
    public void saveMachines() {
        FileIO.infoSave(Gym.machines, EnvConfig.get("MACHINE_FILE"));
    }

    public Map<String, Machine> loadMachines(String FilePath) {
        Gym.machines = (Map<String, Machine>) FileIO.infoLoad(FilePath,Machine.class);
        return Gym.machines;
    }
}
