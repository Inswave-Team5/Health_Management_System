package com.healthmanage.service;

import java.util.*;
import java.util.regex.Pattern;

import com.healthmanage.config.EnvConfig;
import com.healthmanage.dao.AdminDAO;
import com.healthmanage.model.Coupon;
import com.healthmanage.model.Gym;
import com.healthmanage.model.Person;
import com.healthmanage.view.AdminView;
import com.healthmanage.utils.FileIO;
import com.healthmanage.utils.SHA256;

public class AdminService {
	private CouponService couponservice;
	private AttendanceService attendanceService;
	private static AdminService instance;
	private AdminView adminView;
	private AdminDAO adminDAO;
	private LogService logger;

	private AdminService() {
		this.couponservice = CouponService.getInstance();
		this.adminDAO = new AdminDAO();
		this.logger = LogService.getInstance();

	}

	public static AdminService getInstance() {
		if (instance == null) {
			instance = new AdminService();
		}
		return instance;
	}

	public void load() {
		adminDAO.loadAdmins(EnvConfig.get("ADMIN_FILE"));
		logger.addLog(EnvConfig.get("ADMIN_FILE") + " File LOAD");
	}

	public void save() {
		adminDAO.saveAdmins();
		logger.addLog(EnvConfig.get("ADMIN_FILE") + " File SAVE");
	}

	public void memberList() { // 회원 전체조회
		for (Person member : Gym.users.values()) {
			System.out.println(member);
		}
	}

	public String memberSearch(String memberNum) { // 회원 검색조회
		if (Gym.users.containsKey(memberNum)) {
			return Gym.users.get(memberNum).toString();
		} else {
			return null;
		}
	}

	public boolean pwChange(String memberNum, String pw) { // 비밀번호 수정
		// 로그인 상태에서 비밀번호 입력받아 맞는지 확인
		// 기존 비밀번호가 맞으면 새로운 비밀번호 변경
		String hashedPw = SHA256.encrypt(pw);

		if (!Gym.users.get(memberNum).getPassword().equals(hashedPw)) {
			adminView.showMessage("비밀번호가 올바르지 않습니다.");
			return false;
		}

		String newPw = adminView.getInput("새로운 비밀번호를 입력하세요.");
		String newHashedPw = SHA256.encrypt(newPw);
		Gym.users.get(memberNum).setPassword(newHashedPw);
		adminView.showMessage("비밀번호가 성공적으로 변경되었습니다.");
		logger.addLog(memberNum + "님의 비밀번호가 변경되었습니다.");
		return true;
	}

	public void memberDelete(String memberNum) { // 삭제
		Gym.users.remove(memberNum);
		logger.addLog(memberNum + "님의 User정보가 삭제되었습니다.");
	}

	public boolean adminLogin(String adminId, String pw) {
		String hashedPw = SHA256.encrypt(pw);

		if (!Gym.admins.containsKey(adminId)) {
			System.out.println("없는 아이디입니다.");
			return false;
		}

		else {
			if (!Gym.admins.get(adminId).getPassword().equals(hashedPw)) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				return false;
			} else {
				System.out.println("로그인 성공");
				return true;
			}
		}

	}

	// 영어 소문자+숫자, 5~12자
	public boolean isValidId(String adminId) {
		return Pattern.matches("^[a-z0-9]{5,12}$", adminId);
	}

	// 8~16자, 대문자,숫자,소문자영문,특수문자 1개 이상 포함
	public boolean isValidPw(String adminPw) {
		return Pattern.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$", adminPw);
	}

	public Collection<Coupon> findAllCoupon() {
		return couponservice.findAllCoupons();
	}

	public boolean addCoupon(String number, int coinAmount) {
		boolean result = couponservice.createCoupon(number, coinAmount);
		if(result) {
			logger.addLog(number + "번의 쿠폰이 추가되었습니다.");
		}
		return result;
	}

	public String deleteCoupon(String number) {
		Coupon coupon = couponservice.deleteCoupon(number);
		if (coupon == null) {
			return "삭제 실패 - 없는 쿠폰번호 입니다.";
		}
		logger.addLog(number + "번의 쿠폰이 삭제되었습니다.");
		return coupon.toString() + "삭제";
	}

	// 회원 아이디로 이름찾기
	public String findName(String memberNum) {
		if (Gym.users.containsKey(memberNum)) {
			return Gym.users.get(memberNum).getName();
		} else {
			adminView.showMessage("일치하는 회원이 없습니다! 다시 검색해주세요.");
			return null;
		}
	}

	// 개인 회원 출결 조회 (날짜 별로) xxx - 입장 . 퇴근. //회원 아이디와 날짜 입력 받고 회원 출결 출력
	public String UserAttendanceByDay(String memberNum, String date) {
		adminView.showMessage("[" + date + "]");
		return "[" + findName(memberNum) + "]" + attendanceService.getAttendacneByDay(memberNum, date);
	}

	// 개인 회원 출결 조회 (전체) xxx - 입장 . 퇴근. //회원 아이디 입력 받고 회원 출결 출력
	public void listUserAttendanceAll(String memberNum) {
		adminView.showMessage("[" + findName(memberNum) + "의 출결 기록]");
		List<String> attendanceList = attendanceService.listUserAttendaceAll(memberNum);
		for (String attendance : attendanceList) {
			adminView.showMessage(attendance);
		}
	}

	// 전체 회원 출결 조회 (날짜 별로) xxx - 입장 . 퇴근. //날짜 입력 받고 회원 출결 출력
	public void listAllUsersAttendanceByDay(String date) {
		adminView.showMessage("[" + date + "]");
		HashMap<String, String> map = attendanceService.listAllAttendanceByDay(date);
		for (String key : map.keySet()) {
			adminView.showMessage("[" + findName(key) + "]" + map.get(key));
		}
	}

}
