package fr.excilys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.excilys.dao.ComputerDAO;
import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.model.Computer;

@Service
public final class ComputerService {

	private ComputerDAO computerDAO;

	@Autowired
	public ComputerService(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}

	public void update(Computer computer) throws DatabaseDAOException {
		computerDAO.update(computer);
	}

	public void add(Computer computer) throws DatabaseDAOException{
		computerDAO.create(computer);
	}

	public void delete(int iD) throws DatabaseDAOException{
		computerDAO.delete(iD);
	}

	public Optional<Computer> findByID(int ID) throws DatabaseDAOException {
		Optional<Computer> Optionalcomputer = Optional.empty();		
		Optionalcomputer = computerDAO.findByID(ID);
		
		return Optionalcomputer;
	}

	public List<Computer> getAllComput() throws DatabaseDAOException{
		List<Computer> listComputer = new ArrayList<>();
		listComputer = computerDAO.findAll();

		return listComputer;

	}

	public List<Computer> getAllPaginateComput(int ligneDebutOffSet, int taillePage) throws DatabaseDAOException{
		List<Computer> listComputer = new ArrayList<>();
		listComputer = computerDAO.findAllPaginate(ligneDebutOffSet, taillePage);

		return listComputer;

	}
	
	public List<Computer> findAllPaginateSearchLike(String search, int ligneDebutOffSet, int taillePage) throws DatabaseDAOException{
		List<Computer> listComputer = new ArrayList<>();
		listComputer = computerDAO.findAllPaginateSearchLike(search,ligneDebutOffSet, taillePage);

		return listComputer;
	}
	
	public List<Computer> findAllPaginateAlphabeticOrder(int ligneDebutOffSet, int taillePage) throws DatabaseDAOException{
		List<Computer> listComputer = new ArrayList<>();
		listComputer = computerDAO.findAllPaginateAlphabeticOrder(ligneDebutOffSet, taillePage);

		return listComputer;
	}

	public int getNbRows() throws DatabaseDAOException{

		return computerDAO.getNbRow();
	}
	
	public int getNbRowsSearch(String search) throws DatabaseDAOException{

		return computerDAO.getNbRowSearch(search);
	}

}
