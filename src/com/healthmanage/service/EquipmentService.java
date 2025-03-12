package com.healthmanage.service;

import com.healthmanage.model.Equipment;
import com.healthmanage.model.Equipments.EquipmentFactory;
import com.healthmanage.model.Gym;
import com.healthmanage.view.UserView;
import com.healthmanage.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EquipmentService {
    private static EquipmentService instance;
    private HashMap<String, List<Equipment>> userWorkoutHistory = new HashMap<>();;
    private LogService logger;
    private UserView view;

    private EquipmentService() {
    	view = new UserView();
        this.logger = LogService.getInstance();
    }

    public static EquipmentService getInstance() {
        if (instance == null) {
            instance = new EquipmentService();
        }
        return instance;
    }

    public void addWorkoutRecord(String name, int val1, int val2) {
        Equipment userEquipment = EquipmentFactory.createEquipment(name, val1, val2);
        userWorkoutHistory.putIfAbsent(Gym.getCurrentUser().getUserId(), new ArrayList<>());
        userWorkoutHistory.get(Gym.getCurrentUser().getUserId()).add(userEquipment);
    }

    public List<Equipment> getUserWorkoutHistory(String userId) {
        return userWorkoutHistory.get(userId);
    }
}