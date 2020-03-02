package fr.excilys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.excilys.dao.ComputerDAO;
import fr.excilys.model.Computer;

public final class ComputerDAOService {

	private ComputerDAO computerDAO = ComputerDAO.getInstance();
	private static volatile ComputerDAOService instance = null;

	private ComputerDAOService() {
		super();
	}

	public final static ComputerDAOService getInstance() {
		if (ComputerDAOService.instance == null) {
			synchronized (ComputerDAOService.class) {
				if (ComputerDAOService.instance == null) {
					ComputerDAOService.instance = new ComputerDAOService();
				}
			}
		}
		return ComputerDAOService.instance;
	}

	public int update(Computer computer) {
		Computer computerToUpdate = new Computer.Builder().build();
		int nbOfRowUpdatedInDB = 0;
		computerToUpdate = computerDAO.findByID(computer.getId()).get();
		if (computerToUpdate != null) {
			computerDAO.update(computer);
		}
		return nbOfRowUpdatedInDB;
	}

	public int add(Computer computer) {
		int nbOfRowAddedInDB = 0;
		nbOfRowAddedInDB = computerDAO.create(computer);
		return nbOfRowAddedInDB;
	}

	public int delete(Computer computer) {
		int nbOfRowDeletedInDB = 0;
		nbOfRowDeletedInDB = computerDAO.delete(computer.getId());
		return nbOfRowDeletedInDB;
	}

	//
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

	public int getNbRows() {
		int nbRow = -1;
		nbRow = computerDAO.getNbRow();
		return nbRow;
	}

}
