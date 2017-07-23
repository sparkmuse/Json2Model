package com.al.j2m.parser;

import static org.junit.Assert.*;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ParserTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private static final String TEMPLATE_PATH = "templates/test.vm";
	private Parser parser;

	@Before
	public void setUp() throws Exception {
		this.parser = new Parser(TEMPLATE_PATH);
	}

	@Test
	public void parser_WithContext_ReturnsTemplateAndContextMerged() throws Exception {
		String expected = "Hello Alfredo";
		parser.put("person", "Alfredo");
		String actual = parser.parse();
		assertEquals(expected, actual);
	}
	
	@Test
	public void parser_WithNoContext_ReturnsTemplateAndVariableName() throws Exception {
		String expected = "Hello $person";
		String actual = parser.parse();
		assertEquals(expected, actual);
	}
	
	@Test
	public void parser_WithInvalid_ReturnsResourceNotFoundException() throws Exception {
		String expected = "SEVERE: ResourceManager : unable to find resource 'fakeTemplatePath' in any resource loader.";
		exception.expect(ResourceNotFoundException.class);
		Parser parser = new Parser("fakeTemplatePath");
		exception.expectMessage(expected);
	}
}
