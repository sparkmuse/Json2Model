package com.al.json2model.model;


import java.util.Map.Entry;

import com.al.json2model.general.DataType;
import com.al.json2model.model.properties.Language;
import com.google.gson.JsonElement;

/**
 * Model class for Java. This class will be in charge to model the data and produce
 * a file.java class.
 * 
 * @author alfredo
 *
 */
public class ModelJavaScript extends ModelAbstract {
	
	
	public ModelJavaScript(String name, String json, Language language, String destFolder) {
		super(name, json, language, destFolder);
	}
	
	@Override
	public void parse() {
		String funnnyMessage = "...do you really need this for JavaScript? :)";
		System.out.println(funnnyMessage);
	}

	
	@Override
	protected DataType getArrayDataType(Entry<String, JsonElement> entry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected DataType getPrimitiveDataType(Entry<String, JsonElement> entry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void prepareFiles() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getBody() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getBodyProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getBodyConstructor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getBodyGettersAndSetters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getLoadMethod() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
