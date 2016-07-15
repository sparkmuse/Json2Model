package com.al.json2model;

import java.io.IOException;

import com.al.json2model.cmdl.ArgumentParser;
import com.al.json2model.cmdl.Arguments;
import com.al.json2model.general.JsonReader;
import com.al.json2model.general.Language;
import com.al.json2model.model.ModelJava;
import com.al.json2model.model.properties.PropertyReader;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

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
		
		
		ArgumentParser v = new ArgumentParser();
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
		String json = reader.getContent();
		Language language = Language.JAVA;
		String name = "ClassF";
		
		
		ModelJava m = new ModelJava(name, json, language, arguments.getOutputFolder());
		m.parse();
		m.save();
	}
}
