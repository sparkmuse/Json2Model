package com.al.j2m.template;

import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

/**
 * Wrapper class for the Template Engine from Velocity.
 * 
 *	
 */
public class TemplateEngine {
	
	private VelocityEngine engine;
	private VelocityContext context;
	private Template template;
	
	/**
	 * Creates a new instance of the class. 
	 * 
	 * @param templatePath the path to the template to  be used
	 * 	as entry template.
	 * 
	 * @see org.apache.velocity.app.VelocityEngine
	 * @see org.apache.velocity.Template
	 * @see org.apache.velocity.VelocityContext
	 */
	public TemplateEngine(final String templatePath) {
		this.engine = getDefaultEngine();
		this.template = this.engine.getTemplate(templatePath);
		this.context = new VelocityContext();
	}
	
	/**
	 * Merges the Contex and the Template.
	 * 
	 * We are trimming the returned string to deal a little bit 
	 * with the WhiteSpaceGobbling problem in velocity.
	 * 
	 * @return the merged value.
	 * 
	 * @see com.al.j2m.template.TemplateEngine
	 */
	public String merge() {
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		return writer.toString().trim();
	}
	
	/**
	 * Adds a value to the VelocityContext
	 * 
	 * @param key the key
	 * @param value the value to add
	 * 
	 * @see org.apache.velocity.VelocityContext
	 */
	public void put(String key, Object value) {
		context.put(key, value);
	}
	
	/**
	 * Creates a default TemplateEngine
	 * 
	 * @return an instance of the Template engine
	 */
	private VelocityEngine getDefaultEngine() {		
		VelocityEngine velocityEngine = new VelocityEngine();
		Properties properties = new Properties();
		properties.put(RuntimeConstants.RESOURCE_LOADER, "class");
		properties.put("class.resource.loader.class", org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader.class.getName());
		velocityEngine.init(properties);
		return velocityEngine;
	}	
}
