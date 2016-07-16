package com.al.json2model.model.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.al.json2model.general.JsonReader;
import com.al.json2model.model.properties.Language;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Class to read from a JSON file of properties and parse it.
 * 
 * @author alfredo
 *
 */
public class PropertyReader extends JsonReader {
	
	private static final String LANGUAGES_KEY = "languages";

	JsonElement jsonTree = null;
	HashMap<String, Language> languages = new HashMap<>();

	public PropertyReader(String path) {
		super(path);
	}

	/**
	 * Parses a JSON file into the class
	 */
	public void parse() {

		Gson gson = new Gson();
		JsonParser parser = new JsonParser();

		try {
			jsonTree = parser.parse(super.getContent());
			JsonObject rootObject = jsonTree.getAsJsonObject();

			// Get all the keys
			Set<Map.Entry<String, JsonElement>> entrySet = rootObject.entrySet();

			// Iterate through them
			for (Map.Entry<String, JsonElement> entry : entrySet) {

				if (entry.getKey().equals(LANGUAGES_KEY)) {

					// Get the languages.
					JsonObject rootLanguage = entry.getValue().getAsJsonObject();
					Set<Map.Entry<String, JsonElement>> languagesSet = rootLanguage.entrySet();

					// Go through all the languages.
					for (Map.Entry<String, JsonElement> language : languagesSet) {

						JsonElement element = language.getValue();
						Language languageInstance = gson.fromJson(element, Language.class);
						languages.put(language.getKey(), languageInstance);
					}
				}
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return languages.toString();
	}

	/**
	 * @return the languages
	 */
	public HashMap<String, Language> getLanguages() {
		return languages;
	}

	/**
	 * @param languages the languages to set
	 */
	public void setLanguages(HashMap<String, Language> languages) {
		this.languages = languages;
	}
	
	
}
