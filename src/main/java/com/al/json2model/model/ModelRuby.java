package com.al.json2model.model;


import java.util.Map;
import java.util.Map.Entry;

import com.al.json2model.general.ClassFile;
import com.al.json2model.general.DataType;
import com.al.json2model.model.properties.Language;
import com.google.gson.JsonElement;

/**
 * This class will be in charge to model the data and produce
 * a file.java class.
 * 
 * @author alfredo
 *
 */
public class ModelRuby extends ModelAbstract {
	
	public static final String INTERPRETER_DIRECTIVE = "#!/usr/bin/ruby";
	
	/**
	 * Default constructor.
	 * @param name Name of the class to be created.
	 * @param json JSON file to use as a blueprint.
	 * @param language The language used for the class to be created.
	 * @param destFolder Destination folder where to put the file(s).
	 */
	public ModelRuby(String name, String json, Language language, String destFolder) {
		super(name, json, language, destFolder);
	}
	
	
	/**
	 * There are no data types specifications in Python.
	 */
	@Override
	protected DataType getPrimitiveDataType(Map.Entry<String, JsonElement> entry) {
		return new DataType(entry.getKey(), "", false);
	}
	
	/**
	 * There are no data types specifications in Python.
	 */
	@Override
	protected DataType getArrayDataType(Entry<String, JsonElement> entry) {	
		return new DataType(entry.getKey(), "", false);
	}
	
	
	@Override
	protected void prepareFiles() {
		
		final String extension = ".rb";

		//Java has only one class file to be created
		ClassFile file = new ClassFile(modelName, extension, destFolder, getBody());
		files.add(file);
	}
	


	@Override
	protected String getBody() {
		
		// Prepare the body.
		String properties = getBodyProperties();
		String constructor = getBodyConstructor();
		String getterAndSetters = getBodyGettersAndSetters();
		
		StringBuilder sb = new StringBuilder();
		
		
		sb.append(INTERPRETER_DIRECTIVE);
		sb.append(language.NEW_LINE);
		sb.append(language.NEW_LINE);
		sb.append(String.format(language.CLASS_DECLARATION_START, modelName));
		sb.append(properties);
		sb.append(constructor);
		sb.append(language.NEW_LINE);
		sb.append(getLoadMethod());
		sb.append(getterAndSetters);
		sb.append(language.CLASS_DECLARATION_END);
		
		return sb.toString();
	}

	/**
	 * Classes do not declare the instance variables.
	 */
	@Override
	protected String getBodyProperties(){
		return"";
	}
	
	@Override
	protected String getBodyConstructor(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format(language.CONSTRUCTOR_DECLARATION_START, getPropertiesAsArgument()));
		sb.append(language.CONTRUCTOR_SUPER);

		for (String propertyKey : properties.keySet()) {
			DataType t = properties.get(propertyKey);
			sb.append(String.format(language.CONTRUCTOR_PROPERTY_ASSIGNMENT, t.getName(), t.getName()));
		}
		
		sb.append(language.CONTRUCTOR_DECLARATION_END);
		
		return sb.toString();
	}
	
	
	protected String getPropertiesAsArgument() {
		
		StringBuilder sb = new StringBuilder();

		for (String propertyKey : properties.keySet()) {
			
			DataType t = properties.get(propertyKey);
			sb.append(t.getName()).append(", ");
		}
		
		// Remove the last ', ' characters added.
		if (sb.length() > 2) {
			return sb.substring(0, sb.length() - 2);
		} else {	
			return "";
		}
	}
	
	@Override
	protected String getBodyGettersAndSetters(){
		// Not to be used as far as I know.
		return "";
	}
	
	@Override
	protected String getLoadMethod() {

		if (!topObject) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(language.METHOD_LOAD_START);
		sb.append(language.METHOD_LOAD_BODY);
		sb.append(language.METHOD_LOAD_END);
		
		return sb.toString();
		
	}
}
