package com.al.json2model.general;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class to read contents from a file.
 * @author alfredo
 *
 */
public class JsonReader {
	
	private String content = "";
	private String filePath = null;
	
	public JsonReader() {
	}
	
	public JsonReader(String path) {
		filePath = path;
	}
	
	/**
	 * Reads the contents from the JSON file.
	 */
	public void read() {
		
		File file = new File(filePath);
		String charset = "UTF-8";
		byte[] bytes;
		
		try {
			bytes = Files.readAllBytes(Paths.get(file.getPath()));
			content = new String(bytes, charset);
		} catch (IOException e) {
		   	e.printStackTrace();
		}
	}
	

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
