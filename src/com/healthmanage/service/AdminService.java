package com.healthmanage.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.healthmanage.model.Coupon;
import com.healthmanage.model.Gym;
import com.healthmanage.model.Person;


public class AdminService {
	private CouponService couponservice;
	private static AdminService instance;
	
	private AdminService() {
		this.couponservice = CouponService.getInstance();
		
	}
	
	public static AdminService getInstance() {
		if (instance == null) {
			instance = new AdminService();
		}
		return instance;
	}
	
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
	public boolean adminLogin(String adminId, String pw) {
		if (!Gym.admins.containsKey(adminId)) {
			System.out.println("없는 아이디입니다.");
			return false;
		}else {
			if(!Gym.admins.get(adminId).getPassword().equals(pw)) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				return false;
			}else {
				System.out.println("로그인 성공");
				return true;
			}
		}
	}
	
	public Collection<Coupon> findAllCoupon() {
		return couponservice.findAllCoupons();
	}
	
	public void addCoupon(String number, int coinAmount) {
		couponservice.createCoupon(number, coinAmount);
	}
	
	
	public String deleteCoupon(String number) {
		Coupon coupon = couponservice.deleteCoupon(number);
		if ( coupon == null) {
			return "삭제 실패 - 없는 쿠폰번호 입니다.";
		}
		return coupon.toString()+"삭제";
	}

}
