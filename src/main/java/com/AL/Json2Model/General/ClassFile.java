package com.al.json2model.general;

import java.io.File;

public class ClassFile {
	
	private String name;
	private String extension;
	private String folder;
	private String contents;
	
	
	public ClassFile() {
		super();
	}
	
	/**
	 * @return the fullPath
	 */
	public String getFullPath() {
		return folder + File.separator + name + extension;
	}

	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	

	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}
	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}


	public String getExtension() {
		return extension;
	}


	public void setExtension(String extension) {
		this.extension = extension;
	}


	public String getFolder() {
		return folder;
	}


	public void setFolder(String folder) {
		this.folder = folder;
	}
	
	
}
