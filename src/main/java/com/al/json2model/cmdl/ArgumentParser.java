package com.al.json2model.cmdl;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.stream.Stream;

import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.al.j2m.util.PropertiesUtil;

/**
 * Class to parse the command line arguments and populate the the Arguments
 * property.
 * 
 * @author Alfredo Lopez
 *
 */

public class ArgumentParser extends DefaultParser {

	private static final String OPTION_FILE = "f";
	private static final String OPTION_LANGUAGE = "lang";
	private static final String OPTION_OUT = "o";
	private static final String OPTION_HELP = "h";

	private Arguments arguments;
	private Options options;

	/**
	 * Default constructor
	 */
	public ArgumentParser() {
		super();
		this.arguments = new Arguments();
		this.options = new Options();
		setOptions();
	}

	/**
	 * Adds all the options to the argument line.
	 * 
	 * We are modifying the options for the Help and the Language to include
	 * extrafeatures:
	 * 
	 * Help needs no arguments Languages needs different description.
	 */
	private void setOptions() {
		Stream.of(OptionTypes.values()).forEach(t -> addOptionWithArgs(t));
		updateLaguageDescription();
		updateHelpNoArgs();
	}

	/**
	 * Adds an option to the command line with the arguments.
	 * 
	 * @param type option to add
	 */
	private void addOptionWithArgs(OptionTypes type) {
		// @formatter:off
		Option optionWithArgs = Option
				.builder(type.getOption())
				.longOpt(type.getLongOption())
				.argName(type.getName())
				.desc(type.getDescription())
				.hasArg(true)
				.numberOfArgs(1)
				.build();
		options.addOption(optionWithArgs);
		// @formatter:on
	}

	/**
	 * Change the description of the language option
	 * 
	 * @see com.al.json2model.cmdl.OptionTypes
	 */
	private void updateLaguageDescription() {
		options.getOption(OptionTypes.LANGUAGE.getOption())
				.setDescription(OptionTypes.LANGUAGE.getLanguageDescription());
	}

	/**
	 * Change the argument for the help option to require no arguments
	 * 
	 * @see com.al.json2model.cmdl.OptionTypes
	 */
	private void updateHelpNoArgs() {
		options.getOption(OptionTypes.HELP.getOption()).setArgs(0);
	}

	/**
	 * Parses the command line arguments and adds them to the Arguments object
	 * if possible.
	 * 
	 * @param args The command line arguments to be parsed.
	 */
	public void parse(String[] args) {

		try {

			parse(options, args);

			if (args.length == 0 || cmd.hasOption(OPTION_HELP)) {
				// Help has the highest priority.
				displayHelp();

			} else {
				if (cmd.hasOption(OPTION_FILE)) {
					String file = cmd.getOptionValue(OPTION_FILE);
					arguments.setInputFile(file);
				} else {
					throw new MissingArgumentException(currentOption);
				}

				if (cmd.hasOption(OPTION_LANGUAGE)) {
					String language = cmd.getOptionValue(OPTION_LANGUAGE);
					arguments.setLanguage(language);
				} else {
					throw new MissingArgumentException(currentOption);
				}

				if (cmd.hasOption(OPTION_OUT)) {
					String out = cmd.getOptionValue(OPTION_OUT);
					arguments.setOutputFolder(out);
				} else {
					File f = new File(arguments.getInputFile());
					arguments.setOutputFolder(f.getParentFile().getPath());
				}
			}
		} catch (ParseException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Procedure to print the help for the command line.
	 */
	private void displayHelp() {

		Properties properties = PropertiesUtil.getProperties();
		HelpFormatter formatter = new HelpFormatter();

		String cmdSyntax = "j2m";
		String description = "\n" + properties.getProperty("application.description") + "\n\n";
		String footer = "Enjoy";
		String name = properties.getProperty("application.name");
		String version = properties.getProperty("application.version");
		String logo = getLogo();

		System.out.println(logo);
		System.out.println(name);
		System.out.println(version);
		System.out.println();
		formatter.printHelp(cmdSyntax, description, options, footer, true);
	}

	/**
	 * Gets the logo from the resources folder.
	 * 
	 * @return the a string representation of the logo
	 */
	private String getLogo() {
		ClassLoader classLoader = getClass().getClassLoader();
		String result = StringUtils.EMPTY;

		try {
			result = FileUtils.readFileToString(new File(classLoader.getResource("logo.txt").getFile()),
					Charset.defaultCharset());
		} catch (Exception e) {
		}

		return result;
	}

	/**
	 * @return the arguments
	 */
	public Arguments getArguments() {
		return arguments;
	}
}
