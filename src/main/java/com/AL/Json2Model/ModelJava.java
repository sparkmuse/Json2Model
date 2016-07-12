package com.AL.Json2Model;

import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ModelJava extends ModelAbstract {

	public ModelJava(String name, String json, Language language) {
		super(name, json, language);
	}

	protected void parse() {

		JsonElement jsonTree = super.getParser().parse(super.getJson());

		if (jsonTree.isJsonObject()) {
			JsonObject rootObject = jsonTree.getAsJsonObject(); // we assume the top object is an object.

			// Get all the keys
			Set<Map.Entry<String, JsonElement>> entrySet = rootObject.entrySet();

			// Iterate through them
			for (Map.Entry<String, JsonElement> entry : entrySet) {

				String key = entry.getKey();
				JsonElement value = entry.getValue();
				String dataType = null;
				
				if (value.isJsonObject()) {
					//Recursive way to get all the elements
					dataType = key;
					ModelJava m = new ModelJava(key, value.toString(), super.getLanguage());
					m.parse();
				}else if (value.isJsonArray()) {
					dataType = "Array"; //TODO:Fix this later.
				}else if (value.isJsonPrimitive()) {
					dataType = getPrimitiveDataType(value);
				}

				super.getProperties().put(key, dataType);
				
			}
			System.out.println("My Object is:\t" + super.getName());
			System.out.println("My Properties are:\t" + super.getProperties().toString());
		}

	}
	
	
	protected String getPrimitiveDataType(JsonElement value) {
		
		JsonPrimitive primivitive = value.getAsJsonPrimitive();
		
		if (primivitive.isBoolean()) {
			return "boolean";
		} else if (primivitive.isNumber()) {
			return "double"; 	//TODO: Come back to this for now Double
		} else if (primivitive.isString()) {
			return "String";
		} else {
			return "";
		}
	}

}
