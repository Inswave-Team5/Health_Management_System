package com.healthmanage.controller;

import static com.healthmanage.utils.Validations.*;

import com.healthmanage.model.Gym;
import com.healthmanage.service.WeightService;
import com.healthmanage.view.UserView;

public class WeightController {
	private final WeightService weightService;
	private final UserView view;

	public WeightController() {

		this.weightService = WeightService.getInstance();
		this.view = new UserView();
	}

	public void weightEntry() {
        int key = 0;
        while (Gym.isLoggedIn()) {
        	try {
        		key = Integer.parseInt(view.WeightSelectMenu());
        	}catch(NumberFormatException e) {
        		view.showAlert("숫자로된 메뉴 번호를 입력해주세요");
				continue;
        	}
            view.showMessage(key + "번 입력되었습니다.");
            switch (key) {
                case 1:
                    addWeight();
                    break;
                case 2:
                    checkEntry();
                    break;
                case 0:
                	view.showAlert("종료합니다.");
                	weightService.save();
                    return;
                default:
                    view.showMessage("잘못 선택하였습니다.");
                    break;
            }
        }
    }

	public void checkEntry() {
		int key = 0;
		while (Gym.isLoggedIn() && (key = Integer.parseInt(view.WeightSelectCheckMenu())) != 0) {
			view.showMessage(key + "번 입력되었습니다.");
			switch (key) {
			case 1:
				listWeight();
				break;
			case 2:
				listWeightByMonth();
				break;
			case 3:
				listWeightByDay();
				break;
			case 0:
				view.showAlert("종료합니다.");
				return;
			default:
				view.showMessage("잘못 선택하였습니다.");
				break;
			}
		}
	}

	public void addWeight() {
		// 로그인 검증
		if (!Gym.isLoggedIn()) {
			view.showMessage("로그인 후 이용가능합니다!");
			return;
		}

		String weight;
		while (true) {
			weight = view.getInput("몸무게 입력 (kg) : ");
			if (validatePositiveDecimal(weight)) {
				weightService.addWeight(Gym.getCurrentUser().getUserId(), weight);
				break; // 유효한 입력이 들어오면 반복문 종료
			} else {
				view.showMessage("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}

	}

	public void listWeight() {
		// 로그인 검증
		if (!Gym.isLoggedIn()) {
			view.showMessage("로그인 후 이용가능합니다!");
			return;
		}
		weightService.ListWeight(Gym.getCurrentUser().getUserId());
	}

	public void listWeightByMonth() {
		// 로그인 검증
		if (!Gym.isLoggedIn()) {
			view.showMessage("로그인 후 이용가능합니다!");
			return;
		}

		while (true) {
			String month = (view.getInput("월 입력 (입력 형식 : yyyy-MM) : "));
			if (validateYearMonth(month)) {
				weightService.ListWeightByMonth(Gym.getCurrentUser().getUserId(), month);
				break; // 유효한 입력이 들어오면 반복문 종료
			} else {
				view.showMessage("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
	}

	public void listWeightByDay() {
		// 로그인 검증
		if (!Gym.isLoggedIn()) {
			view.showMessage("로그인 후 이용가능합니다!");
			return;
		}

		while (true) {
			String day = (view.getInput("날짜 입력 (입력 형식 : yyyy-MM-dd) : "));
			if (validateYearMonthDay(day)) {
				weightService.ListWeightByDay(Gym.getCurrentUser().getUserId(), day);
				break; // 유효한 입력이 들어오면 반복문 종료
			} else {
				view.showMessage("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
	}

}
