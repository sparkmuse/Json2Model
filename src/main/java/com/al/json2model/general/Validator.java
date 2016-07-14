package com.al.json2model.general;


import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.EnumUtils;

public class Validator {

	private static final String OPTION_FILE = "f";
	private static final String OPTION_LANGUAGE = "lang";
	private static final String OPTION_OUT = "o";
	private static final String OPTION_HELP = "h";
	// This array
	
	//Exposed Variables
	private String inputFile = null;
	private String outputFolder = null;
	private Language language = Language.UNKNOWN;
	private boolean valid;
	
	
	//Internal helpers
	private static String[] arguments;
	private Options options = new Options();
	private CommandLineParser parser = new DefaultParser();

	public Validator(String[] args) {
		super();
		arguments = args;

		// Add the appropriate Options.
		setOptions();
		
	}

	private void setOptions() {

		Option file = Option.builder(OPTION_FILE)
							  .longOpt("file")
							  .argName("FILE")
							  .desc("The file to be processed.")
							  .required(false)
							  .hasArg()
							  .build();
		options.addOption(file);
		
		Option lang = Option.builder(OPTION_LANGUAGE)
							  .longOpt("language")
							  .argName("LANG")
							  .desc("The language for the file output.\n Allowed langauges:\n" +  EnumUtils.getEnumList(Language.class).toString())
							  .required(false)
							  .hasArg()
							  .build();
		options.addOption(lang);
		
		Option out = Option.builder(OPTION_OUT)
							  .longOpt("out")
							  .argName("DIR")
							  .desc("The directory where to place the processed files. If ommited the same DIR as 'file' is used")
							  .required(false)
							  .hasArg()
							  .build();
			options.addOption(out);
		
		Option help = Option.builder(OPTION_HELP)
							.longOpt("help")
							.desc("Help")
							.required(false)
							.build();
		options.addOption(help);
		
	}

	public void parse() throws ParseException {

		CommandLine cmd = parser.parse(options, arguments);
		
		if (cmd.hasOption(OPTION_HELP)) {
			
			displayHelp();
			
		}else {
			if (cmd.hasOption(OPTION_FILE)) {
				System.out.println("f:\t" + cmd.getOptionValue(OPTION_FILE));
				inputFile = cmd.getOptionValue(OPTION_FILE);
			}
			
			if (cmd.hasOption(OPTION_LANGUAGE)) {
				getLangaugeFromString(cmd.getOptionValue(OPTION_LANGUAGE));
				System.out.println("lang:\t" + cmd.getOptionValue(OPTION_LANGUAGE) + "(" + language + ")");
			}
			
			if (cmd.hasOption(OPTION_OUT)) {
				System.out.println("o:\t" + cmd.getOptionValue(OPTION_OUT));
				outputFolder = cmd.getOptionValue(OPTION_OUT);
			}
		}
	}

	private void displayHelp() {
		
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("j2m", "A very small tool for people to get things done fast.", options, "", true);
	}
	
	private void getLangaugeFromString(String lang) {
		
		List <Language> languages = EnumUtils.getEnumList(Language.class);

		for (Language lang1 : languages) {
			if (lang1.toString().equalsIgnoreCase(lang.toString())) {
				language = Language.valueOf(lang1.toString());
			}
		}
				
	}
	
	
	/**
	 * @return the valid
	 */
	public boolean isValid() {
		valid = (inputFile != null && 
				outputFolder == null && 
				language != Language.UNKNOWN) ;
		return valid;
	}

}
