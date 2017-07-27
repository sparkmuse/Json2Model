package com.al.j2m.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	public static Properties getProperties() {
		
		Properties properties = new Properties();
		InputStream input = null;
		
		input = PropertiesUtil.class.getClassLoader().getResourceAsStream("app.properties");
		
		try {
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return properties;
	}
}
