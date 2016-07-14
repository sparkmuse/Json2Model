package com.al.json2model.cmdl;

import java.io.File;

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
		return (!hasDefaultValues() && 
				isValidInputFile() && 
				isValidOutputDirectory());
	}
	
	
	private boolean isValidOutputDirectory() {
		
		File f = new File(outputFolder);
		
		if (!f.exists()) {
			return f.mkdirs();
		}
		
		return true;
		
	}
	
	private boolean isValidInputFile() {
		
		File f = new File(inputFile);
		return (f.exists() && f.isFile() && f.canRead());
	}
	
	private boolean hasDefaultValues() {
		return  (inputFile == null && 
				 outputFolder == null && 
				 language == Language.UNKNOWN);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Arguments \n"
				+ "inputFile=\t" + inputFile + "\n"  
				+ "language=\t" + language + "\n"
				+ "outputFolder=\t" + outputFolder;
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
