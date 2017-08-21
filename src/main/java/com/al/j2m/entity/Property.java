package com.al.j2m.entity;

import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.node.JsonNodeType;

public class Property {

	private String nameOriginal;
	private JsonNodeType typeOriginal;
	private Optional<String> primitiveType;
	private String name;
	private String type;

	public Property() {
	}

	public Property(String name, JsonNodeType type) {
		this.nameOriginal = name;
		this.typeOriginal = type;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNameOriginal() {
		return nameOriginal;
	}

	public JsonNodeType getTypeOriginal() {
		return typeOriginal;
	}

	public Optional<String> getPrimitiveType() {
		return primitiveType;
	}

	public void setPrimitiveType(Optional<String> primitiveType) {
		this.primitiveType = primitiveType;
	}

}
