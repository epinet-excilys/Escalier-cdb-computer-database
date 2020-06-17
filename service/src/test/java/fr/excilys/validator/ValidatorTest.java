package fr.excilys.validator;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.hibernate.Hibernate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fr.excilys.config.AppConfiguration;
import fr.excilys.config.HibernateConfiguration;
import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.exception.ValidatorException;
import fr.excilys.model.Computer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class,HibernateConfiguration.class})
@WebAppConfiguration
public class ValidatorTest {
	private static LocalDate localDate = LocalDate.now().minusYears(2);
	private LocalDate localDate2 = LocalDate.now().minusYears(1);
	private LocalDate localDateNull = null;
	private LocalDate localDateOld = LocalDate.of(1777, 01, 01);
	@Autowired
	private Validator validator;
	
	

	@Test
	public void testisDateOKIntendedUseBothOk() {
		assertTrue(validator.isDateOk(localDate, localDate2));
	}
	
	@Test
	public void testisDateOKIntendedUseIntroducedNull() {
		assertTrue(validator.isDateOk(localDateNull, localDate));
	}
	
	@Test
	public void testisDateOKIntendedUseDiscontinuedNull() {
		assertTrue(validator.isDateOk(localDate, localDateNull));
	}
	
	@Test
	public void testisDateOKIntendedUseBothNull() {
		assertTrue(validator.isDateOk(localDateNull, localDateNull));
	}
	

	@Test(expected = ValidatorException.class)
	public void testisDateOKWrongUseInverseDate() {
		validator.isDateOk(localDate2, localDate);
	}
	
	@Test(expected = ValidatorException.class)
	public void testisDateOKWrongUseOldDiscontinuedDate() {
		validator.isDateOk(localDate2, localDate);
	}
	
	@Test(expected = ValidatorException.class)
	public void testisDateOKWrongUseOldIntroducedDate() {
		validator.isDateOk(localDateOld, localDate);
	}
	

	@Test(expected = ValidatorException.class)
	public void testisDateOKWrongUseOldIntroducedDateAndNull() {
		validator.isDateOk(localDateOld, localDateNull);
	}
	
	@Test
	public void testNameisOKIntendedUse() {
		Computer computer = new Computer.Builder().setNameBuild("test").build();
		assertTrue(validator.isNameOk(computer.getName()));
	}
	
	@Test(expected = ValidatorException.class)
	public void testNameisOKWrongUseBlank() {
		Computer computer = new Computer.Builder().setNameBuild("").build();
		validator.isNameOk(computer.getName());
	}
}
