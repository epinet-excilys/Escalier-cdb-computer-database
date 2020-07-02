package fr.excilys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.excilys.dao.CompanyDAO;
import fr.excilys.dto.CompanyDTO;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.mapper.CompanyMapper;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;


@Service
public class CompanyService {

	private CompanyDAO companyDAO;
	private CompanyMapper companyMapper;
	
	public CompanyService(CompanyDAO companyDAO, CompanyMapper companyMapper) {
		this.companyDAO = companyDAO;
		this.companyMapper = companyMapper;
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
	public List<CompanyDTO> getAllCompanyDTO() throws DatabaseDAOException{
		List<Company> listCompany = new ArrayList<>();
		listCompany = companyDAO.findAll();
		
		return converttoDTOwithMap(listCompany);
	}
	
	@Transactional
	public Optional<CompanyDTO> findByIDDTO(int ID) throws DatabaseDAOException{
		Optional<CompanyDTO> optionalCompanyDTO = Optional.empty();		
		optionalCompanyDTO = Optional.of(companyMapper.fromCompanyToCompanyDTO(companyDAO.findByID(ID).get(0)));
		
		return optionalCompanyDTO;
	}
	
	@Transactional
	public void add(CompanyDTO companyDTO) throws DatabaseDAOException{
		
		companyDAO.create(companyMapper.fromCompanyDTOToCompany(companyDTO));
	}
	
	

	@Transactional
	public int getNbRows() throws DatabaseDAOException{
		return  companyDAO.getNbRow();
	}
	
	@Transactional
	public void deleteCompany(int iDCompany) throws DatabaseDAOException{
		companyDAO.deleteCompany(iDCompany);
	}
	
	private List<CompanyDTO> converttoDTOwithMap(List<Company> listCompany) {
		return listCompany.stream()
		.map(company -> companyMapper.fromCompanyToCompanyDTO(company))
		.collect(Collectors.toList());
	}

	public List<CompanyDTO> getAllPaginateCompany(int page, int size) {
		
		return converttoDTOwithMap(companyDAO.findAllPaginate(page*size, size));
	}


}
