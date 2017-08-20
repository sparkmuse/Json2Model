package com.al.j2m.collector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.al.j2m.entity.Entity;
import com.al.j2m.entity.Property;
import com.al.j2m.util.NameUtils;
import com.al.j2m.util.NameUtilsTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class Collector {

	private static final Logger LOG = LoggerFactory.getLogger(Collector.class);
	private static final String DEFAULT_ROOT_NAME = "root";

	private ObjectMapper objectMapper;
	private JsonNode root;
	private String rootName;
	private List<Entity> data;

	public Collector(String jsonString, Optional<String> rootName) {
		try {
			this.objectMapper = new ObjectMapper();
			this.root = objectMapper.readTree(jsonString);
			this.data = new ArrayList<>();
			this.rootName = rootName.orElse(DEFAULT_ROOT_NAME);

		} catch (Exception ex) {
			LOG.error("There was a problem processing the Json String.", ex);
		}
	}

	public List<Entity> collect() {
		collect(this.rootName, root);
		return this.data;
	}

	private void collect(String name, JsonNode node) {

		Entity entity = new Entity(name, node.getNodeType());
		List<Property> properties = new ArrayList<>();

		Iterator<Map.Entry<String, JsonNode>> iterator = node.fields();

		while (iterator.hasNext()) {

			Map.Entry<String, JsonNode> entry = iterator.next();

			Property property = new Property();
			property.setName(entry.getKey());
			property.setType(entry.getValue().getNodeType().toString());
			properties.add(property);
			
			if (entry.getValue().getNodeType().equals(JsonNodeType.OBJECT)) {
				collect(entry.getKey(), entry.getValue());
			}

			if (entry.getValue().getNodeType().equals(JsonNodeType.ARRAY)) {
				collect(entry.getKey(), entry.getValue().get(0));
			}
		}

		entity.setProperties(properties);
		this.data.add(entity);
	}
}
