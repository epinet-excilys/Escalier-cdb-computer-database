package fr.excilys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.excilys.dao.CompanyDAO;
import fr.excilys.dao.ComputerDAO;
import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;


@Service
public final class CompanyService {

	private CompanyDAO companyDAO;
	
	@Autowired
	public CompanyService(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
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
