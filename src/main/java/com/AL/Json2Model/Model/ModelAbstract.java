package com.al.json2model.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.al.json2model.general.ClassFile;
import com.al.json2model.general.DataType;
import com.al.json2model.general.Language;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public abstract class ModelAbstract {
	
	protected String name;
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

	protected  ModelAbstract(String name, String json, Language language, String destFolder) {
		this.name = name;
		this.json = json;
		this.language = language;
		this.destFolder = destFolder;
	}

	protected abstract void parse();
	protected abstract DataType getPrimitiveDataType(Map.Entry<String, JsonElement> entry);
	

	/**
	 * @return the name
	 */
	protected String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	protected void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the json
	 */
	protected String getJson() {
		return json;
	}

	/**
	 * @param json the json to set
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
