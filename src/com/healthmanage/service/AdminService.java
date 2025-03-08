package com.healthmanage.service;

import java.util.Map;
import java.util.regex.Pattern;

import com.healthmanage.model.Coupon;
import com.healthmanage.model.Gym;
import com.healthmanage.model.Person;
import com.healthmanage.view.UserView;
import com.healthmanage.utils.SHA256;


public class AdminService {
	private CouponService couponservice;
	private static AdminService instance;
	private UserService userService;
	private UserView userView;
	
	private AdminService() {
		this.couponservice = CouponService.getInstance();

	}

	public static AdminService getInstance() {
		if (instance == null) {
			instance = new AdminService();
		}
		return instance;
	}

	public void memberList() { // 회원 전체조회
		for (Person member : Gym.users.values()) {
			System.out.println(member);
		}
	}

	public String memberSearch(String memberNum) { // 회원 검색조회
		if (Gym.users.containsKey(memberNum)) {
			return Gym.users.get(memberNum).toString();
		} else {
			return null;
		}
	}

	public void pwChange(String memberNum, String pw){ //비밀번호 수정
		//로그인 상태에서 비밀번호 입력받아 맞는지 확인
		//기존 비밀번호가 맞으면 새로운 비밀번호 변경
		String hashedPw = SHA256.encrypt(pw);

		
		if(!Gym.users.get(memberNum).getPassword().equals(hashedPw)) {
			userView.showMessage("비밀번호가 올바르지 않습니다.");
			return;
		}

		String newPw = userView.getInput("새로운 비밀번호를 입력하세요.");
		String newHashedPw = SHA256.encrypt(newPw);
		Gym.users.get(memberNum).setPassword(newHashedPw);
		userView.showMessage("비밀번호가 성공적으로 변경되었습니다.");


	}
	
	public void memberDelete(String memberNum) { // 삭제
		Gym.users.remove(memberNum);
	}

	public boolean adminLogin(String adminId, String pw) {
		String hashedPw = SHA256.encrypt(pw);

		if (!Gym.admins.containsKey(adminId)) {
			System.out.println("없는 아이디입니다.");
			return false;
		}

		else {
			if (!Gym.admins.get(adminId).getPassword().equals(hashedPw)) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				return false;
			} else {
				System.out.println("로그인 성공");
				return true;
			}
		}

	}

	// 영어 소문자+숫자, 5~12자
	public boolean isValidId(String adminId) {
		return Pattern.matches("^[a-z0-9]{5,12}$", adminId);
	}

	// 8~16자, 대문자,숫자,소문자영문,특수문자 1개 이상 포함
	public boolean isValidPw(String adminPw) {
		return Pattern.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$", adminPw);
	}

	public Map<String, Coupon> findAllCoupon() {
		return couponservice.findAllCoupons();
	}

	public void addCoupon(String number, int coinAmount) {
		couponservice.createCoupon(number, coinAmount);
	}

	public String deleteCoupon(String number) {
		Coupon coupon = couponservice.deleteCoupon(number);
		if (coupon == null) {
			return "삭제 실패 - 없는 쿠폰번호 입니다.";
		}
		return coupon.toString() + "삭제";
	}

}
