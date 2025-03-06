package com.healthmanage.app;
import com.healthmanage.config.EnvConfig;

public class Main {

	public static void main(String[] args) {
		System.out.println("초기 설정 중입니다.");
		System.out.println(EnvConfig.get("123"));

	}

}
