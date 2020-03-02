package fr.excilys.mapper;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ComputerMapperTest {

	ComputerMapper instance;

	@Before
	public void setUp() throws Exception {
		instance = ComputerMapper.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		instance = null;
	}

	@Test
	public void testFromStringToLocalDateWithEmptyString() {

		try {
			assertEquals(null, instance.fromStringToLocalDate(""));
		} catch (DateTimeParseException e) {
			fail("le test ne devait pas echouer");
		}
	}

	@Test
	public void testFromStringToLocalDateWithString() {

		try {
			instance.fromStringToLocalDate("gsoijngiqhggbihfb");
			fail("le parsing devait echouer");
		} catch (DateTimeParseException e) {
			assertEquals(DateTimeParseException.class,e.getClass());
		}
	}

	@Test
	public void testFromStringToLocalDateWithDateString() {
		try {
			assertEquals(LocalDate.of(1999, Month.DECEMBER, 1), instance.fromStringToLocalDate("1999-12-01"));
		} catch (DateTimeParseException e) {
			fail("le Parsing devait se passer correctement");
		}
	}
	
	@Test
	public void testFromStringToLocalDateWithNull() {
		try {
			assertEquals(null, instance.fromStringToLocalDate(""));
		} catch (NullPointerException e) {
			fail("le teste ne devait pas echouer");
		}
	}
	

}
