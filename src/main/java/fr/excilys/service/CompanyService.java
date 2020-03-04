package fr.excilys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.excilys.dao.CompanyDAO;
import fr.excilys.dao.ComputerDAO;
import fr.excilys.model.Company;

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

	public Optional<Company> findByID(int ID) {
		Company company = new Company.Builder().build();
		company = companyDAO.findByID(ID).get();
		return Optional.ofNullable(company);
	}

	public List<Company> getAllCompany() {
		List<Company> listCompany = new ArrayList<>();
		listCompany = companyDAO.findAll();
		return listCompany;
	}

	public int getNbRows() {
		int nbRow = -1;
		nbRow = companyDAO.getNbRow();
		return nbRow;
	}

}
