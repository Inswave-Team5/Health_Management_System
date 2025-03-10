package com.healthmanage.controller;

import com.healthmanage.view.View;

public class GymController {
	private View view;
	private UserController userController;
	//private AdminController adminController;
	
	public GymController() {
		this.view = new View();
		this.userController = new UserController();
		//this.adminController = new AdminController();
	}
	
	public void start() {
		int key = 0;
		while ((key = Integer.parseInt(view.selectMenu())) != 0) {
			switch (key) {
			case 1 :
				break;
			case 2 :
				userController.start();
				break;
			default:
				System.out.println("잘못 선택하였습니다.");
				break;
			}
		}
		System.out.println("프로그램을 종료합니다...");
	}
}
