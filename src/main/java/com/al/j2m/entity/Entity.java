package com.al.j2m.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.al.j2m.util.NameUtils;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class Entity {

	private String originalName;
	private String name;
	private JsonNodeType type;
	private List<Property> properties;

	public Entity(String originalName, JsonNodeType type) {
		this.originalName = originalName;
		this.type = type;
		this.properties = new ArrayList<>();
		this.name = NameUtils.getSingular(originalName);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<Property> getProperties() {
		return properties;
	}
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public JsonNodeType getType() {
		return type;
	}
	public void setType(JsonNodeType type) {
		this.type = type;
	}
}
