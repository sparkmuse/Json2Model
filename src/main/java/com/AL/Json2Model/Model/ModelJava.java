package com.al.json2model.model;


import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.al.json2model.general.ClassFile;
import com.al.json2model.general.DataType;
import com.al.json2model.general.JavaProperties;
import com.al.json2model.general.Language;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ModelJava extends ModelAbstract implements JavaProperties {

	public ModelJava(String name, String json, Language language) {
		super(name, json, language);
	}
	
	@Override
	public void parse() {

		JsonElement jsonTree = parser.parse(super.getJson());

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
					ModelJava m = new ModelJava(key, value.toString(), super.getLanguage());
					m.topObject = false;
					m.parse();
				}else if (value.isJsonArray()) {
					dataType = new DataType(key,"Array", true); //TODO:Fix this later.
					
				}else if (value.isJsonPrimitive()) {
					dataType = getPrimitiveDataType(entry);
				}

				super.getProperties().put(key, dataType);
				
			}	
		}
		
		// Process the file properties
		prepareBoby();
		
		// Print the class
		System.out.println(files.get(0).getContents());

	}
	
	@Override
	protected DataType getPrimitiveDataType(Map.Entry<String, JsonElement> entry) {
		
		JsonPrimitive primivitive = entry.getValue().getAsJsonPrimitive();
		
		if (primivitive.isBoolean()) {
			return new DataType(entry.getKey(), "boolean", false);
		} else if (primivitive.isNumber()) {
			return new DataType(entry.getKey(), "double", false); //TODO: Come back to this for now Double	
		} else if (primivitive.isString()) {
			return new DataType(entry.getKey(), "String", false);
		} else {
			return new DataType(entry.getKey(), "Object", false);
		}
	}

	@Override
	protected void prepareBoby() {
		
		//Java has only one class file to be created.
		ClassFile file = new ClassFile();
		file.setName(name);
//		file.setFullPath(?); //TODO:COme back and Fix this
		
		// Prepare the body.
		String properties = getBodyProperties();
		String constructor = getBodyConstructor();
		String getterAndSetters = getBodyGettersAndSetters();
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format(CLASS_DECLARATION_START, StringUtils.capitalize(name)));
		sb.append(properties);
		sb.append(constructor);
		sb.append(getLoadMethod());
		sb.append(getterAndSetters);
		sb.append(CLASS_DECLARATION_END);
		
		file.setContents(sb.toString());
		
		//Add the property
		files.add(file);
		
	}
	
	private String getLoadMethod() {

		if (!topObject) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(METHOD_LOAD_START);
		sb.append(METHOD_LOAD_BODY);
		sb.append(METHOD_LOAD_END);
		
		return sb.toString();
		
	}

	private String getBodyProperties(){
		
		StringBuilder sb = new StringBuilder();

		for (String propertyKey : properties.keySet()) {
			
			DataType t = properties.get(propertyKey);
			String type = t.isObject() ? StringUtils.capitalize(t.getName()) : t.getType();
			sb.append(String.format(PROPERTY_DECLARATION, type, t.getName()));
		}
		
		sb.append(NEW_LINE);
		
		return sb.toString();
	}
	
	private String getBodyConstructor(){
		//TODO: Implement Later
		return "";
	}
	
	private String getBodyGettersAndSetters(){
		
		StringBuilder sb = new StringBuilder();

		for (String propertyKey : properties.keySet()) {
			
			DataType t = properties.get(propertyKey);
			String getterSuffix = t.getType().equals("boolean") ? GETTER_NAME_SUFFIX_BOOLEAN : GETTER_NAME_SUFFIX;
			
			String getterName = getterSuffix + StringUtils.capitalize(t.getName());
			String setterName = SETTER_NAME_SUFFIX + StringUtils.capitalize(t.getName());
		
			// Add all the elements together.
			sb.append(String.format(GETTER_DECLARATION_START, getterName, t.getType(), t.getName()));
			sb.append(String.format(GETTER_BODY, t.getName()));
			sb.append(SETTER_DECLARATION_END);
			sb.append(String.format(SETTER_DECLARATION_START, setterName, t.getType(), t.getName()));
			sb.append(String.format(SETTER_BODY, t.getName(), t.getName()));
			sb.append(GETTER_DECLARATION_END);
		}
		
		return sb.toString();
	}
	
}
