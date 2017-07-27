package com.al.j2m.template;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TemplateTypesTest {
	
	@Test
	public void url_JavaEnumerationType_ReturnsPathwithKeywordinMiddle() throws Exception {
		String expected = "templates/java/class.vm";
		assertEquals(expected, TemplateTypes.JAVA.url());
	}
	
}
