package com.al.j2m.cmdl;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.al.j2m.template.TemplateTypes;

/**
 * Representation of the Options types and their constant values.
 * 
 * @author Alfredo Lopez
 *
 */
public enum OptionTypes {

	// @formatter:off

	FILE("f", "file", "FILE", "The file to be processed."),
	
	LANGUAGE("lang", "language", "LANGUAGE", "The language for the file output.\n Allowed languages:\n"),
	
	OPTION_OUT("o", "out", "OUTDIR", "The directory where to place the processed files. If ommited the same DIR as 'file' is used"),
	
	HELP("h", "help", "HELP", "Helps you get help :)");
	
	// @formatter:on

	private String option;
	private String longOption;
	private String name;
	private String description;

	private OptionTypes(String option, String longOption, String name, String description) {
		this.option = option;
		this.longOption = longOption;
		this.name = name;
		this.description = description;
	}

	public String getOption() {
		return option;
	}

	public String getLongOption() {
		return longOption;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	/**
	 * Gets description for the language option with a list of all the languages
	 * 
	 * @return description + language list
	 */
	public String getLanguageDescription() {
		final String separator = ", ";
		final String languages = StringUtils
				.join(Stream.of(TemplateTypes.values()).sorted().collect(Collectors.toList()), separator);
		return OptionTypes.LANGUAGE.getDescription() + languages;
	}

}
