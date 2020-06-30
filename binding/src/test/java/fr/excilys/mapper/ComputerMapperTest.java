package fr.excilys.mapper;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fr.excilys.config.AppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class})
@WebAppConfiguration
public class ComputerMapperTest {

	private ComputerMapper computerMapper;

	
	@Autowired
	public void setComputerMapper(ComputerMapper computerMapper) {
		this.computerMapper = computerMapper;
	}

	@Test
	public void testFromStringToLocalDateWithEmptyString() {

		try {
			assertEquals(null, computerMapper.fromStringToLocalDate(""));
		} catch (DateTimeParseException e) {
			fail("le test ne devait pas echouer");
		}
	}

	@Test
	public void testFromStringToLocalDateWithString() {

		try {
			computerMapper.fromStringToLocalDate("gsoijngiqhggbihfb");
			fail("le parsing devait echouer");
		} catch (DateTimeParseException e) {
			assertEquals(DateTimeParseException.class,e.getClass());
		}
	}

	@Test
	public void testFromStringToLocalDateWithDateString() {
		try {
			assertEquals(LocalDate.of(1999, Month.DECEMBER, 1), computerMapper.fromStringToLocalDate("1999-12-01"));
		} catch (DateTimeParseException e) {
			fail("le Parsing devait se passer correctement");
		}
	}
	
	@Test
	public void testFromStringToLocalDateWithNull() {
		try {
			assertEquals(null, computerMapper.fromStringToLocalDate(""));
		} catch (NullPointerException e) {
			fail("le teste ne devait pas echouer");
		}
	}


	

}
