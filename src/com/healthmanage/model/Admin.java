package com.healthmanage.model;

import java.io.Serializable;

public class Admin extends Person implements Serializable{

	Admin(String name, String password, String userId) {
		super(name, password, userId);
	}

}
