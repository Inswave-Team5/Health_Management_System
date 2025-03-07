package com.healthmanage.view;

import java.util.Scanner;

public class View {
	protected Scanner scan;
	
	public View(){
		this.scan = new Scanner(System.in);
	}
	
	public String getInput(String message) {
		System.out.print(message);
		return scan.nextLine();
	}

	// 🔹 단순 메시지 출력 메서드
	public void showMessage(String message) {
		System.out.println(message);
	}
	
	public String selectMenu() {
		return getInput("1:관리자모드 2:사용자모드 0:종료");
	};
}
