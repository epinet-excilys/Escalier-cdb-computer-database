package fr.excilys.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.excilys.config.AppConfiguration;
import fr.excilys.exception.DatabaseManipulationException;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class})
public class ComputerDAOTest {

	private final int INTENDED_USE_RETURN_VALUE = 1;
	private final int TAILLE_PAGE = 10;
	private ComputerDAO computerDAO;
	private ComputerMapper computerMapper;
	
	@Autowired
	public void setComputerDAO(ComputerDAO computerDAO, ComputerMapper computerMapper) {
		this.computerDAO = computerDAO;
		this.computerMapper = computerMapper; 
	}
	
	@Test
	public void testgetNBRows() {
		assertTrue(computerDAO.getNbRow() == 50);
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

	@Test(expected = DatabaseManipulationException.class)
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

	@Test(expected = DatabaseManipulationException.class)
	public void testModifComputerNull() {
		Computer computer = new Computer.Builder().build();
		computerDAO.update(computer);
	}

	@Test(expected = DatabaseManipulationException.class)
	public void testModifComputerGreaterId() {
		Company company = new Company.Builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.Builder().setIdBuild(70).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroducedDateBuild(LocalDate.now().minusYears(5))
				.setDiscontinuedDateBuild(LocalDate.now().minusYears(1)).setIdCompagnyBuild(company).build();
		computerDAO.update(computer);

	}

	@Test
	public void testDeleteComputerIntendedUse() {
		Company company = new Company.Builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.Builder().setIdBuild(1).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroducedDateBuild(LocalDate.now().minusYears(5))
				.setDiscontinuedDateBuild(LocalDate.now().minusYears(1)).setIdCompagnyBuild(company).build();

		assertTrue(computerDAO.update(computer) == INTENDED_USE_RETURN_VALUE);
	}

	@Test(expected = DatabaseManipulationException.class)
	public void testDeleteComputerGreaterId() {
		Company company = new Company.Builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.Builder().setIdBuild(70).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroducedDateBuild(LocalDate.now().minusYears(5))
				.setDiscontinuedDateBuild(LocalDate.now().minusYears(1)).setIdCompagnyBuild(company).build();

		computerDAO.update(computer);
	}

	@Test(expected = DatabaseManipulationException.class)
	public void testDeleteComputerNullId() {
		Computer computer = new Computer.Builder().build();

		computerDAO.update(computer);
	}

	@Test
	public void testFindComputerIntendeduse() {
		Company company = new Company.Builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Computer computer = new Computer.Builder().setIdBuild(1).setNameBuild("MacBook Pro 15.4 inch")
				.setIntroducedDateBuild(null).setDiscontinuedDateBuild(null).setIdCompagnyBuild(company).build();

		assertTrue(computerDAO.findByID(1).get().equals(computer));
	}

	@Test
	public void testFindIdEqualZero() {
		assertFalse(computerDAO.findByID(0).isPresent());
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

		assertEquals(computersBDD, computersAdd);
	}

	private List<Computer> getTheFirst10Computers() {
		List<Computer> computerList = new ArrayList<>();
		Company company1 = new Company.Builder().setIdBuild(1).setNameBuild("Apple Inc.").build();
		Company company2 = new Company.Builder().setIdBuild(2).setNameBuild("Thinking Machines").build();
		Company companyNull = new Company.Builder().build();
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
				.setIntroducedDateBuild(computerMapper.fromStringToLocalDate("1991-01-01"))
				.setDiscontinuedDateBuild(null).setIdCompagnyBuild(company2).build();
		computerList.add(computer5);
		Computer computer6 = new Computer.Builder().setIdBuild(6).setNameBuild("MacBook Pro")
				.setIntroducedDateBuild(computerMapper.fromStringToLocalDate("2006-01-10"))
				.setDiscontinuedDateBuild(null).setIdCompagnyBuild(company1).build();
		computerList.add(computer6);
		Computer computer7 = new Computer.Builder().setIdBuild(7).setNameBuild("Apple IIe").setIntroducedDateBuild(null)
				.setDiscontinuedDateBuild(null).setIdCompagnyBuild(companyNull).build();
		computerList.add(computer7);
		Computer computer8 = new Computer.Builder().setIdBuild(8).setNameBuild("Apple IIc").setIntroducedDateBuild(null)
				.setDiscontinuedDateBuild(null).setIdCompagnyBuild(companyNull).build();
		computerList.add(computer8);
		Computer computer9 = new Computer.Builder().setIdBuild(9).setNameBuild("Apple IIGS")
				.setIntroducedDateBuild(null).setDiscontinuedDateBuild(null).setIdCompagnyBuild(companyNull).build();
		computerList.add(computer9);
		Computer computer10 = new Computer.Builder().setIdBuild(10).setNameBuild("Apple IIc Plus")
				.setIntroducedDateBuild(null).setDiscontinuedDateBuild(null).setIdCompagnyBuild(companyNull).build();
		computerList.add(computer10);

		return computerList;
	}
}
