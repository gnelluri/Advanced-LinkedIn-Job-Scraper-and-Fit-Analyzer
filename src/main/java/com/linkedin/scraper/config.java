package com.linkedin.scraper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class config {
    private static Properties properties;

    static {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getEmail() {
        return properties.getProperty("email");
    }

    public static String getPassword() {
        return properties.getProperty("password");
    }
}