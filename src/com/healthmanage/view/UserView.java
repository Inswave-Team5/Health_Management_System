package com.healthmanage.view;

public class UserView extends View {
    public UserView() {
        super();
    }

    public String selectMenu() {
        return getMenuInput("USER MODE", 
                        "[1] 나의 출결\n" +
                        "[2] 운동 시간\n" +
                        "[3] 체중 관리\n" +
                        "[4] 운동 기구\n" +
                        "[5] 쿠폰 사용\n" +
                        "[6] 코인 관리\n" +
                        "[7] 비밀번호 변경\n" +
                        "[0] 로그아웃");
    }

    public String selectLogin() {
        return getMenuInput("LOGIN / SIGN UP",
                        "[1] 로그인\n" +
                        "[2] 회원가입\n" +
                        "[0] 종료");
    }

    public String AttendanceSelectMenu() {
        return getMenuInput("CHECK-IN MENU",
                        "[1] 입장 등록\n" +
                        "[2] 퇴장 등록\n" +
                        "[3] 나의 입퇴장 기록\n" +
                        "[0] 메인 메뉴");
    }

    public String WeightSelectMenu() {
        return getMenuInput("WEIGHT MANAGEMENT",
                        "[1] 몸무게 등록\n" +
                        "[2] 몸무게 조회\n" +
                        "[0] 메인 메뉴");
    }

    public String WeightSelectCheckMenu() {
        return getMenuInput("WEIGHT CHECK",
                        "[1] 전체 조회\n" +
                        "[2] 월별 조회\n" +
                        "[3] 날짜 조회\n" +
                        "[0] 뒤로 가기");
    }

    public String TimeSelectMenu() {
        return getMenuInput("WORKOUT TIME",
                        "[1] 오늘의 운동 시간\n" +
                        "[2] 날짜별 조회\n" +
                        "[3] 월별 누적 조회\n" +
                        "[4] 전체 누적 조회\n" +
                        "[0] 메인 메뉴");
    }

    public String EquipmentSelectMenu() {
        return getMenuInput("GYM EQUIPMENT",
                        "[1] 기구 사용 등록\n" +
                        "[2] 기구 사용 조회\n" +
                        "[0] 메인 메뉴");
    }

    public String coinSelectMenu() {
        return getMenuInput("COIN MENU",
                        "[1] 코인 충전\n" +
                        "[2] 코인 선물\n" +
                        "[0] 메인 메뉴");
    }
}
