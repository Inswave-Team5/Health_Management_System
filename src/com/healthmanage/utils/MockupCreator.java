package com.healthmanage.utils;

import java.util.ArrayList;
import java.util.List;

import com.healthmanage.model.Coupon;
import com.healthmanage.model.Gym;
import com.healthmanage.model.User;

public class MockupCreator {
	private static final List<String> KOREAN_NAMES = new ArrayList<>(List.of(
	        "류지훈", "박지훈", "하승연", "최도현", "윤지수", "한지민", "전예지", "이준서", "차예진", "오승환",
	        "정우진", "권지훈", "강다윤", "고다현", "전지훈", "홍민기", "서지호", "유지호", "남도현", "이재호",
	        "허민석", "정현우", "노지훈", "이도윤", "박서연", "배수진", "장민호", "송하늘", "임동현", "김지우",
	        "이서윤", "신가연", "김민준", "송지수", "오지훈", "윤채연", "조윤아", "박하영", "강민혁", "한예슬",
	        "정민수", "문수현", "최예린", "백승민", "유정훈", "안유진", "신서연", "문가영", "박채린", "김하늘"
	    ));

	    public static void generateMockUsers() {
	    	int i;
	        for (i = 0; i < 50; i++) { // 50명의 유저 생성
	            String userId = "user" + (i + 1);
	            String name = KOREAN_NAMES.get(i); // ✅ 중복 없는 이름 선택
	            String password = "User" + (i + 1) + "!"; // 비밀번호 패턴 유지
	            String salt = SHA256.generateSalt();
	            String hashedPw = SHA256.hashPassword(password, salt);

	            User newUser = new User(userId, hashedPw, name, salt);
	            Gym.users.put(userId, newUser);
	        }
	        System.out.println("✅ "+i+"명의 목업 사용자가 생성되었습니다.");
	    }
	    
	 // ✅ 목업 데이터 삽입 (테스트용)
	    public static void generateMockCoupons() {
	        Gym.coupons.put("WRIJQPK1", new Coupon("WRIJQPK1", 3000));
	        Gym.coupons.put("BTQ0MLXQ", new Coupon("BTQ0MLXQ", 3000));
	        Gym.coupons.put("R3T4D2NS", new Coupon("R3T4D2NS", 2000));
	        Gym.coupons.put("T0DS21NG", new Coupon("T0DS21NG", 7000));
	        Gym.coupons.put("QBZHU100", new Coupon("QBZHU100", 10000));
	        Gym.coupons.put("LJ7X2GJK", new Coupon("LJ7X2GJK", 1000));
	        Gym.coupons.put("MWTVO1DU", new Coupon("MWTVO1DU", 2000));
	        Gym.coupons.put("TV7SJSKE", new Coupon("TV7SJSKE", 3000));
	        Gym.coupons.put("FBVATLGX", new Coupon("FBVATLGX", 1000));
	        Gym.coupons.put("P50HP8UD", new Coupon("P50HP8UD", 2000));
	        Gym.coupons.put("6DPN51I0", new Coupon("6DPN51I0", 1000));
	        Gym.coupons.put("W9D9QQ0M", new Coupon("W9D9QQ0M", 3000));
	        Gym.coupons.put("EZ2NW713", new Coupon("EZ2NW713", 3000));
	        Gym.coupons.put("94PXNMS1", new Coupon("94PXNMS1", 3000));
	        Gym.coupons.put("NKIZLVQZ", new Coupon("NKIZLVQZ", 7000));
	        Gym.coupons.put("QIPJX3JU", new Coupon("QIPJX3JU", 3000));
	        Gym.coupons.put("24VLPGGO", new Coupon("24VLPGGO", 5000));
	        Gym.coupons.put("M7YYR6PL", new Coupon("M7YYR6PL", 5000));
	        Gym.coupons.put("E22FBP2E", new Coupon("E22FBP2E", 3000));
	        Gym.coupons.put("3BRTM2O4", new Coupon("3BRTM2O4", 3000));

	        System.out.println("✅ 20개의 목업 쿠폰이 Gym.coupons에 저장되었습니다.");
	    }
}
