package com.healthmanage.service;

import com.healthmanage.model.Equipment;
import com.healthmanage.model.Equipments.EquipmentFactory;
import com.healthmanage.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EquipmentService {
    private static EquipmentService instance;
    private View view;
    private HashMap<String, List<Equipment>> userWorkoutHistory = new HashMap<>();;
    private LogService logger;
    
    private EquipmentService() {
    	this.view = new View();
    	this.logger = LogService.getInstance();
    }

    public static EquipmentService getInstance() {
        if (instance == null) {
            instance = new EquipmentService();
        }
        return instance;
    }

    public void addWorkoutRecord(String userId, String name, int val1, int val2) {
//        workoutRecords.add(new WorkoutRecord(userId, equipment));
        Equipment userEquipment = EquipmentFactory.createEquipment(name, val1, val2);
        userWorkoutHistory.putIfAbsent(userId, new ArrayList<>());
        userWorkoutHistory.get(userId).add(userEquipment);
    }

    public void getUserWorkoutHistory(String userId) {
        List<Equipment> History = userWorkoutHistory.get(userId);
        List<Equipment> userHistory = new ArrayList<Equipment>();
        for (Equipment record : History) {
            userHistory.add(record);
        }
        for(Equipment userRecord : userHistory){
            view.showMessage(userRecord.toString());
        }
    }
}