package com.al.j2m.template;

import static org.junit.Assert.*;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.al.j2m.template.TemplateEngine;

public class TemplateEngineTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private static final String TEMPLATE_PATH = "templates/test.vm";
	private TemplateEngine parser;

	@Before
	public void setUp() throws Exception {
		this.parser = new TemplateEngine(TEMPLATE_PATH);
	}

	@Test
	public void parser_WithContext_ReturnsTemplateAndContextMerged() throws Exception {
		String expected = "Hello Alfredo";
		parser.put("person", "Alfredo");
		String actual = parser.merge();
		assertEquals(expected, actual);
	}
	
	@Test
	public void parser_WithNoContext_ReturnsTemplateAndVariableName() throws Exception {
		String expected = "Hello $person";
		String actual = parser.merge();
		assertEquals(expected, actual);
	}
	
	@Test
	public void parser_WithInvalid_ReturnsResourceNotFoundException() throws Exception {
		String expected = "SEVERE: ResourceManager : unable to find resource 'fakeTemplatePath' in any resource loader.";
		exception.expect(ResourceNotFoundException.class);
		TemplateEngine parser = new TemplateEngine("fakeTemplatePath");
		exception.expectMessage(expected);
	}
}
