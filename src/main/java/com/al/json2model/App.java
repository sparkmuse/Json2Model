package com.al.json2model;

import com.al.json2model.cmdl.ArgumentParser;
import com.al.json2model.cmdl.Arguments;
import com.al.json2model.general.JsonReader;
import com.al.json2model.general.NameUtils;
import com.al.json2model.model.ModelJava;
import com.al.json2model.model.properties.Language;
import com.al.json2model.model.properties.PropertyReader;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.text.WordUtils;

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

		
		ArgumentParser v = new ArgumentParser(pr.getLanguages().keySet());
		v.parse(args);
		
		Arguments arguments = v.getArguments();
		if (!arguments.isValid()) {
			return;
		}
		
		
		JsonReader reader = new JsonReader(arguments.getInputFile());
		reader.read();
		
		if(reader.getContent() == null) {
			return;
		}
		
		//Get the modelName of the top class from the file modelName.
		String name = FilenameUtils.getBaseName(arguments.getInputFile());
		String json = reader.getContent();
		Language language =  pr.getLanguages().get(arguments.getLanguage());
		String outputFolder = arguments.getOutputFolder();
		
		
		ModelJava m = new ModelJava(name, json, language, outputFolder);
		m.parse();
		m.save();
	}
}
