package com.al.json2model.model;

import java.util.Map;
import java.util.Map.Entry;

import com.al.json2model.general.ClassFile;
import com.al.json2model.general.DataType;
import com.al.json2model.model.properties.Language;
import com.google.gson.JsonElement;

/**
 * This class will be in charge to model the data and produce a file.java class.
 * 
 * @author alfredo
 *
 */
public class ModelPhp extends ModelAbstract {
	
	private static final String DATA_TYPE_PHP = "var";
	private static final String VARIABLE_PREFIX = "$";
			

	/**
	 * Default constructor.
	 * 
	 * @param name
	 *            Name of the class to be created.
	 * @param json
	 *            JSON file to use as a blueprint.
	 * @param language
	 *            The language used for the class to be created.
	 * @param destFolder
	 *            Destination folder where to put the file(s).
	 */
	public ModelPhp(String name, String json, Language language, String destFolder) {
		super(name, json, language, destFolder);
	}

	/**
	 * There are no data types specifications in PHP.
	 */
	@Override
	protected DataType getPrimitiveDataType(Map.Entry<String, JsonElement> entry) {
		return new DataType(VARIABLE_PREFIX + entry.getKey(), DATA_TYPE_PHP, false);
	}

	/**
	 * There are no data types specifications in Python.
	 */
	@Override
	protected DataType getArrayDataType(Entry<String, JsonElement> entry) {
		return new DataType(VARIABLE_PREFIX + entry.getKey(), DATA_TYPE_PHP, false);
	}

	@Override
	protected void prepareFiles() {

		String extension = ".php";

		// Java has only one class file to be created
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

		sb.append(String.format(language.CLASS_DECLARATION_START, modelName));
		sb.append(properties);
		sb.append(constructor);
		sb.append(getLoadMethod());
		sb.append(getterAndSetters);
		sb.append(language.CLASS_DECLARATION_END);

		return sb.toString();
	}

	@Override
	protected String getBodyProperties() {
		StringBuilder sb = new StringBuilder();

		for (String propertyKey : properties.keySet()) {

			DataType t = properties.get(propertyKey);
			sb.append(String.format(language.PROPERTY_DECLARATION, t.getType(), t.getName()));
		}

		sb.append(language.NEW_LINE);

		return sb.toString();
	}

	@Override
	protected String getBodyConstructor() {

		StringBuilder sb = new StringBuilder();

		sb.append(String.format(language.CONSTRUCTOR_DECLARATION_START, getPropertiesAsArgument()));
		sb.append(language.CONTRUCTOR_SUPER);

		for (String propertyKey : properties.keySet()) {
			DataType t = properties.get(propertyKey);
			sb.append(String.format(language.CONTRUCTOR_PROPERTY_ASSIGNMENT, t.getName().replace(VARIABLE_PREFIX, ""), t.getName()));
		}

		sb.append(language.CONTRUCTOR_DECLARATION_END);

		return sb.toString();
	}

	protected String getPropertiesAsArgument() {

		StringBuilder sb = new StringBuilder();

		sb.append(" ");
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
	
	/**
	 * The DataType is always going to be var.
	 */
	@Override
	protected DataType getObjectDataType(Map.Entry<String, JsonElement> entry) {

		return new DataType(VARIABLE_PREFIX + entry.getKey(), DATA_TYPE_PHP, true);
	}
	

	@Override
	protected String getBodyGettersAndSetters() {
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
