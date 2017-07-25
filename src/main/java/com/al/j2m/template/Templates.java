package com.al.j2m.template;

/**
 * Interface that contains all templates paths
 * 
 * @author Alfredo Lopez
 *
 */
public enum Templates {

	JAVA("java");
	
	public final static String PREFIX = "templates";
	public final static String SUFFIX = "class.vm";
	
	private String url;
	
	Templates(String url) {
		this.url = url;
	}

	public String url() {
		return PREFIX + "/" + url + "/" + SUFFIX;
	}
}
