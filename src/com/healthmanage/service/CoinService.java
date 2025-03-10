package com.healthmanage.service;

import com.healthmanage.model.User;

public class CoinService {
	
	private static CoinService instance;
	
	private CoinService() {
		
	}
	
	public static CoinService getInstance() {
		if(instance == null) {
			instance = new CoinService();
			
		}
		return instance;
	}
	
	public String addCoin(String money, User user) {
		int coin = Integer.parseInt(money);
		if (coin > 0){
			user.setCoin(user.getCoin() + coin);
			return coin + " 코인 충전완료";
		}
		return "충전현금을 입력해주세요.";
		
		
	}
	

}
