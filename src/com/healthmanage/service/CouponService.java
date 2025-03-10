package com.healthmanage.service;

import java.util.Collection;

import com.healthmanage.config.EnvConfig;
import com.healthmanage.dao.CouponDAO;
import com.healthmanage.model.Coupon;
import com.healthmanage.model.Gym;
import com.healthmanage.model.User;

public class CouponService {
	private static CouponService instance;
	private CouponDAO couponDAO;

	private CouponService() {
		couponDAO = new CouponDAO();
	}

	public static CouponService getInstance() {
		if (instance == null) {
			instance = new CouponService();
		}
		return instance;
	}

	public void load() {
		couponDAO.loadCoupons(EnvConfig.get("COUPON_FILE"));
	}

	public void save() {
		couponDAO.saveCoupons();
	}

	public Collection<Coupon> findAllCoupons() {
		return Gym.coupons.values();
	} // -> 이미 메모리에 있는거 보여준다??? 그냥 바로 가져오면 되지 않나??

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
		} else if (!coupon.isUsed()) {
			coupon.setUsed(true);
			user.setCoin(user.getCoin() + coupon.getCoinAmount());

			return "쿠폰 사용 성공";
		} else {
			return "이미 사용된 쿠폰입니다.";
		}

	}

}
