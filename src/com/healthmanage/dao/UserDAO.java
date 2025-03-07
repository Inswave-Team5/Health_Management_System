package com.healthmanage.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.healthmanage.config.EnvConfig;
import com.healthmanage.model.Gym;

public class UserDAO {

	public void infoSave(Object obj, String filePath) {
		File file = new File(filePath);

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream oos = null;

		try {
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			oos = new ObjectOutputStream(bos);

			oos.writeObject(obj);
		} catch (Exception e) {
			// TODO: handle exception

		} finally {
			try {
				fos.close();
				bos.close();
				oos.close();
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	Object infoLoad(String filePath) {
		File file = new File(filePath);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);

			return ois.readObject();

		} catch (Exception e) {
			// TODO: handle exception
			return null;
		} finally {
			try {
				ois.close();
				bis.close();
				fis.close();
			} catch (Exception e2) {
				// TODO: handle exception

			}
		}
	}
	
	 public void saveUsers() {
	        infoSave(Gym.users, EnvConfig.get("USER_FILE"));
	    }
	 
	 public void saveAdmins() {
	        infoSave(Gym.admins, EnvConfig.get("ADMIN_FILE"));
	    }
	 
	 public void saveCoupons() {
	        infoSave(Gym.coupons, EnvConfig.get("COUPON_FILE"));
	    }
	 
	 public void loadUsers() {
		 infoLoad(EnvConfig.get("USER_FILE"));
	    }
	 
	 public void loadAdmins() {
		 infoLoad(EnvConfig.get("ADMIN_FILE"));
	    }
	 
	 public void loadCoupons() {
		 infoLoad(EnvConfig.get("COUPON_FILE"));
	    }
}
