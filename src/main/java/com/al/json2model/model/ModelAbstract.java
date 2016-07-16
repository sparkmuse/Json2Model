package com.al.json2model.model;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.al.json2model.general.ClassFile;
import com.al.json2model.general.DataType;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Model class from which all the other models will be derived.
 * @author alfredo
 *
 */
public abstract class ModelAbstract {
	
	protected String modelName;
	protected String json;
	protected HashMap<String, DataType> properties  = new HashMap<>();
	protected String language;
	protected boolean topObject = true;
	
	//Object needed for the serialization.
	protected JsonParser parser = new JsonParser();
	
	//The files to write
	protected ArrayList<ClassFile> files = new ArrayList<>();
	
	//The destination folder for the files.
	protected String destFolder;

	/**
	 * Default constructor for the class.
	 * @param modelName The modelName if the class
	 * @param json The JSON file string to be processed
	 * @param language The language used for the class
	 * @param destFolder The folder where to place the processed files.
	 */
	protected  ModelAbstract(String name, String json, String language, String destFolder) {
		this.modelName = name;
		this.json = json;
		this.language = language;
		this.destFolder = destFolder;
	}

	/**
	 * Method to parse the contents.
	 */
	protected abstract void parse();
	
	/**
	 * Method to process the data if they are primitive.
	 * @param entry The map entry for the object (aka the primitive)
	 * @return A DataType object.
	 */
	protected abstract DataType getPrimitiveDataType(Map.Entry<String, JsonElement> entry);
	
	/**
	 * Function to check if the value is a double or an integer.
	 * We rely on the fact that the numbers will flow as Strings and
	 * we try to find a decimal separator for the Locale.US
	 * @param number The number to be checked
	 * @return true if double, false otherwise
	 */
	protected abstract boolean isDouble(String number);
	
	
	/**
	 * Prepares the file for each individual class. It will get the information from
	 * the Model, create a ClassFile object and add it to the files collection
	 * of the model.
	 */
	protected abstract void prepareFiles();
	
	/**
	 * Gets the main block of the file body and puts them together.
	 * Ex: properties, getter & setters, constructors.
	 * @return
	 */
	protected abstract String getBody();
	
	
	/**
	 * Gets the properties and packs in a string format.
	 * @return The properties ready to print.
	 */
	protected abstract String getBodyProperties();
	
	/**
	 * Creates a constructor for the class.
	 * @return The constructors ready to print.
	 */
	protected abstract String getBodyConstructor();
	
	/**
	 * Creates properties getter and setter. 
	 * @return The getter and setters ready to print.
	 */
	protected abstract String getBodyGettersAndSetters();
	
	
	/**
	 * Method to return a helper method that is to be implemented by the
	 * top class of the JSON file. 
	 * This is a place holder since implementation will vary.
	 * @return
	 */
	protected abstract String getLoadMethod();
	
	
	/**
	 * Saves a file to the specified directory.
	 */
	public void save() {
		
		for (ClassFile file : files) {
			
			byte[] bytes = file.getContents().getBytes();
			
			try {		
				Files.write(Paths.get(file.getFullPath()), bytes, StandardOpenOption.CREATE);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	/**
	 * Converts the properties list in a list  of DataType name separated by commas
	 * "Color color, String text"
	 * @return a list of DataTypes and names separated by commas.
	 */
	protected String getPropertiesToString() {
		
		StringBuilder sb = new StringBuilder();

		for (String propertyKey : properties.keySet()) {
			
			DataType t = properties.get(propertyKey);
			String type = t.isObject() ? StringUtils.capitalize(t.getName()) : t.getType();
			sb.append(type).append(" ").append(t.getName()).append(", ");
		}
		
		// Remove the last ', ' characters added.
		return sb.substring(0, sb.length() - 2);
	}
	

	/**
	 * @return the modelName
	 */
	protected String getName() {
		return modelName;
	}

	/**
	 * @param modelName the modelName to set
	 */
	protected void setName(String name) {
		this.modelName = name;
	}

	/**
	 * @return the JSON
	 */
	protected String getJson() {
		return json;
	}

	/**
	 * @param json the JSON to set
	 */
	protected void setJson(String json) {
		this.json = json;
	}

	/**
	 * @return the properties
	 */
	protected Map<String, DataType> getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	protected void setProperties(HashMap<String, DataType> properties) {
		this.properties = properties;
	}

	/**
	 * @return the language
	 */
	protected String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	protected void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the parser
	 */
	protected JsonParser getParser() {
		return parser;
	}

	/**
	 * @param parser the parser to set
	 */
	protected void setParser(JsonParser parser) {
		this.parser = parser;
	}


	
}
