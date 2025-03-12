package com.healthmanage.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.healthmanage.config.EnvConfig;
import com.healthmanage.dao.AdminDAO;
import com.healthmanage.dto.UserSignUpDTO;
import com.healthmanage.model.Admin;
import com.healthmanage.model.Attendance;
import com.healthmanage.model.Gym;
import com.healthmanage.model.User;
import com.healthmanage.utils.SHA256;
import com.healthmanage.utils.Sort;
import com.healthmanage.utils.Time;
import com.healthmanage.view.AdminView;

public class AdminService {
	private CouponService couponservice;
	private AttendanceService attendanceService;
	private static AdminService instance;
	private AdminView adminView;
	private Time time;
	private AdminDAO adminDAO;
	private LogService logger;

	private AdminService() {
		this.couponservice = CouponService.getInstance();
		this.adminDAO = new AdminDAO();
		this.logger = LogService.getInstance();
		this.time = Time.getInstance();
		this.adminView = new AdminView();
		this.attendanceService = AttendanceService.getInstance();
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

//	public void memberDelete(String memberNum) { // 삭제
//		Gym.users.remove(memberNum);
//		logger.addLog(memberNum + "님의 User정보가 삭제되었습니다.");
//	}

	public Admin adminLogin(String adminId, String pw) {
		if (!Gym.admins.containsKey(adminId)) {
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

	public Admin addAdmin(UserSignUpDTO userDTO) {
		if (Gym.admins.containsKey(userDTO.getUserId())) {
			logger.addLog("회원가입 실패: 이미 존재하는 아이디 (" + userDTO.getUserId() + ")");
			return null;
		}

		String salt = SHA256.generateSalt();
		String hashedPw = SHA256.hashPassword(userDTO.getPassword(), salt);
		Admin newAdmin = new Admin(userDTO.getUserId(), hashedPw, userDTO.getName(), salt);

		Gym.admins.put(userDTO.getUserId(), newAdmin);

		logger.addLog("아이디 : " + newAdmin.getUserId() + " | 이름 : " + newAdmin.getName() + "님이 회원가입하셨습니다.");

		return newAdmin;
	}

	// 영어 소문자+숫자, 5~12자
	public boolean isValidId(String adminId) {
		return Pattern.matches("^[a-z0-9]{5,12}$", adminId);
	}

	// 8~16자, 대문자,숫자,소문자영문,특수문자 1개 이상 포함
	public boolean isValidPw(String adminPw) {
		return Pattern.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$", adminPw);
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
