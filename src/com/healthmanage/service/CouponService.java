package com.healthmanage.service;

import java.util.Map;

import com.healthmanage.model.Coupon;
import com.healthmanage.model.Gym;

public class CouponService {
	private static CouponService instance;
	
	private CouponService() {
	}
	
	public static CouponService getInstance() {
		if(instance == null) {
			instance = new CouponService();
		}
		return instance;
	}
	
	public Map<String, Coupon> findAllCoupons() {
		return Gym.coupons;
	} //-> 이미 메모리에 있는거 보여준다??? 그냥 바로 가져오면 되지 않나??
	
	public Coupon findCoupon(String number) {
        if (!Gym.coupons.containsKey(number)) {  
            System.out.println("존재하지 않는 쿠폰번호입니다.");  
            return null;  
        }	
		return Gym.coupons.get(number);
	}
	
	public boolean createCoupon(String number, int coinAmount) {
        if (Gym.coupons.containsKey(number)) {  
            System.out.println("이미 존재하는 쿠폰번호입니다.");  
            return false;  
        }  	
		Coupon coupon = new Coupon(number, coinAmount);
		Gym.coupons.put(number, coupon);
		return true;
	}
	
//	public boolean useCoupon(String number) {
//		Coupon coupon = findCoupon(number);
//		if (coupon == null) {
//			System.out.println("존재하지 않는 쿠폰입니다.");
//			return false;
//		}
//		else if (!coupon.isUsed()) {
//			coupon.setUsed(true);
//			// 고객 코인 += coinAmount;
//			return true;
//		}
//		else {
//			System.out.println("이미 사용된 쿠폰입니다.");
//			return false;
//		}
	public String useCoupon(String number) {
		Coupon coupon = findCoupon(number);
		if (coupon == null) {
			return "존재하지 않는 쿠폰입니다.";
		}
		else if (!coupon.isUsed()) {
			coupon.setUsed(true);
			// 고객 코인 += coinAmount;
			return "쿠폰 사용 성공";
		}
		else {
			return "이미 사용된 쿠폰입니다.";
		}
		
		
		
		
//		for (Coupon coupon : Gym.coupons.values()) {
//			if (coupon.getNumber().equals(number) && !coupon.isUsed()) {
//				coupon.setUsed(true);
//				// 고객 코인 += coinAmount;
//				return true;
//			}
//			else if (coupon.getNumber().equals(number) && coupon.isUsed()) {
//				System.out.println("이미 사용된 쿠폰입니다.");
//				return true;
//			}
//			else {
//				System.out.println("쿠폰 번호를 다시 확인해주세요.");
//			}
//		}
//		return false;
	}

}
