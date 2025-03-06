package com.healthmanage.service;

import java.util.HashMap;
import java.util.Map;

import com.healthmanage.model.Gym;
import com.healthmanage.model.Person;


public class AdminService {
	
	public void memberList() { //회원 전체조회

		for (Person member : Gym.users.values()) {
			System.out.println(member);
		}
	}
	
	
	public String memberSearch(String memberNum) {  //회원 검색조회
		if(Gym.users.containsKey(memberNum)) {
			return Gym.users.get(memberNum).toString();
		}else {
			return null;
		}
	}
	
//	public void memberChange(String memberNum){ //수정
//	
//		}
//	
	public void memberDelete(String memberNum) { //삭제
		Gym.users.remove(memberNum);
	}

	private CouponService couponservice;
	public AdminService() {
		this.couponservice = CouponService.getInstance();
	}
	
	
	
	
	public void findAllCoupon() {
		couponservice.findAllCoupons();
	}
	
	public void addCoupon(String number, int coinAmount) {
		couponservice.createCoupon(number, coinAmount);
	}
	public void findCoupon(String number) {
		couponservice.findCoupon(number);
		
	}

}
