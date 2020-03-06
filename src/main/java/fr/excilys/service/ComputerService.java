package fr.excilys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.excilys.dao.ComputerDAO;
import fr.excilys.model.Computer;

public final class ComputerService {

	private ComputerDAO computerDAO = ComputerDAO.getInstance();
	private static volatile ComputerService instance = null;

	private ComputerService() {
		super();
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

	public int update(Computer computer) {
		int nbOfRowUpdatedInDB = 0;
		nbOfRowUpdatedInDB = computerDAO.update(computer);
		return nbOfRowUpdatedInDB;
	}

	public int add(Computer computer) {
		int nbOfRowAddedInDB = 0;
		nbOfRowAddedInDB = computerDAO.create(computer);
		return nbOfRowAddedInDB;
	}

	public int delete(int iD) {
		int nbOfRowDeletedInDB = 0;
		nbOfRowDeletedInDB = computerDAO.delete(iD);
		return nbOfRowDeletedInDB;
	}

	public Optional<Computer> findByID(int ID) {
		Optional<Computer> computer = Optional.empty();
		computer = computerDAO.findByID(ID);
		return computer;
	}

	public List<Computer> getAllComput() {
		List<Computer> listComputer = new ArrayList<>();
		listComputer = computerDAO.findAll();
		return listComputer;

	}

	public List<Computer> getAllPaginateComput(int ligneDebutOffSet, int taillePage) {
		List<Computer> listComputer = new ArrayList<>();
		listComputer = computerDAO.findAllPaginate(ligneDebutOffSet, taillePage);
		return listComputer;

	}
	
	public List<Computer> findAllPaginateSearchLike(String search, int ligneDebutOffSet, int taillePage) {
		List<Computer> listComputer = new ArrayList<>();
		listComputer = computerDAO.findAllPaginateSearchLike(search,ligneDebutOffSet, taillePage);
		return listComputer;
	}
	
	public List<Computer> findAllPaginateAlphabeticOrder(int ligneDebutOffSet, int taillePage) {
		List<Computer> listComputer = new ArrayList<>();
		listComputer = computerDAO.findAllPaginateAlphabeticOrder(ligneDebutOffSet, taillePage);
		return listComputer;
	}

	public int getNbRows() {
		int nbRow = -1;
		nbRow = computerDAO.getNbRow();
		return nbRow;
	}
	
	public int getNbRowsSearch(String search) {
		int nbRow = -1;
		nbRow = computerDAO.getNbRowSearch(search);
		return nbRow;
	}

}
