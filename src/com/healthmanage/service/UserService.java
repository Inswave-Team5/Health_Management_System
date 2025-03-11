package com.healthmanage.service;

import java.util.regex.Pattern;

import com.healthmanage.config.EnvConfig;
import com.healthmanage.dao.UserDAO;
import com.healthmanage.dto.UserSignUpDTO;
import com.healthmanage.model.Gym;
import com.healthmanage.model.User;
import com.healthmanage.utils.FileIO;
import com.healthmanage.utils.SHA256;

public class UserService {
	private static UserService instance;
	private CouponService couponService;
	private CoinService coinService;
	private UserDAO userDAO;
	private LogService logger;

	private UserService() {
		this.couponService = CouponService.getInstance();
		this.coinService = CoinService.getInstance();
		this.userDAO = new UserDAO();
		this.logger = LogService.getInstance();
	}

	public static UserService getInstance() {
		if (instance == null) {
			instance = new UserService();
		}
		return instance;
	}

	public void load() {
		userDAO.loadUsers(EnvConfig.get("USER_FILE"));
		logger.addLog(EnvConfig.get("USER_FILE") + " File LOAD");
	}

	public void save() {
		userDAO.saveUsers();
		logger.addLog(EnvConfig.get("USER_FILE") + " File SAVE");

	}

	public boolean checkId(String userId) {
		if (Gym.users.containsKey(userId)) {
			return false;
		}
		return true;
	}

	public User addUser(UserSignUpDTO userDTO) {
		if (Gym.users.containsKey(userDTO.getUserId())) {
			logger.addLog("회원가입 실패: 이미 존재하는 아이디 (" + userDTO.getUserId() + ")");
			return null;
		}

		String salt = SHA256.generateSalt();
		String hashedPw = SHA256.hashPassword(userDTO.getPassword(), salt);
		User newUser = new User(userDTO.getUserId(), hashedPw, userDTO.getName(), salt);

		Gym.users.put(userDTO.getUserId(), newUser);

		logger.addLog("아이디 : " + newUser.getUserId() + " | 이름 : " + newUser.getName() + "님이 회원가입하셨습니다.");

		return newUser;
	}

	public User userLogin(String userId, String pw) {
		if (!Gym.users.containsKey(userId)) {
			return null;
		}
		User user = Gym.users.get(userId);

		boolean isPasswordValid = SHA256.verifyPassword(pw, user.getSalt(), user.getPassword());

		if (isPasswordValid) {
			logger.addLog(userId + "님이 로그인 하셨습니다.");
			return user;
		} else {
			return null;
		}
	}

	// 영어 소문자+숫자, 5~12자
	public boolean isValidId(String userId) {
		return Pattern.matches("^[a-z0-9]{5,12}$", userId);
	}

	// 8~16자, 대문자,숫자,소문자영문,특수문자 1개 이상 포함
	public boolean isValidPw(String userPw) {
		return Pattern.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$", userPw);
	}

	public String useCoupon(String couponNumber) {
		return couponService.useCoupon(couponNumber);
	}

	public String addCoin(int money) {
		return coinService.addCoin(money);
	}

	public String withdrawCoin(String coin, String senderId, String receiverId) {
		User sender = Gym.users.get(senderId);
		User receiver = Gym.users.get(receiverId);

		if (sender == null || receiver == null) {
			return "송신자 또는 수신자를 찾을 수 없습니다.";
		}

		String result = coinService.withdraw(coin, sender, receiver);

		if (result.equals("이체되었습니다.")) {
			Gym.users.get(sender.getUserId()).setCoin(sender.getCoin());
			Gym.users.get(receiver.getUserId()).setCoin(receiver.getCoin());
		}

		return result;
	}
}