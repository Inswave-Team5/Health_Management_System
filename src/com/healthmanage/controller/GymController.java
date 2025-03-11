package com.healthmanage.controller;

import com.healthmanage.service.GymService;
import com.healthmanage.view.View;

public class GymController {
	private View view;
	private UserController userController;
	private AdminController adminController;
	private GymService gymService;
	
	
	public GymController() {
		this.view = new View();
		this.userController = new UserController();
		this.adminController = new AdminController();
		this.gymService = new GymService();
		
	}
	
	public void start() {
		gymService.load();
		gymService.adminInit();
		int key = 0;
		while ((key = Integer.parseInt(view.selectMenu())) != 0) {
			switch (key) {
			case 1 :
				adminController.start();
				break;
			case 2 :
				userController.entry();
				break;
			default:
				System.out.println("잘못 선택하였습니다.");
				break;
			}
		}
		gymService.save();
		System.out.println("프로그램을 종료합니다...");
	}
	
}
