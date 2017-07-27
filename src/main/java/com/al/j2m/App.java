package com.al.j2m;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.text.WordUtils;

import com.al.j2m.cmdl.ArgumentParser;
import com.al.j2m.cmdl.Arguments;
import com.al.json2model.general.JsonReader;
import com.al.json2model.general.PathUtils;
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
	
	public static void main(String[] args) {
		
//		String fullPropertyPath = PathUtils.getPropertiesFile();
//		
//		PropertyReader pr = new PropertyReader(fullPropertyPath);
//		pr.read();
//		pr.parse();
//		
		
		
		
		
		

		ArgumentParser argParser = new ArgumentParser();
		Arguments arguments = argParser.parse(args);
		
		if (arguments.isValid()) {
			
			JsonReader reader = new JsonReader(arguments.getInputFile());
			reader.read();
			
			// Get the parameters for the model.
			String name = FilenameUtils.getBaseName(arguments.getInputFile());
			name = WordUtils.capitalize(name);
			String json = reader.getContent();
		//	Language language = pr.getLanguages().get(arguments.getLanguage());
			String outputFolder = arguments.getOutputFolder();
			
			
		//	ModelAbstract model = ModelFactory.build(name, json, language, outputFolder);
		//	model.parse();
		//	model.save();
		}
	}
}
