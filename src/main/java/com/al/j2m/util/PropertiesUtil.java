package com.al.j2m.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;

public class PropertiesUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(PropertiesUtil.class);
	
	private static final ClassLoader classLoader = PropertiesUtil.class.getClassLoader();
	
	public static Properties getProperties() {
		
		final String filePath = "app.properties";
		Properties properties = new Properties();
		InputStream input = null;

		input = classLoader.getResourceAsStream(filePath);
		
		try {
			properties.load(input);
		} catch (IOException e) {
			LOG.error("Error reading the properties from file {}",filePath, e);
		}
		
		return properties;
	}
}
