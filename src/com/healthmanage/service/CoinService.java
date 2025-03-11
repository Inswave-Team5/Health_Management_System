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

	public String addCoin(int money) {
		User user = (User) Gym.getCurrentUser();
		if (user == null) {
			return "로그인이 필요합니다.";
		}
		int coin = money / 1000; // 1000원당 1코인
		int change = money % 1000; // 거스름돈 (1000원 단위로 나누고 남은 금액)
		user.setCoin(user.getCoin() + coin);
		logger.addLog(user.getUserId() + "님에게 " + coin + " 코인이 충전되었습니다.");
		return coin + " 코인 충전 완료!";

	}

	// 이체(출금)한 유저, 이체받을(입금) 유저, 코인 파일에 저장
	public String withdraw(int coin, User sender, User receiver) { // 유저 간 계좌 이체
		if (sender.getCoin() >= coin) {
			sender.setCoin(sender.getCoin() - coin);
			receiver.setCoin(receiver.getCoin() + coin);
			logger.addLog(sender.getUserId() + "님이 " + receiver.getUserId() + "님에게 " + coin + "원 이체하셨습니다.");
			return "이체되었습니다. 현재코인 : "+sender.getCoin();
		} else {
			return "코인이 부족합니다. 현재코인 : "+ sender.getCoin();
		}
	}

//	// 코인 잔액 조회 -> user 필드의 getCoin() 사용
//	public String getCoin(User user) {
//		return "보유한 코인은 " + user.getCoin() + "입니다.";
//	}
}
