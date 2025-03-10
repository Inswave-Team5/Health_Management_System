package com.healthmanage.app;
import com.healthmanage.config.EnvConfig;
import com.healthmanage.controller.GymController;
import com.healthmanage.controller.WeightController;

public class Main {

	public static void main(String[] args) {
		System.out.println("초기 설정 중입니다.");
		System.out.println(EnvConfig.get("USER_FILE"));
		
		
		GymController gym = new GymController();
//		gym.start();

		WeightController weight = new WeightController();


	}

}
