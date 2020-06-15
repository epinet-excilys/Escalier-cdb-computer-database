package fr.excilys.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertArrayEquals;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.hibernate.Hibernate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.excilys.config.AppConfiguration;
import fr.excilys.config.HibernateConfiguration;
import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class, HibernateConfiguration.class})
@Transactional
public class ComputerDAOTest {

	private final int INTENDED_USE_RETURN_VALUE = 1;
	private final int FAILED_USE_RETURN_VALUE = 0;
	private final int TAILLE_PAGE = 10;
	private final int TAILLE_BDD = 50;
	@Autowired
	private ComputerDAO computerDAO;
	
	
	
	@Test
	public void testgetNBRows() {
		assertTrue(computerDAO.getNbRow() == TAILLE_BDD);
	}

	@Test
	public void testAddComputerIntendedUse() {
		Company company = new Company.Builder().setIdBuild(1).build();
		Computer computer = new Computer.Builder().setNameBuild("Nom")
				.setIntroducedDateBuild(LocalDate.now().minusYears(5))
				.setDiscontinuedDateBuild(LocalDate.now().minusYears(1)).setIdCompagnyBuild(company).build();

		try {
			assertTrue(computerDAO.create(computer) == INTENDED_USE_RETURN_VALUE);
		} catch (NoSuchElementException e1) {
			fail("Ajout n'a pas marcher à la BDD est impossible" + e1.getMessage());
		}

	}

	@Test
	public void testAddComputerIntendedUseNullComp() {
		Company company = new Company.Builder().setIdBuild(1).build();
		Computer computer = new Computer.Builder().setNameBuild("Nom")
				.setIntroducedDateBuild(LocalDate.now().minusYears(5))
				.setDiscontinuedDateBuild(LocalDate.now().minusYears(1)).build();

		try {
			assertTrue(computerDAO.create(computer) == INTENDED_USE_RETURN_VALUE);
		} catch (NoSuchElementException e1) {
			fail("Ajout n'a pas marcher à la BDD est impossible" + e1.getMessage());
		}

	}

	@Test(expected = DatabaseDAOException.class)
	public void testAddComputerNull() {
		Computer computer = null;
		computerDAO.create(computer);
	}

	@Test
	public void testModifComputerIntendedUse() {
		Company company = new Company.Builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.Builder().setIdBuild(1).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroducedDateBuild(LocalDate.now().minusYears(5))
				.setDiscontinuedDateBuild(LocalDate.now().minusYears(1)).setIdCompagnyBuild(company).build();
		assertTrue(computerDAO.update(computer) == INTENDED_USE_RETURN_VALUE);
	}
	
	@Test
	public void testModifComputerIntendedUseCompanyNull() {
		Computer computer = new Computer.Builder().setIdBuild(1).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroducedDateBuild(LocalDate.now().minusYears(5))
				.setDiscontinuedDateBuild(LocalDate.now().minusYears(1)).build();

		assertTrue(computerDAO.update(computer) == INTENDED_USE_RETURN_VALUE);
	}

	@Test
	public void testModifComputerNull() {
		Computer computer = new Computer.Builder().build();
		assertEquals(computerDAO.update(computer),TAILLE_BDD+1);
	}

	@Test
	public void testModifComputerGreaterId() {
		Company company = new Company.Builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.Builder().setIdBuild(70).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroducedDateBuild(LocalDate.now().minusYears(5))
				.setDiscontinuedDateBuild(LocalDate.now().minusYears(1)).setIdCompagnyBuild(company).build();
		assertEquals(computerDAO.update(computer),TAILLE_BDD+1);

	}

	@Test
	public void testDeleteComputerIntendedUse() {
		Company company = new Company.Builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.Builder().setIdBuild(1).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroducedDateBuild(LocalDate.now().minusYears(5))
				.setDiscontinuedDateBuild(LocalDate.now().minusYears(1)).setIdCompagnyBuild(company).build();

		
		assertEquals(computerDAO.delete(computer.getId()),INTENDED_USE_RETURN_VALUE);
	}

	@Test
	public void testDeleteComputerGreaterId() {
		Company company = new Company.Builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.Builder().setIdBuild(70).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroducedDateBuild(LocalDate.now().minusYears(5))
				.setDiscontinuedDateBuild(LocalDate.now().minusYears(1)).setIdCompagnyBuild(company).build();

		assertEquals(computerDAO.delete(computer.getId()),FAILED_USE_RETURN_VALUE);
	}

