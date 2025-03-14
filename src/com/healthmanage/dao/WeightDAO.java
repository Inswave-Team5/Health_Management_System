package com.healthmanage.dao;

import com.healthmanage.config.EnvConfig;

import com.healthmanage.model.Weight;
import com.healthmanage.service.WeightService;
import com.healthmanage.utils.FileIO;

import java.util.List;
import java.util.Map;

public class WeightDAO {
    public void saveWeight() {
        FileIO.infoSave(WeightService.weightList, EnvConfig.get("WEIGHT_FILE"));
    }

    public Map<String, List<Weight>> loadWeight(String filePath) {
        //WeightService.weightList = (Map<String, List<Weight>>) FileIO.infoWeightLoad(filePath);
        WeightService.weightList = FileIO.<Weight>infoMapLoad(filePath);

        return WeightService.weightList;
    }
}
