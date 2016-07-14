package com.al.json2model;

import com.al.json2model.cmdl.Arguments;
import com.al.json2model.cmdl.ArgumentParser;
import com.al.json2model.general.JsonReader;
import com.al.json2model.general.Language;
import com.al.json2model.model.ModelJava;

/**
 * Hello world!
 *
 */
public class App {
	
	public static final String  APP_NAME = "Json2Model";
	
	public static void main(String[] args) {
		
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
		
		String json = reader.getContent();
		Language language = Language.JAVA;
		String name = "ClassF";
		
		
		ModelJava m = new ModelJava(name, json, language, arguments.getOutputFolder());
		m.parse();
		m.save();
	}
}
