package com.healthmanage.view;

public class AdminView extends View {
    public AdminView() {
        super();
    }

    public String selectEntryMenu() {
        return getMenuInput("ADMIN LOGIN", 
                        "[1] 로그인\n" +
                        "[2] 관리자 등록\n" +
                        "[0] 종료");
    }

    public String selectAdminMenu() {
        return getMenuInput("ADMIN MODE",
                        "[1] 회원 관리\n" +
                        "[2] 쿠폰 관리\n" +
                        "[3] 기구 관리\n" +
                        "[0] 종료");
    }

    public String selectUserManageMenu() {
        return getMenuInput("MEMBER MANAGEMENT", 
                        "[1] 전체 조회 (이름순)\n" +
                        "[2] 개인회원 출결 (날짜별)\n" +
                        "[3] 개인회원 출결 (전체)\n" +
                        "[4] 전체 회원 출결 (날짜별)\n" +
                        "[5] 전체 회원 운동 누적 시간\n" +
                        "[0] 종료");
    }

    public String selectCouponManageMenu() {
        return getMenuInput("COUPON MANAGEMENT",
                        "[1] 쿠폰 발급\n" +
                        "[2] 쿠폰 조회\n" +
                        "[3] 쿠폰 삭제\n" +
                        "[0] 종료");
    }

    public String selectMachineManageMenu() {
        return getMenuInput("MACHINE MANAGEMENT",
                "[1] 머신 현황\n" +
                        "[2] 머신 추가\n" +
                        "[3] 머신 삭제\n" +
                        "[0] 종료");
    }

    public void showRank(int rank, String userId, String workOutTime) {
        System.out.println("\n===================================");
        System.out.printf("          🏆 랭킹 %d위%n", rank);
        System.out.println("===================================");
        System.out.printf("👤 아이디 : %s%n", userId);
        System.out.printf("⏳ 누적 운동 시간 : %s%n", workOutTime);
        System.out.println("===================================\n");
    }
}
