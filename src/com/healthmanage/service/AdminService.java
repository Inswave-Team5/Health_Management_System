package com.healthmanage.service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.healthmanage.model.Admin;
import com.healthmanage.model.Attendance;
import com.healthmanage.model.Coupon;
import com.healthmanage.model.Gym;
import com.healthmanage.model.Person;
import com.healthmanage.model.User;

import com.healthmanage.config.EnvConfig;
import com.healthmanage.dao.AdminDAO;
import com.healthmanage.model.Admin;
import com.healthmanage.model.Coupon;
import com.healthmanage.model.Gym;
import com.healthmanage.model.Person;
import com.healthmanage.model.User;
import com.healthmanage.view.AdminView;
import com.healthmanage.utils.FileIO;

import com.healthmanage.utils.SHA256;

import com.healthmanage.utils.Sort;
import com.healthmanage.utils.Time;

public class AdminService {
	private CouponService couponservice;
	private AttendanceService attendanceService;
	private static AdminService instance;
	private List<Attendance> attendanceList = new ArrayList<>();

	private AdminView adminView;
	private Time time;
	private AdminDAO adminDAO;
	private LogService logger;

	private AdminService() {
		this.couponservice = CouponService.getInstance();
		this.adminDAO = new AdminDAO();
		this.logger = LogService.getInstance();
		this.time = Time.getInstance();
	}

	public static AdminService getInstance() {
		if (instance == null) {
			instance = new AdminService();
		}
		return instance;
	}

	// 회원 이름순 정렬 후 전체조회
	public Collection<User> memberList() {
		List<User> users = Sort.sortUser(Gym.users.values());
		return users;
	}

	public void load() {
		adminDAO.loadAdmins(EnvConfig.get("ADMIN_FILE"));
		logger.addLog(EnvConfig.get("ADMIN_FILE") + " File LOAD");
	}

	public void save() {
		adminDAO.saveAdmins();
		logger.addLog(EnvConfig.get("ADMIN_FILE") + " File SAVE");
	}

	public String memberSearch(String memberNum) { // 회원 검색조회
		if (Gym.users.containsKey(memberNum)) {
			return Gym.users.get(memberNum).toString();
		} else {
			return null;
		}
	}

	public boolean pwChange(String memberNum, String pw) { // 비밀번호 수정

		User user = Gym.users.get(memberNum);
		if (user == null) {
			adminView.showMessage("사용자를 찾을 수 없습니다.");
			return false;
		}

		if (!SHA256.verifyPassword(pw, user.getSalt(), user.getPassword())) {
			adminView.showMessage("비밀번호가 올바르지 않습니다.");
			return false;
		}

		String newPw = adminView.getInput("새로운 비밀번호를 입력하세요.");
		String newSalt = SHA256.generateSalt();
		String newHashedPw = SHA256.hashPassword(newPw, newSalt);

		user.setPassword(newHashedPw, newSalt);

		adminView.showMessage("비밀번호가 성공적으로 변경되었습니다.");
		logger.addLog(memberNum + "님의 비밀번호가 변경되었습니다.");
		return true;
	}

//	public void memberDelete(String memberNum) { // 삭제
//		Gym.users.remove(memberNum);
//		logger.addLog(memberNum + "님의 User정보가 삭제되었습니다.");
//	}

	public Admin adminLogin(String adminId, String pw) {
		if (!Gym.users.containsKey(adminId)) {
			return null;
		}
		Admin admin = Gym.admins.get(adminId);

		boolean isPasswordValid = SHA256.verifyPassword(pw, admin.getSalt(), admin.getPassword());

		if (isPasswordValid) {
			logger.addLog(adminId + "님이 로그인 하셨습니다.");
			return admin;
		} else {
			return null;
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
		if (couponservice.createCoupon(number, coinAmount) == null) {
			return false;
		}
		return true;
	}

	public String deleteCoupon(String number) {
		Coupon coupon = couponservice.deleteCoupon(number);
		if (coupon == null) {
			return "삭제 실패 - 없는 쿠폰번호 입니다.";
		}
		return coupon.toString() + "삭제";
	}

	// 회원 운동시간 누적기준 정렬
	public Map<String, String> getRank() {
		// attendance list 받아오기

		// 시간 계산하기
		Map<String, String> tmpList = new HashMap<>();

		for (int i = 0; i < attendanceList.size(); i++) {
			String tmpId = attendanceList.get(i).getUserId();
			String tmpTime = attendanceList.get(i).getWorkOutTime();

			if (!tmpList.containsKey(tmpId)) {
				tmpList.put(tmpId, tmpTime);
			} else {
				String existingTime = tmpList.get(tmpId);
				Duration duration1 = time.totalDuration(existingTime);
				Duration duration2 = time.totalDuration(tmpTime);

				tmpList.replace(tmpId, duration1.plus(duration2).toString());
			}

		}

		// attendance list 넘겨주기
		Map<String, String> sortedList = Sort.sortRank2(tmpList);

		return sortedList;
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
		if(attendanceList==null|| attendanceList.isEmpty()){
			adminView.showMessage("기록이 없습니다.");
		}else{
			for (String attendance : attendanceList) {
				adminView.showMessage(attendance);
			}
		}
	}

	// 전체 회원 출결 조회 (날짜 별로) xxx - 입장 . 퇴근. //날짜 입력 받고 회원 출결 출력
	public void listAllUsersAttendanceByDay(String date) {
		adminView.showMessage("[" + date + "]");
		HashMap<String, String> map = attendanceService.listAllAttendanceByDay(date);
		if(map==null||map.isEmpty()){
			adminView.showMessage("기록이 없습니다.");
		}else{
			for (String key : map.keySet()) {
				adminView.showMessage("[" + findName(key) + "]" + map.get(key));
		}
		}
	}

}
