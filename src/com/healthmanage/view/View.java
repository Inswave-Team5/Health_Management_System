package com.healthmanage.view;

import java.util.Scanner;

public class View {
    protected Scanner scan;

    public View() {
        this.scan = new Scanner(System.in);
    }

    private void printHeader(String title) {
        System.out.println("\n===================================");
        System.out.printf("          %s%n", title);
        System.out.println("===================================");
    }

    public String getInput(String message) {
		System.out.print(message);
		return scan.nextLine();
	}
    
    public String getMenuInput(String title, String message) {
        printHeader(title);
        System.out.println(message);
        System.out.print(">> ");
        return scan.nextLine();
    }

    public void showMessage(String message) {
        System.out.println("\n[💬 알림] " + message);
    }

    public String selectMenu() {
        return getMenuInput("MAIN MENU", 
                        "[1] 관리자 모드\n" +
                        "[2] 사용자 모드\n" +
                        "[0] 종료");
    }
}
