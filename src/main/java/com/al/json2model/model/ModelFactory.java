package com.al.json2model.model;

import org.apache.commons.io.FilenameUtils;

import com.al.json2model.cmdl.Arguments;
import com.al.json2model.general.JsonReader;
import com.al.json2model.model.properties.Language;
import com.al.json2model.model.properties.PropertyReader;

/**
 * This class will create an dispense instances of the models need per language.
 * 
 * @author alfredo
 *
 */
public class ModelFactory {

	public static ModelAbstract build(Arguments arguments, JsonReader reader, PropertyReader properties) {
		
		ModelAbstract model = null;
		
		// Get the parameters for the model.
		String name = FilenameUtils.getBaseName(arguments.getInputFile());
		String json = reader.getContent();
		Language language = properties.getLanguages().get(arguments.getLanguage());
		String outputFolder = arguments.getOutputFolder();

		
		String lang = language.LANGUAGE_NAME;
		
		// Dispenser
		if (lang.equalsIgnoreCase("java")) {
			
			model = new ModelJava(name, json, language, outputFolder);
			
		} else if (lang.equalsIgnoreCase("c_sharp")) {
			
			model = new ModelCSharp(name, json, language, outputFolder);
			
		} else if (lang.equalsIgnoreCase("java_script")) {
			
			model = new ModelJavaScript(name, json, language, outputFolder);
			
		} else if (lang.equalsIgnoreCase("swift")) {
			
			model = new ModelSwift(name, json, language, outputFolder);
		}

		return model;
	}
}
