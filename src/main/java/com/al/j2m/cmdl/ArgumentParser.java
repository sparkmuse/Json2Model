package com.al.j2m.cmdl;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.stream.Stream;

import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.al.j2m.util.PropertiesUtil;

/**
 * Class to parse the command line arguments and populate the the Arguments
 * property.
 * 
 * @author Alfredo Lopez
 *
 */

public class ArgumentParser extends DefaultParser {
	
	private static final Logger LOG = LoggerFactory.getLogger(ArgumentParser.class);

	/**
	 * Default constructor
	 */
	public ArgumentParser() {
		super();
		this.options = new Options();
		setOptions();
	}

	/**
	 * Adds all the options to the argument line.
	 * 
	 * We are modifying the options for the Help and the Language to include
	 * extra features:
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
	 * @see com.al.j2m.cmdl.OptionTypes
	 */
	private void updateLaguageDescription() {
		options.getOption(OptionTypes.LANGUAGE.getOption())
				.setDescription(OptionTypes.LANGUAGE.getLanguageDescription());
	}

	/**
	 * Change the argument for the help option to require no arguments
	 * 
	 * @see com.al.j2m.cmdl.OptionTypes
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
	public Arguments parse(String[] args) {

		try {
			super.parse(options, args);

			if (args.length == 0 || cmd.hasOption(OptionTypes.HELP.getOption())) {
				displayHelp();
				return Arguments.HELP;
			}

			String file = cmd.getOptionValue(OptionTypes.FILE.getOption());
			String language = cmd.getOptionValue(OptionTypes.LANGUAGE.getOption());
			String defaultOut = new File(cmd.getOptionValue(OptionTypes.FILE.getOption())).getParent();
			String out = cmd.getOptionValue(OptionTypes.OUT_DIR.getOption(), defaultOut);

			return new Arguments(file, out, language);

		} catch (ParseException e) {
			LOG.error("Error parsing the command line. {}", e.getMessage());
		}
		
		return Arguments.NO_ARGUMENTS;
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

		LOG.info(logo);
		LOG.info(name);
		LOG.info("{}\n", version);
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
		} catch (Exception ex) {
			LOG.error("Missing logo", ex);
		}

		return result;
	}
}
