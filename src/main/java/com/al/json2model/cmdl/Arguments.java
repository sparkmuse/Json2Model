package com.al.json2model.cmdl;

import java.io.File;

/**
 * Class to hold the arguments passed by the command line after they have been
 * parsed by the class ArgumentParser.
 * 
 * @author alfredo
 *
 */
public class Arguments {
	
	private String inputFile = null;
	private String outputFolder = null;
	private String language = null;
	
	public Arguments() {
		super();
	}
	
	public Arguments(String inputFile, String outputFolder, String language) {
		super();
		this.inputFile = inputFile;
		this.outputFolder = outputFolder;
		this.language = language;
	}
	
	/**
	 * Checks if the values in the arguments fields are valid.
	 * @return true if are valid, otherwise false.
	 */
	public boolean isValid() {	
		return (!hasDefaultValues() && 
				isValidInputFile() && 
				isValidOutputDirectory());
	}
	
	/**
	 * Checks if the output directory is valid.
	 * - If the file does not exist we try to create one.
	 * @return true if valid or if successfully created, otherwise false.
	 */
	private boolean isValidOutputDirectory() {
		
		File f = new File(outputFolder);
		
		if (!f.exists()) {
			return f.mkdirs();
		}
		
		return true;
		
	}
	
	/**
	 * Checks if the input file is valid.
	 * @return true if valid, otherwise false.
	 */
	private boolean isValidInputFile() {
		
		File f = new File(inputFile);
		return (f.exists() && f.isFile() && f.canRead());
	}
	
	private boolean hasDefaultValues() {
		return  (inputFile == null && 
				 outputFolder == null && 
				 language == null);
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
	public String getLanguage() {
		return  language.toUpperCase();
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	

	
}
