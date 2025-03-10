package com.healthmanage.app;
import com.healthmanage.config.EnvConfig;
import com.healthmanage.controller.GymController;
import com.healthmanage.service.LogService;
import com.healthmanage.controller.WeightController;
public class Main {

	public static void main(String[] args) {
		System.out.println("초기 설정 중입니다.");
		System.out.println(EnvConfig.get("USER_FILE"));
		System.out.println(EnvConfig.get("LOG_FILE_PATH"));
		
		LogService log = LogService.getInstance();
		log.addLog("로그추가입니다.");
		
		log.logCleanUp();
		
		GymController gym = new GymController();
		gym.start();

//		WeightController weight = new WeightController();


	}

}
