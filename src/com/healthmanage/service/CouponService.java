package com.healthmanage.service;

import java.util.Collection;
import java.util.List;

import com.healthmanage.config.EnvConfig;
import com.healthmanage.dao.CouponDAO;
import com.healthmanage.model.Coupon;
import com.healthmanage.model.Gym;
import com.healthmanage.model.User;
import com.healthmanage.utils.Sort;

public class CouponService {
	private static CouponService instance;
	private CouponDAO couponDAO;
	private LogService logger;
	private CouponService() {
		couponDAO = new CouponDAO();
		logger = LogService.getInstance();
	}

	public static CouponService getInstance() {
		if (instance == null) {
			instance = new CouponService();
		}
		return instance;
	}

	// 쿠폰번호로 정렬후 조회
	public Collection<Coupon> findAllCoupons() {
		Collection<Coupon> coupons = Sort.sortCoupon(Gym.coupons.values());
		return coupons;
	}

	public void load() {
		couponDAO.loadCoupons(EnvConfig.get("COUPON_FILE"));
		logger.addLog(EnvConfig.get("COUPON_FILE") + " File LOAD");
		
	}

	public void save() {
		couponDAO.saveCoupons();
		logger.addLog(EnvConfig.get("COUPON_FILE") + " File SAVE");
	}

	private Coupon findCoupon(String number) {
		if (!Gym.coupons.containsKey(number)) {
			return null;
		}
		return Gym.coupons.get(number);
	}

	public Coupon createCoupon(String number, int coinAmount) {
	    if (Gym.coupons.containsKey(number)) {
	        return null;  // 이미 존재하는 경우 null 반환
	    }
	    Coupon newCoupon = new Coupon(number, coinAmount);
	    Gym.coupons.put(number, newCoupon);

	    logger.addLog(number + "번 쿠폰이 추가되었습니다.");

	    return newCoupon;  // 새로 생성된 쿠폰 반환
	}

	public Coupon deleteCoupon(String number) {
		Coupon coupon = Gym.coupons.remove(number);
		if (coupon != null) {
			logger.addLog(number + "번의 쿠폰이 삭제되었습니다.");			
		}
		return coupon;
	}

	public String useCoupon(String number) {
		Coupon coupon = findCoupon(number);
		User user = (User)Gym.getCurrentUser();
		if (coupon == null) {
			return "존재하지 않는 쿠폰입니다.";
		} else if (!coupon.isUsed()) {
			coupon.setUsed(true);
			user.setCoin(user.getCoin() + coupon.getCoinAmount());
			logger.addLog(number + "번의 쿠폰이 사용되었습니다.");
			return "쿠폰 사용 성공";
		} else {
			return "이미 사용된 쿠폰입니다.";
		}
	}
}
