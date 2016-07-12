package com.AL.Json2Model;

import java.util.HashMap;
import java.util.Map;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public abstract class ModelAbstract {
	
	private String name;
	private String json;
	private Map<String, String> properties;
	private Language language;
	
	//Object needed for the serialization.
	private JsonParser parser = new JsonParser();

	protected  ModelAbstract(String name, String json, Language language) {
		this.name = name;
		this.json = json;
		this.properties = new HashMap<>();
		this.language = language;
		this.parser = new JsonParser();
	}

	protected abstract void parse();
	protected abstract String getPrimitiveDataType(JsonElement value);

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
	protected Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	protected void setProperties(Map<String, String> properties) {
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
