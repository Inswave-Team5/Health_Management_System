package com.healthmanage.view;

public class AdminView extends View {
    public AdminView() {
        super();
    }

    public String selectEntryMenu() {
        return getMenuInput("ADMIN LOGIN",
                "[1] 로그인       [2] 관리자 등록    [0] 종료");
    }

    public String selectAdminMenu() {
        return getMenuInput("ADMIN MODE",
                "[1] 회원 관리    [2] 쿠폰 관리    [3] 기구 관리    [0] 종료");
    }

    public String selectUserManageMenu() {
        return getMenuInput("MEMBER MANAGEMENT",
                "[1] 전체 조회 (이름순)   [2] 개인 출결 (날짜)   [3] 개인 출결 (전체)\n" +
                "[4] 전체 출결 (날짜)     [5] 회원 순위보기    [0] 종료");
    }

    public String selectCouponManageMenu() {
        return getMenuInput("COUPON MANAGEMENT",
                "[1] 쿠폰 발급    [2] 쿠폰 조회    [3] 쿠폰 삭제    [0] 종료");
    }

    public String selectMachineManageMenu() {
        return getMenuInput("MACHINE MANAGEMENT",
                "[1] 머신 현황    [2] 머신 추가    [3] 머신 삭제    [0] 종료");
    }

    public void showRank(int rank, String userId, String workOutTime) {
        System.out.printf("🏆 랭킹 %d위  ", rank);
        System.out.printf("👤 아이디 : %10s\t", userId);
        System.out.printf("⏳ 누적 운동 시간 : %s\n", workOutTime);
    }
}
