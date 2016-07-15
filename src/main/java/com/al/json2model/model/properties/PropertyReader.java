package com.al.json2model.model.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.al.json2model.general.JsonReader;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PropertyReader extends JsonReader {
	
	private static String LANGUAGES_KEY = "languages";

	JsonElement jsonTree = null;
	HashMap<String, Language> languages = new HashMap<>();

	public PropertyReader(String path) {
		super(path);
	}

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
}
