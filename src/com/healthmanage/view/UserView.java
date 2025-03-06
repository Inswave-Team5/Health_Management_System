package com.healthmanage.view;

import java.util.Scanner;

public class UserView extends View {
	

	public UserView() {
		super();
		
	}



	public String selectMenu() {
		return getInput("사용자 모드입니다. \n "
				+ "1:추가 2:삭제 3:검색 4:도서 목록 5:ISBN 목록 6:저장 7:로드 0:종료");
	}

}
