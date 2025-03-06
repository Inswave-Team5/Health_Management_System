package com.healthmanage.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvConfig {
    private static final Properties properties = new Properties();

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream(".env");
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("환경 변수 파일(.env) 로드 실패", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
