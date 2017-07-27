package com.al.j2m.cmld;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.al.j2m.cmdl.ArgumentParser;
import com.al.j2m.cmdl.Arguments;

public class ArgumentParserTest {
	
	private static final String[] ARGS_NO_OUTPUT = {"-f", "/folder/file", "-lang", "java"};
	
	private static final String[] ARGS_OUTPUT = {"-f", "/folder/file", "-lang", "java", "-o", "/otherFolder"};
	
	private static final String[] ARGS_HELP = {"-h"};
	
	private static final String[] ARGS_EMPTY = {};
	
	private ArgumentParser parser;
	
	@Before
	public void setUp() throws Exception {
		parser = new ArgumentParser();
	}

	@Test
	public void parse_WithArgumentsEmpty_ReturnsArgumentHelp() throws Exception {
		Arguments expected = Arguments.HELP;
		assertEquals(expected, parser.parse(ARGS_EMPTY));
	}
	
	@Test
	public void parse_WithArgumentHelp_ReturnsArgumentHelp() throws Exception {
		Arguments expected = Arguments.HELP;
		assertEquals(expected, parser.parse(ARGS_HELP));
	}

	@Test
	public void parse_WithArgumentsWithNoOutputFolder_ReturnsArgumentsWithSourceFolder() throws Exception {
		String expected = "/folder";
		String actual = parser.parse(ARGS_NO_OUTPUT).getOutputFolder().replaceAll("\\\\", "/");
		assertEquals(expected, actual);
	}
	
	
	@Test
	public void parse_WithArgumentsWithOutputFolder_ReturnsArgumentsWithOutputFolder() throws Exception {
		String expected = "/otherFolder";
		String actual = parser.parse(ARGS_OUTPUT).getOutputFolder().replaceAll("\\\\", "/");
		assertEquals(expected, actual);
	}
	
}
