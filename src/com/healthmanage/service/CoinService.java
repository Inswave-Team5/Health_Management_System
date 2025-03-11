package com.healthmanage.service;

import com.healthmanage.model.User;
import com.healthmanage.view.UserView;
import com.healthmanage.dao.UserDAO;
import com.healthmanage.model.Gym;

public class CoinService {
	private static CoinService instance;
	private LogService logger;

	private CoinService() {
		this.logger = LogService.getInstance();
	}

	public static CoinService getInstance() {
		if (instance == null) {
			instance = new CoinService();
		}
		return instance;
	}

	public String addCoin(int coin) {
		User user = (User) Gym.getCurrentUser();
		if (user == null) {
			return "로그인이 필요합니다.";
		}
		user.setCoin(user.getCoin() + coin);
		logger.addLog(user.getUserId() + "님에게 " + coin + " 코인이 충전되었습니다.");
		return coin + " 코인 충전 완료!";

	}

	// 이체(출금)한 유저, 이체받을(입금) 유저, 코인 파일에 저장
	public String withdraw(String coin, User sender, User receiver) { // 유저 간 계좌 이체
		int rcoin = Integer.parseInt(coin);
		if (rcoin > 0 && sender.getCoin() >= rcoin) {
			sender.setCoin(sender.getCoin() - rcoin);
			receiver.setCoin(receiver.getCoin() + rcoin);
			logger.addLog(sender.getUserId() + "님이 " + receiver.getUserId() + "님에게 " + coin + "원 이체하셨습니다.");
			return "이체되었습니다.";
		} else {
			return "입력하신 코인을 다시 확인해주세요.";
		}
	}

//	// 코인 잔액 조회 -> user 필드의 getCoin() 사용
//	public String getCoin(User user) {
//		return "보유한 코인은 " + user.getCoin() + "입니다.";
//	}
}
