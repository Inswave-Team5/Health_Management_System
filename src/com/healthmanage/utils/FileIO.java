package com.healthmanage.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.healthmanage.model.Attendance;
import com.healthmanage.model.Weight;

public class FileIO {
	public static void createFile(String filePath) {
		File file = new File(filePath);
        try {
            File parentDir = file.getParentFile(); // 상위 디렉토리 확인
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs(); // ✅ 디렉토리 자동 생성
            }
            if(file.exists()) {
            	return;
            }
            if (file.createNewFile()) {
                System.out.println("✅ 파일이 성공적으로 생성되었습니다: " + filePath);
            } else {
                System.out.println("⚠️ 파일 생성 실패 (이미 존재할 가능성 있음): " + filePath);
            }
        } catch (IOException e) {
            System.out.println("❌ 파일 생성 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
	}
	
	public static void infoSave(Object obj, String filePath) {
		File file = new File(filePath);
		File parentDir = file.getParentFile();
		
		if(parentDir != null && !parentDir.exists()) {
			parentDir.mkdirs();
		}

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
				oos.close();
				bos.close();
				fos.close();
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	public static <T> Map<String, T> infoLoad(String filePath, Class<T> obj) {
		File file = new File(filePath);
		
		// 파일이 존재하는지 확인
        if (!file.exists() || file.length() == 0) {
            System.out.println("⚠️ 파일이 존재하지 않습니다: " + filePath);
            return new HashMap<>(); // 기본 객체 반환 (또는 예외 발생 가능)
        }
		
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);

			return (Map<String, T>) ois.readObject();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				ois.close();
				bis.close();
				fis.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public static <T> Map<String, List<T>> infoMapLoad(String filePath) {
		System.out.println(filePath);
		File file = new File(filePath);

		// 파일이 존재하는지 확인
		if (!file.exists() || file.length() == 0) {
			System.out.println("⚠️ 파일이 존재하지 않습니다: " + filePath);
			return new HashMap<String, List<T>>(); // 기본 객체 반환 (또는 예외 발생 가능)
		}

		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);

			return (Map<String, List<T>>) ois.readObject();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				ois.close();
				bis.close();
				fis.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
