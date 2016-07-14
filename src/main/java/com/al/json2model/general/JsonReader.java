package com.al.json2model.general;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonReader {
	
	private String content = null;
	private String filePath = null;
	
	public JsonReader(String path) {
		filePath = path;
	}
	
	public void read() {
		
		String charset = "UTF-8";
		byte[] bytes;
		
		try {
			bytes = Files.readAllBytes(Paths.get(filePath));
			content = new String(bytes, charset);
			
		} catch (IOException e) {
		   	System.out.println(e.getMessage());
		}
	}
	
	

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
