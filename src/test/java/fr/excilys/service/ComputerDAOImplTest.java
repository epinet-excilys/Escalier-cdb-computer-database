package fr.excilys.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.excilys.dao.ComputerDAO;



public class ComputerDAOImplTest {
	ComputerDAO computerDao;
	

	@Before
	public void setUp() throws Exception {
		computerDao = mock(ComputerDAO.class);
	}

	@After
	public void tearDown() throws Exception {
	}

//	@Test
//	public void test() {
//		
//		
//		
//		fail("Not yet implemented");
//	}

}
