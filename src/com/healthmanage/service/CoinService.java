package com.healthmanage.service;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.healthmanage.config.EnvConfig;
import com.healthmanage.dao.UserDAO;
import com.healthmanage.model.Gym;
import com.healthmanage.model.User;

public class CoinService {
	private static CoinService instance;
	private LogService logger;
	private UserDAO userDAO;

	private CoinService() {
		this.logger = LogService.getInstance();
		this.userDAO = UserDAO.getInstance();
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

		userDAO.saveUsers();
		logger.addLog(user.getUserId() + "님에게 " + coin + " 코인이 충전되었습니다.");
		writeCoinReceipt(user.getName(), user.getUserId(), money, coin, change, user.getCoin());
		return coin + " 코인 충전 완료!";
	}

	// 코인 충전 영수증 출력
	public void writeCoinReceipt(String userName, String userId, int inputMoney, int coin, int change, int coinAmount) {
		int price = coin * 1000;
		try {
			PrintWriter pw = new PrintWriter(EnvConfig.get("RECEIPT_PATH"));
			pw.println("┌────────────────────────────┐");
			pw.println("│        [ 영 수 증 ]         │");
			pw.println("├────────────────────────────┤");
			pw.printf("│ %-8s  %16s │\n", "충전코인", String.format("%,d", coin));
			pw.printf("│ %-8s  %16s │\n", "금액", String.format("%,d", price));
			pw.printf("│ %-8s  %16s │\n", "CASH", String.format("%,d", inputMoney));
			pw.printf("│ %-8s  %16s │\n", "CHANGE", String.format("%,d", change));
			pw.println("├────────────────────────────┤");
			pw.printf("│ %-8s  %16s │\n", "총 코인", String.format("%,d", coinAmount));
			pw.println("└────────────────────────────┘");
			pw.println();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 이체(출금)한 유저, 이체받을(입금) 유저, 코인 파일에 저장
	public String withdraw(int coin, User sender, User receiver) { // 유저 간 계좌 이체
		Map<String, User> backupData = new HashMap<>();
	    backupData.put(sender.getUserId(), new User(sender));  
	    backupData.put(receiver.getUserId(), new User(receiver));
		try {
			if (sender.getCoin() < coin) {
				return "코인이 부족합니다. 현재코인 : " + sender.getCoin();
			}
			// 출금처리
			sender.setCoin(sender.getCoin() - coin);
			// 입금처리
			receiver.setCoin(receiver.getCoin() + coin);
			// 파일저장
			userDAO.saveUsers(); // 🔹 여기서 호출
			// 로그기록
			logger.addLog(sender.getUserId() + "님이 " + receiver.getUserId() + "님에게 " + coin + "원 이체하셨습니다.");
			return "이체되었습니다. 현재코인 : " + sender.getCoin();
		} catch (Exception e) {
			backupData.forEach(Gym.users::put);
			logger.addLog(sender.getUserId() + "->" + receiver.getUserId() + " : " + coin + "원 이체 오류발생");
			return "이체 실패! 오류 발생: " + e.getMessage();
		}
	}
}
