package com.al.j2m.util;

import static org.junit.Assert.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author alfredo
 *
 */
public class NameUtilsTest {

	@Test
	public void getCommonBetween_WithFirstNameAndLastName_ReturnName() throws Exception {
		assertEquals("stName", NameUtils.getCommonBetween("firstName", "lastName"));
	}

	@Test
	public void getCommonBetween_WithOrangeAndPotato_ReturnsEmptyString() throws Exception {
		assertEquals(StringUtils.EMPTY, NameUtils.getCommonBetween("orange", "potato"));
	}

	@Test
	public void getCommonBetween_WithDateFinalAndDateDate_ReturnsDate() throws Exception {
		assertEquals("date", NameUtils.getCommonBetween("dateFinal", "dateDate"));
	}

	@Test
	public void inferName_WithfirstName_ReturnsName() throws Exception {
		assertEquals("Name", NameUtils.inferName("firstName"));
	}

	@Test
	public void inferName_WithFirstName_ReturnsName() throws Exception {
		assertEquals("Name", NameUtils.inferName("FirstName"));
	}
	
	@Test
	public void inferName_WithNoCapitals_ReturnsSameString() throws Exception {
		assertEquals("name", NameUtils.inferName("name"));
	}
}
