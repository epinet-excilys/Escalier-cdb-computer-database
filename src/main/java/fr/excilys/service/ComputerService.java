package fr.excilys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.excilys.dao.ComputerDAO;
import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.model.Computer;

public final class ComputerService {

	private ComputerDAO computerDAO = ComputerDAO.getInstance();
	private static volatile ComputerService instance = null;

	private ComputerService() {
	}

	public final static ComputerService getInstance() {
		if (ComputerService.instance == null) {
			synchronized (ComputerService.class) {
				if (ComputerService.instance == null) {
					ComputerService.instance = new ComputerService();
				}
			}
		}
		return ComputerService.instance;
	}

	public void update(Computer computer) {
		try {
		computerDAO.update(computer);
		}catch(DatabaseDAOException databaseDAOException) {
			//TODO GERER LE RENVOIE UTILISATEUR
		}
	}

	public void add(Computer computer) {
		try {
		computerDAO.create(computer);
		}catch(DatabaseDAOException databaseDAOException) {
			//TODO GERER LE RENVOIE UTILISATEUR
		}
	}

	public void delete(int iD) {
		try {
		computerDAO.delete(iD);
		}catch(DatabaseDAOException databaseDAOException) {
			//TODO GERER LE RENVOIE UTILISATEUR
		}
	}

	public Optional<Computer> findByID(int ID) {
		Optional<Computer> computer = Optional.empty();
		
		computer = computerDAO.findByID(ID);
		//TODO GERER LE RENVOIE UTILISATEUR
		return computer;
	}

	public List<Computer> getAllComput() {
		List<Computer> listComputer = new ArrayList<>();
		listComputer = computerDAO.findAll();
		//TODO GERER LE RENVOIE UTILISATEUR
		return listComputer;

	}

	public List<Computer> getAllPaginateComput(int ligneDebutOffSet, int taillePage) {
		List<Computer> listComputer = new ArrayList<>();
		listComputer = computerDAO.findAllPaginate(ligneDebutOffSet, taillePage);
		//TODO GERER LE RENVOIE UTILISATEUR
		return listComputer;

	}
	
	public List<Computer> findAllPaginateSearchLike(String search, int ligneDebutOffSet, int taillePage) {
		List<Computer> listComputer = new ArrayList<>();
		listComputer = computerDAO.findAllPaginateSearchLike(search,ligneDebutOffSet, taillePage);
		//TODO GERER LE RENVOIE UTILISATEUR
		return listComputer;
	}
	
	public List<Computer> findAllPaginateAlphabeticOrder(int ligneDebutOffSet, int taillePage) {
		List<Computer> listComputer = new ArrayList<>();
		listComputer = computerDAO.findAllPaginateAlphabeticOrder(ligneDebutOffSet, taillePage);
		//TODO GERER LE RENVOIE UTILISATEUR
		return listComputer;
	}

	public int getNbRows() {
		//TODO
		return computerDAO.getNbRow();
	}
	
	public int getNbRowsSearch(String search) {
		//TODO
		return computerDAO.getNbRowSearch(search);
	}

}
