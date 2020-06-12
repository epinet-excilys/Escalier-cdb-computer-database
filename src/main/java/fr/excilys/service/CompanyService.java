package fr.excilys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.excilys.dao.CompanyDAO;
import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.model.Company;


@Service
public class CompanyService {

	private CompanyDAO companyDAO;
	
	public CompanyService(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	@Transactional
	public Optional<Company> findByID(int ID) throws DatabaseDAOException{
		Optional<Company> optionalCompany = Optional.empty();		
		optionalCompany = Optional.of(companyDAO.findByID(ID).get(0));
		
		return optionalCompany;
	}

	@Transactional
	public List<Company> getAllCompany() throws DatabaseDAOException{
		List<Company> listCompany = new ArrayList<>();
		listCompany = companyDAO.findAll();
		
		return listCompany;
	}

	@Transactional
	public int getNbRows() throws DatabaseDAOException{
		return  companyDAO.getNbRow();
	}
	
	@Transactional
	public void deleteCompany(int iDCompany) throws DatabaseDAOException{
		companyDAO.deleteCompany(iDCompany);
	}

}
