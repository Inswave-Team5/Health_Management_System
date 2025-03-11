package com.healthmanage.service;

import java.util.HashMap;

import java.util.Scanner;

import com.healthmanage.model.User;

import com.healthmanage.utils.SHA256;

public class MembershipService {
	
	private AdminService adminservice = AdminService.getInstance();
	
	public void addMember() {
		
		Scanner scan = new Scanner(System.in);
		
		HashMap<String, User> user_dic = new HashMap<String, User>();
		
		String id = "";
		System.out.println("ID를 입력해주세요 :");
		id = scan.nextLine();
		if(!adminservice.isValidId(id)) {
			System.out.println("ID 형식이 올바르지 않습니다. 영소문자+숫자 포함 5~12자를 입력해주세요.");
		}
		
		if (user_dic.containsKey(id)) {
			System.out.println("이미 존재하는 ID 입니다.");
			return;
		}
		
		String pw = "";
		System.out.println("패스워드를 입력해주세요 : ");
		pw = scan.nextLine();
		
		if(!adminservice.isValidPw(pw)) {
			System.out.println("패스워드 형식이 올바르지 않습니다. 대문자,숫자,소문자영문,특수문자 1개 이상 포함한 8~16자를 입력해주세요.");
			return;
		}
		String salt = SHA256.generateSalt();
		String hashedPw = SHA256.hashPassword(pw, salt);
		
		String name ="";
		System.out.println("이름을 입력해주세요 : ");
		name = scan.nextLine();
		
		User user = new User(name, hashedPw, id, salt);
		user_dic.put(id, user);
		System.out.println(name + "님 회원가입이 완료됐습니다.");

	
	}
}
