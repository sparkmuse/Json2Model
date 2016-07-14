package com.al.json2model;

import com.al.json2model.cmdl.Arguments;
import com.al.json2model.cmdl.ArgumentParser;
import com.al.json2model.general.Language;
import com.al.json2model.model.ModelJava;

/**
 * Hello world!
 *
 */
public class App {
	
	public static final String  APP_NAME = "Json2Model";
	
	public static void main(String[] args) {
		
		// Validate the input
		ArgumentParser v = new ArgumentParser();
		v.parse(args);
		
		Arguments arguments = v.getArguments();

		if (arguments.isValid()) {
		
			//String json = "{ \"f1\":\"Hello\",\"f2\":{\"f3\":\"World\"}}";
			String json = "{\"widget\":{\"debug\":\"on\",\"window\":{\"title\":\"Sample Konfabulator Widget\",\"name\":\"main_window\",\"width\":500,\"height\":500},\"image\":{\"src\":\"Images/Sun.png\",\"name\":\"sun1\",\"hOffset\":250,\"vOffset\":250,\"alignment\":\"center\"},\"text\":{\"data\":\"Click Here\",\"size\":36,\"style\":\"bold\",\"name\":\"text1\",\"hOffset\":250,\"vOffset\":100,\"alignment\":\"center\"}}}";
			
			Language language = Language.JAVA;
			String name = "ClassF";
			ModelJava m = new ModelJava(name, json, language);
			m.parse();	
		}

	}
}
