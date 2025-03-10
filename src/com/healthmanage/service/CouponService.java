package com.healthmanage.service;

import java.util.Collection;
import java.util.List;

import com.healthmanage.model.Coupon;
import com.healthmanage.model.Gym;
import com.healthmanage.model.User;
import com.healthmanage.utils.Sort;

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
	
	// 쿠폰번호로 정렬후 조회
	public void findAllCoupons() {
	      List<Coupon> coupons = Sort.sortCoupon(Gym.coupons.values());
	      for (Coupon coupon : coupons) {
	         System.out.println(coupon);
	      }
	   }
	
	private Coupon findCoupon(String number) {
        if (!Gym.coupons.containsKey(number)) {  
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
	

	public Coupon deleteCoupon(String number) {
		return Gym.coupons.remove(number);
	}
	
	public String useCoupon(String number, User user) {
		Coupon coupon = findCoupon(number);
		if (coupon == null) {
			return "존재하지 않는 쿠폰입니다.";
		}
		else if (!coupon.isUsed()) {
			coupon.setUsed(true);
			user.setCoin(user.getCoin() + coupon.getCoinAmount());
			
			return "쿠폰 사용 성공";
		}
		else {
			return "이미 사용된 쿠폰입니다.";
		}

	}

}
