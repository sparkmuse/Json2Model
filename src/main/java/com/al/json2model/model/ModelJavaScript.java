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
	public void save() {
	}

	@Override
	protected DataType getPrimitiveDataType(Entry<String, JsonElement> entry) {
		return null;
	}

	@Override
	protected boolean isDouble(String number) {
		return false;
	}

	@Override
	protected void prepareFiles() {
	}

	@Override
	protected String getBody() {
		return null;
	}

	@Override
	protected String getBodyProperties() {
		return null;
	}

	@Override
	protected String getBodyConstructor() {
		return null;
	}

	@Override
	protected String getBodyGettersAndSetters() {
		return null;
	}

	@Override
	protected String getLoadMethod() {
		return null;
	}

	@Override
	protected void processArray(Entry<String, JsonElement> entry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processChildrenObjects(String key, JsonElement value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected DataType getArrayDataType(Entry<String, JsonElement> entry) {
		// TODO Auto-generated method stub
		return null;
	}
}
