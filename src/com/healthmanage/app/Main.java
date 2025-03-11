package com.healthmanage.app;
import com.healthmanage.config.EnvConfig;
import com.healthmanage.controller.GymController;
import com.healthmanage.service.LogService;
public class Main {

	public static void main(String[] args) {
		System.out.println(EnvConfig.get("APP_NAME") + " " +  EnvConfig.get("APP_VERSION") +" Starting...");
		
		LogService log = LogService.getInstance();
		log.addLog(EnvConfig.get("APP_NAME") + " " +  EnvConfig.get("APP_VERSION") +" Starting...");
		
		log.logCleanUp();
		
		GymController gym = new GymController();
		gym.start();
	}
}
