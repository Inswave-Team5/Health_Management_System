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

	public void addUser(UserSignUpDTO userDTO) {
		Gym.users.put(userDTO.getUserId(), new User(userDTO.getUserId(), userDTO.getPassword(), userDTO.getName()));
	}

	public User userLogin(String userId, String pw) {
		if (Gym.users.containsKey(userId) && Gym.users.get(userId).getPassword().equals(pw)) {
			return Gym.users.get(userId);
		} else {
			return null;
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
		
		if(result.equals("이체되었습니다.")) {
		Gym.users.get(sender.getUserId()).setCoin(sender.getCoin());
		Gym.users.get(receiver.getUserId()).setCoin(receiver.getCoin());
		}
		
		return result;
	}
}