package com.al.json2model.model;

import java.util.AbstractMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import com.al.j2m.util.NameUtils;
import com.al.json2model.general.ClassFile;
import com.al.json2model.general.DataType;
import com.al.json2model.model.properties.Language;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

/**
 * Model class for Java. This class will be in charge to model the data and
 * produce a file.java class.
 * 
 * @author alfredo
 *
 */
@SuppressWarnings("deprecation")
public class ModelJava extends ModelAbstract {

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
	 * @see ModelAbstract
	 */
	public ModelJava(String name, String json, Language language, String destFolder) {
		super(name, json, language, destFolder);
	}

	@Override
	protected DataType getPrimitiveDataType(Map.Entry<String, JsonElement> entry) {

		JsonPrimitive primivitive = entry.getValue().getAsJsonPrimitive();

		if (primivitive.isBoolean()) {
			return new DataType(entry.getKey(), "boolean", false);
		} else if (primivitive.isNumber()) {

			if (isDouble(primivitive.getAsString())) {
				return new DataType(entry.getKey(), "double", false);
			} else {
				return new DataType(entry.getKey(), "int", false);
			}

		} else if (primivitive.isString()) {
			return new DataType(entry.getKey(), "String", false);
		} else {
			return new DataType(entry.getKey(), "Object", false);
		}
	}

	private DataType getPrimitiveDataTypeBoxed(Map.Entry<String, JsonElement> entry) {

		JsonPrimitive primivitive = entry.getValue().getAsJsonPrimitive();

		if (primivitive.isBoolean()) {
			return new DataType(entry.getKey(), "Boolean", false);
		} else if (primivitive.isNumber()) {

			if (isDouble(primivitive.getAsString())) {
				return new DataType(entry.getKey(), "Double", false);
			} else {
				return new DataType(entry.getKey(), "Integer", false);
			}

		} else if (primivitive.isString()) {
			return new DataType(entry.getKey(), "String", false);
		} else {
			return new DataType(entry.getKey(), "Object", false);
		}
	}

	@Override
	protected DataType getArrayDataType(Map.Entry<String, JsonElement> entry) {

		final String format = "ArrayList<%s>";

		String type = null;
		String name = entry.getKey();
		String nameClass = null;
		JsonElement testType = entry.getValue().getAsJsonArray().get(0);

		if (testType.isJsonObject() || testType.isJsonArray()) {

			nameClass = NameUtils.getCapitalized(NameUtils.getSingular(entry.getKey()));
			type = String.format(format, nameClass);

		} else {

			Map.Entry<String, JsonElement> pair = new AbstractMap.SimpleEntry<String, JsonElement>(name, testType);

			DataType base = getPrimitiveDataTypeBoxed(pair);
			nameClass = base.getType();
			type = String.format(format, nameClass);
		}

		return new DataType(name, type, false);

	}

	@Override
	protected void prepareFiles() {

		final String fileExtension = ".java";

		// Java has only one class file to be created.
		ClassFile file = new ClassFile(modelName, fileExtension, destFolder, getBody());
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
			String type = t.isObject() ? StringUtils.capitalize(t.getName()) : t.getType();
			sb.append(String.format(language.PROPERTY_DECLARATION, type, t.getName()));
		}

		sb.append(language.NEW_LINE);

		return sb.toString();
	}

	@Override
	protected String getBodyConstructor() {

		StringBuilder sb = new StringBuilder();

		sb.append(String.format(language.CONSTRUCTOR_DECLARATION_START, modelName, getPropertiesAsArgument()));
		sb.append(language.CONTRUCTOR_SUPER);

		for (String propertyKey : properties.keySet()) {
			DataType t = properties.get(propertyKey);
			sb.append(String.format(language.CONTRUCTOR_PROPERTY_ASSIGNMENT, t.getName(), t.getName()));
		}

		sb.append(language.CONTRUCTOR_DECLARATION_END);

		return sb.toString();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected String getBodyGettersAndSetters() {

		StringBuilder sb = new StringBuilder();

		for (String propertyKey : properties.keySet()) {

			DataType t = properties.get(propertyKey);
			String getterSuffix = t.getType().equals("boolean") ? language.GETTER_NAME_SUFFIX_BOOLEAN
					: language.GETTER_NAME_SUFFIX;

			String getterName = getterSuffix + StringUtils.capitalize(t.getName());
			String setterName = language.SETTER_NAME_SUFFIX + WordUtils.capitalize(t.getName());

			// Add all the elements together.
			sb.append(String.format(language.GETTER_DECLARATION_START, t.getType(), getterName));
			sb.append(String.format(language.GETTER_BODY, t.getName()));
			sb.append(language.SETTER_DECLARATION_END);
			sb.append(String.format(language.SETTER_DECLARATION_START, setterName, t.getType(), t.getName()));
			sb.append(String.format(language.SETTER_BODY, t.getName(), t.getName()));
			sb.append(language.GETTER_DECLARATION_END);
		}

		return sb.toString();
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
