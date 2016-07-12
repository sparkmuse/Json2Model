package com.AL.Json2Model;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		// We the Json String
		Language language = Language.JAVA;
		String name = "ClassF";
		String json = "{ \"f1\":\"Hello\",\"f2\":{\"f3:\":\"World\"}}";

		// a classy way
		ModelJava m = new ModelJava(name, json, language);
		m.parse();
		
		
	}
}
