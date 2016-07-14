package com.al.json2model.cmdl;

import java.io.File;

import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.EnumUtils;

import com.al.json2model.general.Language;

/**
 * Class to parse the command line arguments and populate the the Arguments
 * property.
 * @author alfredo
 *
 */

public class ArgumentParser extends DefaultParser{

	private static final String OPTION_FILE = "f";
	private static final String OPTION_LANGUAGE = "lang";
	private static final String OPTION_OUT = "o";
	private static final String OPTION_HELP = "h";
	
	//Exposed Variables
	Arguments arguments = new Arguments();
	
	//Internal helpers
	Options options = new Options();

	
	public ArgumentParser() {
		super();
		setOptions();
	}

	/**
	 * Adds all the options to the argument line.
	 */
	private void setOptions() {
		
		String longOpt, argName, desc;
		
		longOpt = "file";
		argName = "FILE";
		desc = "The file to be processed.";
		Option file = Option.builder(OPTION_FILE).longOpt(longOpt).argName(argName).desc(desc).hasArg().build();
		options.addOption(file);
		
		
		longOpt = "language";
		argName = "LANG";
		desc = "The language for the file output.\n Allowed langauges:\n" +  EnumUtils.getEnumList(Language.class).toString();
		Option lang = Option.builder(OPTION_LANGUAGE).longOpt(longOpt).argName(argName).desc(desc).hasArg().build();
		options.addOption(lang);
		
		
		longOpt = "out";
		argName = "DIR";
		desc = "The directory where to place the processed files. If ommited the same DIR as 'file' is used";
		Option out = Option.builder(OPTION_OUT).longOpt(longOpt).argName(argName).desc(desc).hasArg().build();
		options.addOption(out);
		
		
		longOpt = "help";
		desc = "Help";
		Option help = Option.builder(OPTION_HELP).longOpt(longOpt).desc(desc).build();
		options.addOption(help);
		
	}

	/**
	 * Parses the command line arguments and adds them to the Arguments object if possible.
	 * @param args The command line arguments to be parsed.
	 * @return 
	 */
	public void parse(String [] args){
		//TODO: Remove the print to command line here.
		try {
			
			parse(options, args);
			
			if (cmd.hasOption(OPTION_HELP)) {
				// Help has the highest priority.
				displayHelp();
				
			}else {
				if (cmd.hasOption(OPTION_FILE)) {
					String file = cmd.getOptionValue(OPTION_FILE);
					arguments.setInputFile(file);
				}else {
					throw new  MissingArgumentException(currentOption);
				}
				
				if (cmd.hasOption(OPTION_LANGUAGE)) {
					Language language = Language.valueOf(cmd.getOptionValue(OPTION_LANGUAGE).toUpperCase());
					arguments.setLanguage(language);
				}else {
					throw new MissingArgumentException(currentOption);
				}
				
				if (cmd.hasOption(OPTION_OUT)) {
					String out = cmd.getOptionValue(OPTION_OUT);
					arguments.setOutputFolder(out);
				}else {
					File f = new File(arguments.getInputFile());
					arguments.setOutputFolder(f.getParentFile().getPath());
				}
				
				// Print the values to the console.
				System.out.println(arguments.toString());
				
			}
		}catch (ParseException e) {
			System.err.println(e.getMessage());;
		}
	}
	

	/**
	 * Procedure to print the help for the command line.
	 */
	private void displayHelp() {
		
		HelpFormatter formatter = new HelpFormatter();
		
		String cmdSyntax = "j2m";
		String header = "A very small tool for people to get things done fast! :)\n\n";
		String footer = "Enjoy";

		formatter.printHelp(cmdSyntax, header, options, footer, true);
	}

	
	/**
	 * @return the arguments
	 */
	public Arguments getArguments() {
		return arguments;
	}
	
	
	
}
