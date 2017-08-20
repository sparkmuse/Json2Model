package com.al.j2m.util;

import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PropertiesUtilTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void getProperties_Default_ReturnPropertiesFile() throws Exception {
		Properties properties = PropertiesUtil.getProperties();
		assertTrue(properties.keySet().size() != 0);
	}
	
	@Test
	public void propertiesUtil_DefaultContructor_ThrowsIllegalStateException() throws Exception {
		exception.expect(IllegalStateException.class);
		exception.expectMessage("Utility class");
		
		@SuppressWarnings("unused")
		PropertiesUtil propertiesUtil = new PropertiesUtil();
	}
}


