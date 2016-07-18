package com.al.json2model.model;


import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import com.al.json2model.general.ClassFile;
import com.al.json2model.general.DataType;
import com.al.json2model.model.properties.Language;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

/**
 * Model class for Java. This class will be in charge to model the data and produce
 * a file.java class.
 * 
 * @author alfredo
 *
 */
public class ModelObjectiveC extends ModelAbstract {
	
	/**
	 * Default constructor.
	 * @param name Name of the class to be created.
	 * @param json JSON file to use as a blueprint.
	 * @param language The language used for the class to be created.
	 * @param destFolder Destination folder where to put the file(s).
	 * @see ModelAbstract
	 */
	public ModelObjectiveC(String name, String json, Language language, String destFolder) {
		super(name, json, language, destFolder);
	}
	
	@Override
	protected DataType getPrimitiveDataType(Map.Entry<String, JsonElement> entry) {
		
		JsonPrimitive primivitive = entry.getValue().getAsJsonPrimitive();
		
		if (primivitive.isBoolean()) {
			return new DataType(entry.getKey(), "BOOL", false);
		} else if (primivitive.isNumber()) {
			return new DataType(entry.getKey(), "NSNumber", false);
		} else if (primivitive.isString()) {
			return new DataType(entry.getKey(), "NSString", false);
		} else {
			return new DataType(entry.getKey(), "id", false);
		}
	}
	
	@Override
	protected DataType getArrayDataType(Map.Entry<String, JsonElement> entry) {

		String name = entry.getKey();
		String type = "NSArray";
		
		return new DataType(name, type, false);
	}


	protected void prepareFiles() {

		// Prepare the .h interface file.
		ClassFile interfaceFile = new ClassFile();
		interfaceFile.setName(modelName);
		interfaceFile.setFolder(destFolder);
		interfaceFile.setExtension(".h");
		interfaceFile.setContents(getBodyInterface());
		
		//Add the file to the properties.
		files.add(interfaceFile);
		
		
		// Prepare the .m implementation file.
		ClassFile implementFile = new ClassFile();
		implementFile.setName(modelName);
		implementFile.setFolder(destFolder);
		implementFile.setExtension(".m");
		implementFile.setContents(getBodyImplementation());
		
		//Add the file to the properties.
		files.add(implementFile);

	}


	/**
	 * This method has been divided in two for this language
	 * getBody() = getBodyInterface() + getBodyImplementation()
	 */
	@Override
	protected String getBody() {	
		return "";
	}

	private String getBodyInterface() {
		
		String properties = getBodyProperties();
		String constructorDeclaration = getConstructorInterface();
		String loadMethodDeclaration = topObject?  "- (void) load;\n" : "";
		StringBuilder sb = new StringBuilder();
		
		sb.append(language.IMPORT_FILES);
		sb.append(String.format(language.INTERFACE_DECLARATION_START, modelName));
		sb.append(properties);
		sb.append(constructorDeclaration);
		sb.append(loadMethodDeclaration);
		sb.append(language.NEW_LINE);
		sb.append(language.INTERFACE_DECLARATION_END);
		
		return sb.toString();
	}

	
	private String getBodyImplementation() {
		//TODO: Fix the imports.
		String imports = getImplementationInports();
		
		// Prepare the body.
		String constructor = getBodyConstructor();
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(imports);
		sb.append(String.format(language.IMPLEMENTATION_DECLARATION_START, modelName));
		sb.append(constructor);
		sb.append(language.IMPLEMENTATION_DECLARATION_END);
		
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
	
	
	private String getConstructorInterface() {
		
		return String.format(language.CONSTRUCTOR_DECLARATION_INTERFACE, getPropertiesAsArgument());
	}
	
	@Override
	protected String getBodyConstructor(){
		
		//String 
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format(language.CONSTRUCTOR_DECLARATION_START ,getPropertiesAsArgument()));
		sb.append(language.NEW_LINE);
		sb.append(language.CONTRUCTOR_SUPER);
		sb.append(language.NEW_LINE);
		sb.append("\tif (self) {\n");

		for (String propertyKey : properties.keySet()) {
			DataType t = properties.get(propertyKey);
			sb.append(String.format(language.CONTRUCTOR_PROPERTY_ASSIGNMENT, t.getName(), t.getName()));
		}
		sb.append("\t}\n");
		sb.append("\treturn self\n");
		sb.append(language.CONTRUCTOR_DECLARATION_END);
		
		return sb.toString();
	}
	
	
	/**
	 * This procedure is not needed for this language.
	 */
	@Override
	protected String getBodyGettersAndSetters(){
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
	

	private String getImplementationInports(){
		
		String format = "#import \"%s.h\"\n";
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format(format, modelName));
		
		for (String propertyKey : properties.keySet()) {
			
			DataType t = properties.get(propertyKey);
			if (t.isObject()) {
				
				sb.append(String.format(format, t.getType()));
			}
		}
		
		sb.append(language.NEW_LINE);
		
		return sb.toString();
	}
	
	@Override
	protected String getPropertiesAsArgument() {
		
		boolean firstArgument = true;
		
		StringBuilder sb = new StringBuilder();
		
		for (String propertyKey : properties.keySet()) {
			
			DataType t = properties.get(propertyKey);
			String argument = null;
			String variable = null;
			
			String prefix = "and";
			if (firstArgument) {
				prefix = "With";
				firstArgument =false;
			}
			
			argument = prefix + WordUtils.capitalize(t.getName()) + ":";
			variable = "(" + WordUtils.capitalize(t.getType())+ " *)" + t.getName();
			
			// Add the rest of the values
			sb.append(argument).append(" ").append(variable).append(" ");
		}
		
		// Remove the last ' ' characters added.
		return sb.substring(0, sb.length() - 1);
	}
}
