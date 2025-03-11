package com.healthmanage.view;

import java.util.Scanner;

public class UserView extends View {
	public UserView() {
		super();
	}

	public String selectMenu() {
		return getInput("사용자 모드입니다. \n "
				+ "1:나의 출결 2:운동시간 3:체중관리 4:운동기구 5:쿠폰등록 6:코인 7:비밀번호 변경 8:로그아웃");
	}
	
	public String selectLogin() {
		return getInput("1. 로그인 2. 회원가입 0. 종료");
	}

	public String AttendanceSelectMenu() {
		return getInput("[나의 출결] 메뉴입니다. \n "
				+ "1:입장 등록  2:퇴장 등록  3:나의 입퇴장  0:메인메뉴");
	}

	public String WeightSelectMenu() {
		return getInput("[나의 몸무게] 메뉴입니다. \n "
				+ "1:몸무게 등록  2:몸무게 조회  0:메인메뉴");
	}

	public String WeightSelectCheckMenu() {
		return getInput("[몸무게 조회] 메뉴입니다. \n "
				+ "1:전체 조회  2:월별 조회  3:날짜 조회  0:뒤로가기");
	}

	public String TimeSelectMenu() {
		return getInput("[운동 시간] 메뉴입니다. \n "
				+ "1:오늘의 운동시간  2:날짜별 조회  3:월별 누적 조회  4.전체 누적 조회  0:메인메뉴");
	}

	public String EquipmentSelectMenu() {
		return getInput("[운동 기구] 메뉴입니다. \n "
				+ "1:기구 사용 등록  2:기구 사용 조회  0:메인메뉴");
	}

	public String coinSelectMenu() {
		return getInput("[코인] 메뉴입니다. \n "
				+ "1:코인 충전  2:코인 선물  0:메인메뉴");
	}

}
