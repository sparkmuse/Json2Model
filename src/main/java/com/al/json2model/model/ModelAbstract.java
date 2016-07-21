package com.al.json2model.model;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.text.WordUtils;

import com.al.json2model.general.ClassFile;
import com.al.json2model.general.DataType;
import com.al.json2model.general.NameUtils;
import com.al.json2model.model.properties.Language;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * Model class from which all the other models will be derived.
 * @author alfredo
 *
 */
public abstract class ModelAbstract {
	
	protected String modelName;
	protected String json;
	protected HashMap<String, DataType> properties  = new HashMap<>();
	protected Language language;
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
	 * @param langucccage The language used for the class
	 * @param destFolder The folder where to place the processed files.
	 */
	public  ModelAbstract(String name, String json, Language language, String destFolder) {
		this.modelName = name;
		this.json = json;
		this.language = language;
		this.destFolder = destFolder;
	}

	/**
	 * Method to parse the contents.
	 */
	public void parse() {

		JsonElement jsonTree = null;
		
		try {
			jsonTree = parser.parse(json);
			
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
						dataType = getObjectDataType(entry);
						
						processChildrenObjects(key, value);
						
					}else if (value.isJsonArray()) {
						dataType = getArrayDataType(entry);
						
						processArray(entry);
						
					}else if (value.isJsonPrimitive()) {
						dataType = getPrimitiveDataType(entry);
					}
					
					// Add the new property.
					properties.put(key, dataType);
				}	
			}	
			
			// Process the file properties only if there are properties to process.
			if (properties.size() > 0) {
				prepareFiles();
			}
			
			
		} catch (JsonSyntaxException e) {
			System.err.println(e.getMessage());
		} catch (JsonParseException e) {
			System.err.println(e.getMessage());
		}
	}

	
	/**
	 * Saves a file to the specified directory.
	 */
	public void save() {
		
		for (ClassFile file : files) {
			
			byte[] bytes = file.getContents().getBytes();
			
			try {		
				Files.write(Paths.get(file.getFullPath()), bytes);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	

	
	/**
	 * Method to process the arrays. Arrays have a lot of different ways to be
	 * represented in modern languages. 
	 * By rule we use the higher level representation structures of arrays (Lists, ArraysLists...)
	 * rather the lower level ones (Unless we have no other option like in C)
	 * 
	 * @param entry The entry to be analyzed.
	 * @return a data type for the array. TODO:Expand for other data types check before moving
	 */
	protected abstract DataType getArrayDataType(Map.Entry<String, JsonElement> entry);
	
	/**
	 * Method to process the data if they are primitive.
	 * @param entry The map entry for the object (aka the primitive)
	 * @return A DataType object.
	 */
	protected abstract DataType getPrimitiveDataType(Map.Entry<String, JsonElement> entry);
	

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
	 * Converts the properties list in a list  of DataType name separated by commas
	 * "Color color, String text"
	 * @return a list of DataTypes and names separated by commas.
	 */
	protected String getPropertiesAsArgument() {
		
		StringBuilder sb = new StringBuilder();

		for (String propertyKey : properties.keySet()) {
			
			DataType t = properties.get(propertyKey);
			String type = t.isObject() ? StringUtils.capitalize(t.getName()) : t.getType();
			sb.append(type).append(" ").append(t.getName()).append(", ");
		}
		
		// Remove the last ', ' characters added.
		if (sb.length() > 2) {
			return sb.substring(0, sb.length() - 2);
		} else {
			return "";
		}
	}
	
	
	/**
	 * Gets the data type for the objects. In most of the case the objects are going
	 * to have the DataType as the capital of the key.
	 * @param entry The entry to analyze
	 * @return
	 */
	protected DataType getObjectDataType(Map.Entry<String, JsonElement> entry) {
		
		String key = entry.getKey();
		return new DataType(key, NameUtils.getCapitalized(key), true);
	}
	
	
	/**
	 * Method to recursively process all the elements in a n array.
	 * @param entry The entry element that is an JsonArray object.
	 */
	protected void processArray(Map.Entry<String, JsonElement> entry) {
		
	
		String nameClass = NameUtils.getCapitalized(NameUtils.getSingular(entry.getKey()));
		
		//Recursively process the inner elements
		JsonArray array = entry.getValue().getAsJsonArray();
		for (JsonElement jsonElement : array) {
			
			if (jsonElement.isJsonObject()) {
				processChildrenObjects(nameClass, jsonElement);
			}
			
		}
	}	
	
	/**
	 * Method to process all children on an object recursively.
	 * @param key The name of the child Object
	 * @param value The JSON element.
	 */
	protected void processChildrenObjects(String key, JsonElement value) {
		
		key = WordUtils.capitalize(key);
		ModelAbstract m = ModelFactory.build(key, value.toString(), language, destFolder);
		m.topObject = false;
		m.parse();
		m.save();
	}
	
	/**
	 * Function to check if the value is a double or an integer.
	 * We rely on the fact that the numbers will flow as Strings and
	 * we try to find a decimal separator for the Locale.US
	 * @param number The number to be checked
	 * @return true if double, false otherwise
	 */
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
	protected Language getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	protected void setLanguage(Language language) {
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
