package com.al.j2m.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility to read the properties file.
 * 
 * @author Alfredo Lopez
 *
 */
public class PropertiesUtil {

	private static final Logger LOG = LoggerFactory.getLogger(PropertiesUtil.class);

	/**
	 * Default hidden constructor so this class is never instantiated.
	 */
	PropertiesUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Gets the properties file and reads them.
	 * 
	 * @see Properties
	 * @return a Properties Object
	 */
	public static Properties getProperties() {

		final String filePath = "app.properties";
		Properties properties = new Properties();
		InputStream input = PropertiesUtil.class.getClassLoader().getResourceAsStream(filePath);

		try {
			properties.load(input);
		} catch (IOException e) {
			LOG.error("Error reading the properties from file {}", filePath, e);
		}

		return properties;
	}
}
