package fr.excilys.validator;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.excilys.config.AppConfiguration;
import fr.excilys.model.Computer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class})
public class ValidatorTest {
	private static LocalDate localDate = LocalDate.now().minusYears(2);
	private LocalDate localDate2 = LocalDate.now().minusYears(1);
	private LocalDate localDateNull = null;
	private LocalDate localDateOld = LocalDate.of(1777, 01, 01);
	private Validator validator;
	
	
	public ValidatorTest(Validator validator) {
		this.validator = validator;
	}
	
	
//
//	@Test
//	public void testisDateOKIntendedUseBothOk() {
//		assertTrue(validator.isDateOk(localDate, localDate2));
//	}
//	
//	@Test
//	public void testisDateOKIntendedUseIntroducedNull() {
//		assertTrue(Validator.isDateOk(localDateNull, localDate));
//	}
//	
//	@Test
//	public void testisDateOKIntendedUseDiscontinuedNull() {
//		assertTrue(Validator.isDateOk(localDate, localDateNull));
//	}
//	
//	@Test
//	public void testisDateOKIntendedUseBothNull() {
//		assertTrue(Validator.isDateOk(localDateNull, localDateNull));
//	}
//	
//
//	@Test
//	public void testisDateOKWrongUseInverseDate() {
//		assertFalse(Validator.isDateOk(localDate2, localDate));
//	}
//	
//	@Test
//	public void testisDateOKWrongUseOldDiscontinuedDate() {
//		assertFalse(Validator.isDateOk(localDate2, localDate));
//	}
//	
//	@Test
//	public void testisDateOKWrongUseOldIntroducedDate() {
//		assertFalse(Validator.isDateOk(localDateOld, localDate));
//	}
//	
//
//	@Test
//	public void testisDateOKWrongUseOldIntroducedDateAndNull() {
//		assertFalse(Validator.isDateOk(localDateOld, localDateNull));
//	}
//	
//	@Test
//	public void testNameisOKIntendedUse() {
//		Computer computer = new Computer.Builder().setNameBuild("test").build();
//		assertTrue(Validator.isNameOk(computer));
//	}
//	
//	@Test
//	public void testNameisOKWrongUseBlank() {
//		Computer computer = new Computer.Builder().setNameBuild("").build();
//		assertFalse(Validator.isNameOk(computer));
//	}
}
