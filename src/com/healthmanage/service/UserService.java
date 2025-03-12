package com.healthmanage.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.healthmanage.config.EnvConfig;
import com.healthmanage.dao.UserDAO;
import com.healthmanage.dto.UserSignUpDTO;
import com.healthmanage.model.Gym;
import com.healthmanage.model.User;
import com.healthmanage.utils.SHA256;
import com.healthmanage.utils.Sort;

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

	public boolean verifyPassword(String memberNum, String pw) {
		User user = Gym.users.get(memberNum);
		if (user == null) {
			return false;
		}
		return SHA256.verifyPassword(pw, user.getSalt(), user.getPassword());
	}

	public void updatePassword(String memberNum, String newPw) {
		User user = (User)Gym.getCurrentUser();
		if (user == null) {
			return;
		}
		String newSalt = SHA256.generateSalt();
		String newHashedPw = SHA256.hashPassword(newPw, newSalt);
		user.setPassword(newHashedPw, newSalt);
		logger.addLog(memberNum + "님의 비밀번호가 변경되었습니다.");
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

	public String withdrawCoin(int coin, User receiver) {
		User sender = Gym.users.get(((User) Gym.getCurrentUser()).getUserId());
		return coinService.withdraw(coin, sender, receiver);
	}

	public String findUserId(String userId) { // 회원 검색조회
		if (Gym.users.containsKey(userId)) {
			return Gym.users.get(userId).toString();
		}
		return null;
	}

	// 회원 전체조회
	private Collection<User> findAllUser() {
		return Gym.users.values();
	}

	// 회원 이름순 정렬 후 전체조회
	public List<User> findAllUserSortName() {
		return findAllUser().stream().sorted(Comparator.comparing(User::getName)).collect(Collectors.toList());
	}
}