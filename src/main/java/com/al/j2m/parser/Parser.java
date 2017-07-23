package com.al.j2m.parser;

import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

public class Parser {
	
	private VelocityEngine engine;
	private VelocityContext context;
	private Template template;
	
	public Parser(final String templatePath) {
		this.engine = getDefaultEngine();
		this.template = this.engine.getTemplate(templatePath);
		this.context = new VelocityContext();
	}
	
	public String parse() {
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		return writer.toString();
	}
	
	public void put(String key, Object value) {
		context.put(key, value);
	}
	
	private VelocityEngine getDefaultEngine() {		
		VelocityEngine velocityEngine = new VelocityEngine();
		Properties properties = new Properties();
		properties.put(RuntimeConstants.RESOURCE_LOADER, "class");
		properties.put("class.resource.loader.class", org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader.class.getName());
		velocityEngine.init(properties);
		return velocityEngine;
	}
}
