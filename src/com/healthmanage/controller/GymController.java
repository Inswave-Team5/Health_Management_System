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
//		gymService.UserInfoInit();
//		gymService.CouponInit();
//		gymService.AttandanceInit();
//		gymService.WeightInit();
//		gymService.adminInit();
		int key = 0;
		while (true) {
			try {
				key = Integer.parseInt(view.selectMenu());				
			}catch(NumberFormatException e) {
				view.showAlert("숫자로된 메뉴 번호를 입력해주세요");
				continue;
			}
			switch (key) {
			case 1 :
				adminController.entry();
				break;
			case 2 :
				userController.entry();
				break;
			case 0 :
				gymService.save();
				System.out.println("프로그램을 종료합니다...");
				return;
			default:
				System.out.println("잘못 선택하였습니다.");
				break;
			}
		}
	}	
}
