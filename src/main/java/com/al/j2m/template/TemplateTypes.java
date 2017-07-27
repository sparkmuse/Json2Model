package com.al.j2m.template;

/**
 * Interface that contains all templates paths
 * 
 * @author Alfredo Lopez
 *
 */
public enum TemplateTypes {

	JAVA("java"), BVA("vba");
	
	private final static String PREFIX = "templates";
	private final static String SUFFIX = "class.vm";
	
	private String templateName;
	
	TemplateTypes(String name) {
		this.templateName = name;
	}

	public String url() {
		return PREFIX + "/" + templateName + "/" + SUFFIX;
	}
	
	public String getName() {
		return templateName;
	}
}
