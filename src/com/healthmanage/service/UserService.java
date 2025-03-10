package com.healthmanage.service;

import java.util.regex.Pattern;

import com.healthmanage.dto.UserSignUpDTO;
import com.healthmanage.model.Gym;
import com.healthmanage.model.User;
import com.healthmanage.utils.SHA256;

public class UserService {
	private static UserService instance;
	private CouponService couponService;
	private CoinService coinService;
	private UserService() {
		this.couponService = CouponService.getInstance();
		this.coinService = CoinService.getInstance();
		
	}

	public static UserService getInstance() {
		if (instance == null) {
			instance = new UserService();
		}
		return instance;
	}

	public boolean checkId(String userId) {
		if (Gym.users.containsKey(userId)) {
			return false;
		}
		return true;
	}

	public void addUser(UserSignUpDTO userDTO) {
		Gym.users.put(userDTO.getUserId(), new User(userDTO.getUserId(), userDTO.getPassword(), userDTO.getName()));
	}

	public boolean userLogin(String userId, String pw) {
		String hashedPw = SHA256.encrypt(pw);
		if (Gym.users.containsKey(userId) && Gym.users.get(userId).getPassword().equals(pw)) {
			return true;
		} else {
			return false;
		}


	}

	
	//영어 소문자+숫자, 5~12자
	public boolean isValidId(String userId) {
		return Pattern.matches("^[a-z0-9]{5,12}$", userId);
	}
	
	//8~16자, 대문자,숫자,소문자영문,특수문자 1개 이상 포함
	public boolean isValidPw(String userPw) {
	    return Pattern.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$", userPw);
	}
	
	

	public String useCoupon(String couponNumber) {
		User user = null;
		return couponService.useCoupon(couponNumber, user);
	}
	
	public String addCoin(String money) {
		User user = null;
		return coinService.addCoin(money, user);
	}
	
	public String withdrawCoin(String coin, String senderId, String receiverId) {
		User sender = Gym.users.get(senderId);
		User receiver = Gym.users.get(receiverId);
		
		if (sender == null || receiver == null) {
	        return "송신자 또는 수신자를 찾을 수 없습니다.";
	    }
		
		String result = coinService.withdraw(coin, sender, receiver);
		
		Gym.users.get(sender.getUserId()).setCoin(sender.getCoin());
		Gym.users.get(receiver.getUserId()).setCoin(receiver.getCoin());
	
		return result;
	}
}