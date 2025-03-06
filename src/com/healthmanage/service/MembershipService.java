package com.healthmanage.service;

import java.util.HashMap;
import java.util.Scanner;

import com.healthmanage.model.User;

public class MembershipService {
	
	public void addMember() {
		
		Scanner scan = new Scanner(System.in);
		
		HashMap<String, User> user_dic = new HashMap<String, User>();
		
		String id = "";
		System.out.println("ID를 입력해주세요 :");
		id = scan.nextLine();
		if (user_dic.containsKey(id)) {
			System.out.println("이미 존재하는 ID 입니다.");
			return;
		}
		
		String pw = "";
		System.out.println("패스워드를 입력해주세요 : ");
		pw = scan.nextLine();
		
		String name ="";
		System.out.println("이름을 입력해주세요 : ");
		name = scan.nextLine();
		
		User user = new User(name, pw, id);
		user_dic.put(id, user);
		System.out.println(name + "님 회원가입이 완료됐습니다.");

	
	}
}
