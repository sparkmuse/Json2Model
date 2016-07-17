package com.al.json2model;

import org.apache.commons.io.FilenameUtils;

import com.al.json2model.cmdl.ArgumentParser;
import com.al.json2model.cmdl.Arguments;
import com.al.json2model.general.JsonReader;
import com.al.json2model.model.ModelAbstract;
import com.al.json2model.model.ModelFactory;
import com.al.json2model.model.properties.Language;
import com.al.json2model.model.properties.PropertyReader;

/**
 * Main application class. :)
 *
 */
public class App {
	
	public static final String APP_NAME = "Json2Model";
	public static final String PROPERTIES_FILE = "resources/properties.json";
	
	public static void main(String[] args) {

		PropertyReader pr = new PropertyReader(PROPERTIES_FILE);
		pr.read();
		pr.parse();

		ArgumentParser argParser = new ArgumentParser(pr.getLanguages().keySet());
		argParser.parse(args);
		
		Arguments arguments = argParser.getArguments();
		
		if (arguments.isValid()) {
			
			JsonReader reader = new JsonReader(arguments.getInputFile());
			reader.read();
			
			// Get the parameters for the model.
			String name = FilenameUtils.getBaseName(arguments.getInputFile());
			String json = reader.getContent();
			Language language = pr.getLanguages().get(arguments.getLanguage());
			String outputFolder = arguments.getOutputFolder();
			
			
			ModelAbstract model = ModelFactory.build(name, json, language, outputFolder);
			model.parse();
			model.save();
		}
	}
}
