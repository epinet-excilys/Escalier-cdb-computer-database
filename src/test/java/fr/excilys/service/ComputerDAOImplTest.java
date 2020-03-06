package fr.excilys.service;

import static org.mockito.Mockito.*;
import org.junit.Before;
import fr.excilys.dao.ComputerDAO;

public class ComputerDAOImplTest {
	ComputerDAO computerDao;
	

	@Before
	public void setUp() throws Exception {
		computerDao = mock(ComputerDAO.class);
	}
}
