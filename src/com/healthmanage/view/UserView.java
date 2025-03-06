package com.healthmanage.view;
import java.util.Scanner;

public class UserView {
	Scanner scan = new Scanner(System.in);
	
	public void userView() {
		int key = 0;
		while ((key = selectMenu()) != 0) {
			switch (key) {
			/*
			 * case 1: addBook(); break; case 2: removeBook(); break; case 3: searchBook();
			 * break; case 4: listBook(); break; case 5: listISBN(); break; case 6: save();
			 * break; case 7: load(); break;
			 */
			default:
				System.out.println("잘못 선택하였습니다.");
				break;
			}
		}
		System.out.println("종료합니다...");
	}

	public int selectMenu() {
		System.out.println("1:추가 2:삭제 3:검색 4:도서 목록 5:ISBN 목록 6:저장 7:로드 0:종료");
		int key = scan.nextInt();
		scan.nextLine();
		return key;
	}

}
