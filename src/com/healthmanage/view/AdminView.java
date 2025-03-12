package com.healthmanage.view;

import java.util.Scanner;

public class AdminView extends View {
	public AdminView() {
		super();
	}
	
	public String selectEntryMenu() {
		return getInput("1. 로그인 2. 관리자등록 0. 종료");
	}
	
	public String selectAdminMenu() {
		return getInput("관리자 모드입니다. \n "
				+ "1:회원관리 2:쿠폰관리 3:기구관리 0:종료");
	}
	
	public String selectUserManageMenu() {
		return getInput("회원관리. \n "
				+ "1:전체조회 2:개인회원출결(날짜별) 3:개인회원출결(전체) 4:전체회원출결(날짜별) 5:전체회원운동누적시간 0:종료");
	}
	
	public String selectCouponManageMenu() {
		return getInput("쿠폰관리. \n "
				+ "1:쿠폰발급 2:쿠폰조회 3:쿠폰삭제 0:종료");
	}
	
	public void showRank(int rank, String userId, String workOutTime) {
		System.out.println("랭킹 " + rank + "등 아이디 : " + userId + " 누적 시간 : " + workOutTime);
	}

}
