package com.al.j2m.cmdl;

import java.io.File;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Class to hold the arguments passed by the command line after they have been
 * parsed by the class ArgumentParser.
 * 
 * @author Alfredo Lopez
 *
 */
public class Arguments {

	public static final Arguments HELP = new Arguments(true);
	public static final Arguments NO_ARGUMENTS = new Arguments();

	private boolean isHelp = false;
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

	private Arguments(boolean isHelp) {
		this.isHelp = true;
	}

	/**
	 * Checks if the values in the arguments fields are valid.
	 * 
	 * @return true if are valid, otherwise false.
	 */
	public boolean isValid() {
		return (!hasDefaultValues() && isValidInputFile() && isValidOutputDirectory());
	}

	/**
	 * Checks if the output directory is valid. - If the file does not exist we
	 * try to create one.
	 * 
	 * @return true if valid or if successfully created, otherwise false.
	 */
	private boolean isValidOutputDirectory() {

		File f = new File(outputFolder);
		boolean result = f.exists();

		if (!f.exists()) {
			result = f.mkdirs();
		}

		return result;
	}

	/**
	 * Checks if the input file is valid.
	 * 
	 * @return true if valid, otherwise false.
	 */
	private boolean isValidInputFile() {

		File f = new File(inputFile);
		return (f.exists() && f.isFile() && f.canRead());
	}

	private boolean hasDefaultValues() {
		return (inputFile == null && outputFolder == null && language == null);
	}

	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	

	public boolean isHelp() {
		return isHelp;
	}

	public void setHelp(boolean isHelp) {
		this.isHelp = isHelp;
	}

	public String getInputFile() {
		return inputFile;
	}

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public String getOutputFolder() {
		return outputFolder;
	}

	public void setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
