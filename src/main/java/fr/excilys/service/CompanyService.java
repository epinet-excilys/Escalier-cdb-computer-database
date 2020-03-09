package fr.excilys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.excilys.dao.CompanyDAO;
import fr.excilys.dao.ComputerDAO;
import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;

public final class CompanyService {

	private CompanyDAO companyDAO = CompanyDAO.getInstance();
	private static volatile CompanyService instance = null;

	private CompanyService() {
		super();
	}

	public final static CompanyService getInstance() {
		if (CompanyService.instance == null) {
			synchronized (CompanyService.class) {
				if (CompanyService.instance == null) {
					CompanyService.instance = new CompanyService();
				}
			}
		}
		return CompanyService.instance;
	}

	public Optional<Company> findByID(int ID) throws DatabaseDAOException{
		Optional<Company> optionalCompany = Optional.empty();		
		optionalCompany = companyDAO.findByID(ID);
		
		return optionalCompany;
	}

	public List<Company> getAllCompany() throws DatabaseDAOException{
		List<Company> listCompany = new ArrayList<>();
		listCompany = companyDAO.findAll();
		
		return listCompany;
	}

	public int getNbRows() throws DatabaseDAOException{
		return  companyDAO.getNbRow();
	}

}
