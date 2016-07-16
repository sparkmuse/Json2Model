package com.al.json2model.model;


import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.al.json2model.general.ClassFile;
import com.al.json2model.general.DataType;
import com.al.json2model.general.NameUtils;
import com.al.json2model.model.properties.Language;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

/**
 * Model class for Java. This class will be in charge to model the data and produce
 * a file.java class.
 * 
 * @author alfredo
 *
 */
public class ModelJava extends ModelAbstract {
	
	
	public ModelJava(String name, String json, Language language, String destFolder) {
		super(name, json, language, destFolder);
	}
	
	@Override
	public void parse() {

		JsonElement jsonTree = null;
		
		try {
			jsonTree = parser.parse(super.getJson());
			
			if (jsonTree.isJsonObject()) {
				JsonObject rootObject = jsonTree.getAsJsonObject(); // we assume the top object is an object.

				// Get all the keys
				Set<Map.Entry<String, JsonElement>> entrySet = rootObject.entrySet();

				// Iterate through them
				for (Map.Entry<String, JsonElement> entry : entrySet) {

					String key = entry.getKey();
					JsonElement value = entry.getValue();
					DataType dataType = null;
					
					if (value.isJsonObject()) {		
						dataType = new DataType(key, key, true);
						
						//Recursive way to get all the elements
						ModelJava m = new ModelJava(key, value.toString(), language, destFolder);
						m.topObject = false;
						m.parse();
						m.save();
						
					}else if (value.isJsonArray()) {
						
						dataType = getArrayDataType(entry);
						
						System.out.println("We found an array");
						
					}else if (value.isJsonPrimitive()) {
						dataType = getPrimitiveDataType(entry);
					}

					properties.put(key, dataType);
					
				}	
			}	
			
			// Process the file properties
			prepareFiles();
			
			
		} catch (JsonSyntaxException e) {
			
			System.err.println(e.getMessage());
		} catch (JsonParseException e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	@Override
	protected DataType getPrimitiveDataType(Map.Entry<String, JsonElement> entry) {
		
		JsonPrimitive primivitive = entry.getValue().getAsJsonPrimitive();
		
		if (primivitive.isBoolean()) {
			return new DataType(entry.getKey(), "boolean", false);
		} else if (primivitive.isNumber()) {
			
			if( isDouble(primivitive.getAsString())) {
				return new DataType(entry.getKey(), "double", false);
			}else {
				return new DataType(entry.getKey(), "int", false);
			}	
		} else if (primivitive.isString()) {
			return new DataType(entry.getKey(), "String", false);
		} else {
			return new DataType(entry.getKey(), "Object", false);
		}
	}
	
	
	private DataType getArrayDataType(Map.Entry<String, JsonElement> entry) {
		
		JsonArray array = entry.getValue().getAsJsonArray();
		
		String name = entry.getKey();
		String nameClass = NameUtils.getCapitalized(NameUtils.getSingular(entry.getKey()));
		String type = "ArrayList<" + nameClass + ">";
		
		for (JsonElement jsonElement : array) {
			
			//Recursive way to get all the elements
			ModelJava m = new ModelJava(nameClass, jsonElement.toString(), language, destFolder);
			m.topObject = false;
			m.parse();
			m.save();
		}
		
		//Gets the ArrayList Type itself.
		DataType dt = new DataType(name, type, false);
		return dt;
	}
	
	@Override
	protected boolean isDouble(String number) {
		
		if (NumberUtils.isNumber(number)) {
			
			DecimalFormatSymbols dsf = new DecimalFormatSymbols(Locale.US);
			char charSeparator = dsf.getDecimalSeparator();
			String separator = String.valueOf(charSeparator);
			
			if (number.contains(separator)) {
				return true;
			}
		}
		return false;
	}
	

	@Override
	protected void prepareFiles() {

		//Java has only one class file to be created.
		ClassFile file = new ClassFile();
		file.setName(StringUtils.capitalize(modelName));
		file.setFolder(destFolder);
		file.setExtension(".java");
		file.setContents(getBody());
		
		//Add the property
		files.add(file);
	}
	


	@Override
	protected String getBody() {
		
		// Prepare the body.
		String properties = getBodyProperties();
		String constructor = getBodyConstructor();
		String getterAndSetters = getBodyGettersAndSetters();
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format(language.CLASS_DECLARATION_START, StringUtils.capitalize(modelName)));
		sb.append(properties);
		sb.append(constructor);
		sb.append(getLoadMethod());
		sb.append(getterAndSetters);
		sb.append(language.CLASS_DECLARATION_END);
		
		return sb.toString();
	}

	@Override
	protected String getBodyProperties(){
		
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
	protected String getBodyConstructor(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format(language.CONSTRUCTOR_DECLARATION_START , StringUtils.capitalize(modelName), getPropertiesToString()));
		sb.append(language.CONTRUCTOR_SUPER);

		for (String propertyKey : properties.keySet()) {
			DataType t = properties.get(propertyKey);
			sb.append(String.format(language.CONTRUCTOR_PROPERTY_ASSIGNMENT, t.getName(), t.getName()));
		}
		
		sb.append(language.CONTRUCTOR_DECLARATION_END);
		
		return sb.toString();
	}
	
	
	@Override
	protected String getBodyGettersAndSetters(){
		
		StringBuilder sb = new StringBuilder();

		for (String propertyKey : properties.keySet()) {
			
			DataType t = properties.get(propertyKey);
			String getterSuffix = t.getType().equals("boolean") ? language.GETTER_NAME_SUFFIX_BOOLEAN : language.GETTER_NAME_SUFFIX;
			
			String getterName = getterSuffix + StringUtils.capitalize(t.getName());
			String setterName = language.SETTER_NAME_SUFFIX + StringUtils.capitalize(t.getName());
		
			// Add all the elements together.
			sb.append(String.format(language.GETTER_DECLARATION_START,t.getType(), getterName));
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
