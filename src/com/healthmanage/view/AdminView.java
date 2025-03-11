package com.healthmanage.view;

import java.util.Scanner;

public class AdminView extends View {
	public AdminView() {
		super();
	}
	
	public String selectAdminMenu() {
		return getInput("관리자 모드입니다. \n "
				+ "1:회원관리 0:종료");
	}
	
	public String selectUserManageMenu() {
		return getInput("회원관리. \n "
				+ "1:전체조회 2:회원조회 3: 회원수정 4: 회원삭제 0:종료");
	}
	
	public void showRank(int rank, String userId, String workOutTime) {
		System.out.println("랭킹 " + rank + "등 아이디 : " + userId + " 누적 시간 : " + workOutTime);
	}

}
