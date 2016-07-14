package com.al.json2model.cmdl;

import com.al.json2model.general.Language;

public class Arguments {
	
	private String inputFile = null;
	private String outputFolder = null;
	private Language language = Language.UNKNOWN;
	
	public Arguments() {
		super();
	}
	
	public Arguments(String inputFile, String outputFolder, Language language) {
		super();
		this.inputFile = inputFile;
		this.outputFolder = outputFolder;
		this.language = language;
	}
	
	
	public boolean isValid() {
		
		return (inputFile != null && 
				outputFolder != null && 
				language != Language.UNKNOWN) ;
	}
	
	
	/**
	 * @return the inputFile
	 */
	public String getInputFile() {
		return inputFile;
	}
	/**
	 * @param inputFile the inputFile to set
	 */
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}
	/**
	 * @return the outputFolder
	 */
	public String getOutputFolder() {
		return outputFolder;
	}
	/**
	 * @param outputFolder the outputFolder to set
	 */
	public void setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
	}
	/**
	 * @return the language
	 */
	public Language getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(Language language) {
		this.language = language;
	}
	

	
}
