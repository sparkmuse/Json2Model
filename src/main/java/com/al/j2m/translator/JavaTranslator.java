package com.al.j2m.translator;

import com.al.j2m.entity.Entity;
import com.al.j2m.entity.Property;
import static com.fasterxml.jackson.databind.node.JsonNodeType.*;

/**
 * Concrete class to translate from Json to Java.
 * 
 * @author Alfredo Lopez
 */
public class JavaTranslator extends Translator {

	public JavaTranslator(Entity entity) {
		super(entity);
	}

	@Override
	public Property marshall(Property property) {

		switch (property.getTypeOriginal()) {
		case ARRAY:
			property.setType("List");
			break;
		case BINARY:
			break;
		case BOOLEAN:
			property.setType("boolean");
			break;
		case MISSING:
			break;
		case NULL:
			break;
		case NUMBER:
			property.setType("Number");
			break;
		case OBJECT:
			property.setType("Object");
			break;
		case POJO:
			break;
		case STRING:
			property.setType("String");
			break;
		default:
			break;
		}
		return property;
	}
}
