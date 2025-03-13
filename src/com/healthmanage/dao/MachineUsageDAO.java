package com.healthmanage.dao;

import com.healthmanage.config.EnvConfig;
import com.healthmanage.dto.MachineUsageDTO;
import com.healthmanage.model.Attendance;
import com.healthmanage.service.AttendanceService;
import com.healthmanage.service.MachineService;
import com.healthmanage.utils.FileIO;

import java.util.List;
import java.util.Map;

public class MachineUsageDAO {
    public void saveUsage() {
        FileIO.infoSave(MachineService.usageMap, EnvConfig.get("USAGE_FILE"));
    }

    public Map<String, List<MachineUsageDTO>> loadUsage(String filePath) {
        MachineService.usageMap = FileIO.<MachineUsageDTO>infoMapLoad(filePath);
        return MachineService.usageMap;
    }
}
