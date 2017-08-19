package com.al.j2m;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.text.WordUtils;

import com.al.j2m.cmdl.ArgumentParser;
import com.al.j2m.cmdl.Arguments;
import com.al.j2m.collector.Collector;
import com.al.j2m.entity.Entity;
import com.al.json2model.general.JsonReader;

/**
 * Main application class. :)
 *
 */
public class App {

	public static final String APP_NAME = "Json2Model";

	public static void main(String[] args) throws Exception {

		ArgumentParser argParser = new ArgumentParser();
		Arguments arguments = argParser.parse(args);

		String jsonString = "{\"customer\":{\"firstName\": \"John\",\"lastName\": \"Doe\"},\"addresses\": [{\"street\": \"Faraway Creek\",\"number\": 1234,\"city\":	\"Never-land\"}],\"balance\": 23234.23,\"active\": true}";
		Collector collector = new Collector(jsonString, Optional.empty());
		
		List<Entity> list = collector.collect();
		
		for (Entity data : list) {
			System.out.println(data.toString());
		}
		

		if (arguments.isValid()) {

			JsonReader reader = new JsonReader(arguments.getInputFile());
			reader.read();

			// Get the parameters for the model.
			String name = FilenameUtils.getBaseName(arguments.getInputFile());
			name = WordUtils.capitalize(name);
			String json = reader.getContent();
			// Language language =
			// pr.getLanguages().get(arguments.getLanguage());
			String outputFolder = arguments.getOutputFolder();

			// ModelAbstract model = ModelFactory.build(name, json, language,
			// outputFolder);
			// model.parse();
			// model.save();
		}
	}
}
