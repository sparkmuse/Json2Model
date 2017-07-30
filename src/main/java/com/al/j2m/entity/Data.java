package com.al.j2m.entity;

import javax.swing.text.Utilities;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class Data {

	private String originalName;
	private JsonNode value;
	private JsonNodeType type;
	
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public JsonNode getValue() {
		return value;
	}
	public void setValue(JsonNode value) {
		this.value = value;
	}
	public JsonNodeType getType() {
		return type;
	}
	public void setType(JsonNodeType type) {
		this.type = type;
	}
}
