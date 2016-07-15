package com.al.json2model.model.properties;

public final class PropertiesJava {

	public static final String NEW_LINE = "\n";

	// The start declaration for the class.
	public static final String CLASS_DECLARATION_START = "public class %s {\n\n";
	public static final String CLASS_DECLARATION_END = "}\n";

	// The first element is the class type the second one is the class name.
	public static final String PROPERTY_DECLARATION = "\tprivate %s %s;\n";

	// The first element equals the second.
	public static final String ASSIGNMENT = "\t\t%s = %s\n";
	
	// Constructor Class name, all data types and a name
	public static final String CONSTRUCTOR_DECLARATION_START = "\tpublic %s(%s) {\n";
	public static final String CONTRUCTOR_SUPER = "\t\tsuper();\n";
	public static final String CONTRUCTOR_PROPERTY_ASSIGNMENT = "\t\tthis.%s = %s;\n";
	public static final String CONTRUCTOR_DECLARATION_END = "\t}\n";
	public static final String CONSTRUCTOR_PROPERTY_ARGUMENT = "%s %s "; 
	

	// Getter method name, data type, and name
	public static final String GETTER_DECLARATION_START = "\tpublic %s %s() {\n";
	public static final String GETTER_DECLARATION_END = "\t}\n";
	public static final String GETTER_NAME_SUFFIX = "get";
	public static final String GETTER_NAME_SUFFIX_BOOLEAN = "is";
	public static final String GETTER_BODY = "\t\treturn %s;\n";

	// Setter method name, data type. Data type is made of the type and the name
	// "int years"
	public static final String SETTER_DECLARATION_START = "\tpublic void %s(%s %s) {\n";
	public static final String SETTER_DECLARATION_END = "\t}\n";
	public static final String SETTER_NAME_SUFFIX = "set";
	public static final String SETTER_BODY = "\t\tthis.%s = %s;\n";

	// Method to load the data if the object is top.
	public static final String METHOD_LOAD_START = "\tpublic void load() {\n";
	public static final String METHOD_LOAD_BODY = "\t\t//TODO: Needs to be implemented.\n";
	public static final String METHOD_LOAD_END = "\t}\n";
	
}
