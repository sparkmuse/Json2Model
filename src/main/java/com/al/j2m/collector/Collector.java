package com.al.j2m.collector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.al.j2m.entity.Data;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class Collector {

	private static final Logger LOG = LoggerFactory.getLogger(Collector.class);

	private ObjectMapper objectMapper;
	private JsonNode root;
	private List<Data> data;

	public Collector(String jsonString) {
		try {
			this.objectMapper = new ObjectMapper();
			this.root = objectMapper.readTree(jsonString);
			this.data = new ArrayList<>();
		} catch (Exception ex) {
			LOG.error("There was a problem processing the Json String.", ex);
		}
	}

	public List<Data> collect() {
		return collect(this.root);
	}

	private List<Data> collect(JsonNode root) {

		Iterator<Map.Entry<String, JsonNode>> iterator = root.fields();

		while (iterator.hasNext()) {

			Map.Entry<String, JsonNode> entry = iterator.next();

			Data dataEntry = new Data();
			dataEntry.setOriginalName(entry.getKey());
			dataEntry.setValue(entry.getValue());
			dataEntry.setType(entry.getValue().getNodeType());
			this.data.add(dataEntry);
			
			if (dataEntry.getType() == JsonNodeType.OBJECT) {
				collect(entry.getValue());
			}
		}

		return this.data;
	}
}
