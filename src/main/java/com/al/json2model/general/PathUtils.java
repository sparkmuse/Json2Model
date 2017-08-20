package com.al.json2model.general;

import java.io.File;


/**
 * Class to hold Path utilities
 * 	
 * @author alfredo
 *
 */
public class PathUtils {
	
	public static final String PROPERTIES_FOLDER = "resources";
	public static final String  PROPERTIES_FILE = "properties.json";

	/**
	 * Get the top folder for the application and then add the path to access
	 * the properties JSON file.
	 * 
	 * There might be cleaner ways to do this. Research is pending.
	 * 
	 * Several code snippet  from the web but they don't work because they give the
	 * path relative to the application executable location.
	 * <pre>
	 * this.getClass().getProtectionDomain().getCodeSource().getLocation();
	 * 
	 * new File(".").getAbsolutePath();
	 * 
	 * System.getProperty("user.dir")
	 * </pre>
	 * @see <a href="http://stackoverflow.com/questions/218061/get-the-applications-path">http://stackoverflow.com/questions/218061/get-the-applications-path</a>
	 * @return
	 */
	public static String getPropertiesFile() {
		
		ClassLoader classLoader = PathUtils.class.getClassLoader();
		File file = new File(classLoader.getResource(PROPERTIES_FILE).getFile());
		
		return  file.getAbsolutePath();
	}
}
