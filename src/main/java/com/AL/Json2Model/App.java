package com.AL.Json2Model;

import com.AL.Json2Model.Helpers.Language;
import com.AL.Json2Model.Model.ModelJava;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		
		System.out.println(args[0]);
		
		// We the Json String
		Language language = Language.JAVA;
		String name = "ClassF";
		String json = "{ \"f1\":\"Hello\",\"f2\":{\"f3\":\"World\"}}";

		// a classy way
		ModelJava m = new ModelJava(name, json, language);
		m.parse();
		
		
	}
}
