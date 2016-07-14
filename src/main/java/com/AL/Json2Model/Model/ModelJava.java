package com.al.json2model.model;


import static com.al.json2model.model.properties.PropertiesJava.CLASS_DECLARATION_END;
import static com.al.json2model.model.properties.PropertiesJava.CLASS_DECLARATION_START;
import static com.al.json2model.model.properties.PropertiesJava.GETTER_BODY;
import static com.al.json2model.model.properties.PropertiesJava.GETTER_DECLARATION_END;
import static com.al.json2model.model.properties.PropertiesJava.GETTER_DECLARATION_START;
import static com.al.json2model.model.properties.PropertiesJava.GETTER_NAME_SUFFIX;
import static com.al.json2model.model.properties.PropertiesJava.GETTER_NAME_SUFFIX_BOOLEAN;
import static com.al.json2model.model.properties.PropertiesJava.METHOD_LOAD_BODY;
import static com.al.json2model.model.properties.PropertiesJava.METHOD_LOAD_END;
import static com.al.json2model.model.properties.PropertiesJava.METHOD_LOAD_START;
import static com.al.json2model.model.properties.PropertiesJava.NEW_LINE;
import static com.al.json2model.model.properties.PropertiesJava.PROPERTY_DECLARATION;
import static com.al.json2model.model.properties.PropertiesJava.SETTER_BODY;
import static com.al.json2model.model.properties.PropertiesJava.SETTER_DECLARATION_END;
import static com.al.json2model.model.properties.PropertiesJava.SETTER_DECLARATION_START;
import static com.al.json2model.model.properties.PropertiesJava.SETTER_NAME_SUFFIX;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.al.json2model.general.ClassFile;
import com.al.json2model.general.DataType;
import com.al.json2model.general.Language;
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
						dataType = new DataType(key,"Array", true); //TODO:Fix this later.
						
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
			return new DataType(entry.getKey(), "double", false); //TODO: Come back to this for now Double	
		} else if (primivitive.isString()) {
			return new DataType(entry.getKey(), "String", false);
		} else {
			return new DataType(entry.getKey(), "Object", false);
		}
	}

	@Override
	protected void prepareFiles() {

		//Java has only one class file to be created.
		ClassFile file = new ClassFile();
		file.setName(StringUtils.capitalize(name));
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
		
		sb.append(String.format(CLASS_DECLARATION_START, StringUtils.capitalize(name)));
		sb.append(properties);
		sb.append(constructor);
		sb.append(getLoadMethod());
		sb.append(getterAndSetters);
		sb.append(CLASS_DECLARATION_END);
		
		return sb.toString();
	}

	@Override
	protected String getBodyProperties(){
		
		StringBuilder sb = new StringBuilder();

		for (String propertyKey : properties.keySet()) {
			
			DataType t = properties.get(propertyKey);
			String type = t.isObject() ? StringUtils.capitalize(t.getName()) : t.getType();
			sb.append(String.format(PROPERTY_DECLARATION, type, t.getName()));
		}

		sb.append(NEW_LINE);
		
		return sb.toString();
	}
	
	@Override
	protected String getBodyConstructor(){
		//TODO: Implement Later
		return "";
	}
	
	
	@Override
	protected String getBodyGettersAndSetters(){
		
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
	
	@Override
	protected String getLoadMethod() {

		if (!topObject) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(METHOD_LOAD_START);
		sb.append(METHOD_LOAD_BODY);
		sb.append(METHOD_LOAD_END);
		
		return sb.toString();
		
	}
}
