package com.healthmanage.view;

import java.util.Scanner;

public class UserView {
	private Scanner scan;

	public UserView() {
		this.scan = new Scanner(System.in);
	}

	public String getUserInput(String message) {
		System.out.print(message);
		return scan.nextLine();
	}

	// 🔹 단순 메시지 출력 메서드
	public void showMessage(String message) {
		System.out.println(message);
	}

	public String selectMenu() {
		return getUserInput("1:추가 2:삭제 3:검색 4:도서 목록 5:ISBN 목록 6:저장 7:로드 0:종료");
	}

}
