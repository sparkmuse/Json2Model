package com.al.json2model.model;

import com.al.json2model.model.properties.Language;

/**
 * This class will create an dispense instances of the models need per language.
 * 
 * @author alfredo
 *
 */
public class ModelFactory {

	public static ModelAbstract build(String name, String json, Language language, String destFolder) {
		
		ModelAbstract model = null;
		String lang = language.LANGUAGE_NAME;
		
		// Dispenser
		if (lang.equalsIgnoreCase("java")) {
			
			model = new ModelJava(name, json, language, destFolder);
			
		} else if (lang.equalsIgnoreCase("c_sharp")) {
			
			model = new ModelCSharp(name, json, language, destFolder);
			
		} else if (lang.equalsIgnoreCase("java_script")) {
			
			model = new ModelJavaScript(name, json, language, destFolder);
			
		} else if (lang.equalsIgnoreCase("swift")) {
			
			model = new ModelSwift(name, json, language, destFolder);
		}

		return model;
	}
}
