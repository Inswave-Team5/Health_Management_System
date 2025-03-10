package com.healthmanage.service;

import com.healthmanage.model.User;
import com.healthmanage.view.UserView;
import com.healthmanage.dao.UserDAO;
import com.healthmanage.model.Gym;


public class CoinService {
	
	private static CoinService instance;
	private UserView userView;
	
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
	
	
	//이체(출금)한 유저, 이체받을(입금) 유저, 코인 파일에 저장
	public void withdraw(int coin, User sender, User receiver) {  //유저 간 계좌 이체
		if(coin > 0 & sender.getCoin() >= coin) {
			sender.setCoin(sender.getCoin() - coin);
			receiver.setCoin(receiver.getCoin() + coin);
			userView.showMessage("이체되었습니다.");
		}else {
			userView.showMessage("입력하신 코인을 다시 확인해주세요.");
		}
		
		if(Gym.users.containsKey(sender.getUserId())) {
			Gym.users.get(sender.getUserId()).setCoin(sender.getCoin());
		}
		
		if(Gym.users.containsKey(receiver.getUserId())) {
			Gym.users.get(receiver.getUserId()).setCoin(receiver.getCoin());
		}
			

	}
	
	


	

}
