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
			
		} else if (lang.equalsIgnoreCase("objective_c")) {
			
			model = new ModelObjectiveC(name, json, language, destFolder);
			
		} else if (lang.equalsIgnoreCase("visual_basic")) {
			
			model = new ModelVisualBasic(name, json, language, destFolder);
			
		} else if (lang.equalsIgnoreCase("type_script")) {
			
			model = new ModelTypeScript(name, json, language, destFolder);

		} else if (lang.equalsIgnoreCase("python")) {
			
			model = new ModelPython(name, json, language, destFolder);
			
		} else if (lang.equalsIgnoreCase("php")) {
			
			model = new ModelPhp(name, json, language, destFolder);
			
		} else if (lang.equalsIgnoreCase("vba")) {
			
			model = new ModelVBA(name, json, language, destFolder);
			
		} else if (lang.equalsIgnoreCase("ruby")) {
			
			model = new ModelRuby(name, json, language, destFolder);
		}

		return model;
	}
}
