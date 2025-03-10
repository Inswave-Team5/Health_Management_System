package com.healthmanage.app;

public class LoginUser {

    //로그인한 userid 저장하는 static 변수
    private static String loginUserId = "1234";

    //로그인한 userid 설정
    public static void setLoginUserId(String userId) {
        LoginUser.loginUserId = userId;
    }

    //로그인한 userid 얻어내기
    public static String getLoginUserId() {
        return loginUserId;
    }

    //로그아웃 - loginUserId 초기화
    public static void logout() {
        loginUserId = "null";
    }

    //로그인 체크
    public static boolean isLogin(){
        return loginUserId.equals("null");
    }
}
