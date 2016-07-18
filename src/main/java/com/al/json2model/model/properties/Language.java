package com.al.json2model.model.properties;

/**
 * Class to keep all the information regarding a language.
 * 
 * @author alfredo
 *
 */
public class Language {
	
	/**
	 * General language specifiers.
	 */
	public String LANGUAGE_NAME;
	public String NEW_LINE;
	public String ASSIGNMENT;
	
	/**
	 * Class declaration specifiers.
	 */
	public String CLASS_DECLARATION_START;
	public String CLASS_DECLARATION_END;
	
	
	/**
	 * Header declarations for languages that use two files for 
	 * declarations.
	 */
	public String IMPORT_FILES;
	public String INTERFACE_DECLARATION_START;
	public String INTERFACE_DECLARATION_END;
	public String IMPLEMENTATION_DECLARATION_START;
	public String IMPLEMENTATION_DECLARATION_END;
	
	/**
	 * Properties specifiers.
	 */
	public String PROPERTY_DECLARATION;
	
	/**
	 * Constructor specifiers.
	 */
	public String CONSTRUCTOR_DECLARATION_INTERFACE;
	public String CONTRUCTOR_DECLARATION_END;
	public String CONSTRUCTOR_DECLARATION_START;
	public String CONTRUCTOR_SUPER;
	public String CONTRUCTOR_PROPERTY_ASSIGNMENT;
	
	/**
	 * Helper load method for top level objects specifiers.
	 */
	public String METHOD_LOAD_START;
	public String METHOD_LOAD_BODY;
	public String METHOD_LOAD_END;

	/**
	 * Getter specifiers.
	 */
	public String GETTER_DECLARATION_START;
	public String GETTER_DECLARATION_END;
	public String GETTER_NAME_SUFFIX;
	public String GETTER_NAME_SUFFIX_BOOLEAN;
	public String GETTER_BODY;

	/**
	 * Setter specifiers.
	 */
	public String SETTER_DECLARATION_START;
	public String SETTER_DECLARATION_END;
	public String SETTER_NAME_SUFFIX;
	public String SETTER_BODY;
	
}
