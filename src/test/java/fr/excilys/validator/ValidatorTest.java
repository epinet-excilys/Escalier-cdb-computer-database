package fr.excilys.validator;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ValidatorTest {
	private static LocalDate localDate = LocalDate.now().minusYears(2);
	private LocalDate localDate2 = LocalDate.now().minusYears(1);
	private LocalDate localDateNull = null;
	private LocalDate localDateOld = LocalDate.of(1777, 01, 01);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testisDateOKIntendedUseBothOk() {
		assertTrue(Validator.isDateOk(localDate, localDate2));
	}
	
	@Test
	public void testisDateOKIntendedUseIntroducedNull() {
		assertTrue(Validator.isDateOk(localDateNull, localDate));
	}
	
	@Test
	public void testisDateOKIntendedUseDiscontinuedNull() {
		assertTrue(Validator.isDateOk(localDate, localDateNull));
	}
	
	@Test
	public void testisDateOKIntendedUseBothNull() {
		assertTrue(Validator.isDateOk(localDateNull, localDateNull));
	}
	

	@Test
	public void testisDateOKWrongUseInverseDate() {
		assertFalse(Validator.isDateOk(localDate2, localDate));
	}
	
	@Test
	public void testisDateOKWrongUseOldDiscontinuedDate() {
		assertFalse(Validator.isDateOk(localDate2, localDate));
	}
	
	@Test
	public void testisDateOKWrongUseOldIntroducedDate() {
		assertFalse(Validator.isDateOk(localDateOld, localDate));
	}
	

	@Test
	public void testisDateOKWrongUseOldIntroducedDateAndNull() {
		assertFalse(Validator.isDateOk(localDateOld, localDateNull));
	}
	

}