	@Test
	public void testDeleteComputerNullId() {
		Computer computer = new Computer.Builder().build();

		assertEquals(computerDAO.delete(computer.getId()),FAILED_USE_RETURN_VALUE);
	}

	@Test
	public void testFindComputerIntendeduse() {
		Company company = new Company.Builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.Builder().setIdBuild(1).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroducedDateBuild(null).setDiscontinuedDateBuild(null).setIdCompagnyBuild(company).build();

		assertTrue(computerDAO.findByID(1).get(0).equals(computer));
	}

	@Test
	public void testFindIdEqualZero() {
		assertTrue(computerDAO.findByID(0).isEmpty());
	}

	@Test
	public void testFindAllPaginateCorrectSize() {
		List<Computer> computers = new ArrayList<>();
		computers = computerDAO.findAllPaginate(0, TAILLE_PAGE);

		assertTrue(computers.size() == TAILLE_PAGE);
	}

	@Test
	public void testFindAllPaginateWrongEntry() {
		List<Computer> computers = new ArrayList<>();
		computers = computerDAO.findAllPaginate(-5, TAILLE_PAGE);

		assertTrue(computers.size() == TAILLE_PAGE);
	}

	@Test
	public void testFindAllPaginateAllComputersListAreEquals() {
		List<Computer> computersBDD = computerDAO.findAllPaginate(0, TAILLE_PAGE);
		List<Computer> computersAdd = getTheFirst10Computers();

		assertArrayEquals(computersBDD.toArray(), computersAdd.toArray());
	}

	private List<Computer> getTheFirst10Computers() {
		List<Computer> computerList = new ArrayList<>();
		Company company1 = new Company.Builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Company company2 = new Company.Builder().setIdBuild(2).setNameBuild("Thinking Machines").build();
		Computer computer1 = new Computer.Builder().setIdBuild(1).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroducedDateBuild(null).setDiscontinuedDateBuild(null).setIdCompagnyBuild(company1).build();
		computerList.add(computer1);
		Computer computer2 = new Computer.Builder().setIdBuild(2).setNameBuild("CM-2a").setIntroducedDateBuild(null)
				.setDiscontinuedDateBuild(null).setIdCompagnyBuild(company2).build();
		computerList.add(computer2);
		Computer computer3 = new Computer.Builder().setIdBuild(3).setNameBuild("CM-200").setIntroducedDateBuild(null)
				.setDiscontinuedDateBuild(null).setIdCompagnyBuild(company2).build();
		computerList.add(computer3);
		Computer computer4 = new Computer.Builder().setIdBuild(4).setNameBuild("CM-5e").setIntroducedDateBuild(null)
				.setDiscontinuedDateBuild(null).setIdCompagnyBuild(company2).build();
		computerList.add(computer4);
		Computer computer5 = new Computer.Builder().setIdBuild(5).setNameBuild("CM-5")
				.setIntroducedDateBuild(fromStringToLocalDate("1991-01-01"))
				.setDiscontinuedDateBuild(null).setIdCompagnyBuild(company2).build();
		computerList.add(computer5);
		Computer computer6 = new Computer.Builder().setIdBuild(6).setNameBuild("MacBook Pro")
				.setIntroducedDateBuild(fromStringToLocalDate("2006-01-10"))
				.setDiscontinuedDateBuild(null).setIdCompagnyBuild(company1).build();
		computerList.add(computer6);
		Computer computer7 = new Computer.Builder().setIdBuild(7).setNameBuild("Apple IIe").setIntroducedDateBuild(null)
				.setDiscontinuedDateBuild(null).build();
		computerList.add(computer7);
		Computer computer8 = new Computer.Builder().setIdBuild(8).setNameBuild("Apple IIc").setIntroducedDateBuild(null)
				.setDiscontinuedDateBuild(null).build();
		computerList.add(computer8);
		Computer computer9 = new Computer.Builder().setIdBuild(9).setNameBuild("Apple IIGS")
				.setIntroducedDateBuild(null).setDiscontinuedDateBuild(null).build();
		computerList.add(computer9);
		Computer computer10 = new Computer.Builder().setIdBuild(10).setNameBuild("Apple IIc Plus")
				.setIntroducedDateBuild(null).setDiscontinuedDateBuild(null).build();
		computerList.add(computer10);

		return computerList;
	}
	
	private LocalDate fromStringToLocalDate(String dateInString) throws DateTimeException{

		if ((dateInString != null) && !dateInString.isEmpty()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate dateTime;
			dateTime = LocalDate.parse(dateInString, formatter);
			return dateTime;
		} else {
			return null;
		}
	}
}