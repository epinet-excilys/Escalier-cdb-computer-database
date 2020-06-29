package fr.excilys.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.excilys.dao.ComputerDAO;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Computer;

@Service
public class ComputerService {

	private ComputerDAO computerDAO;
	private ComputerMapper computerMapper;

	public ComputerService(ComputerDAO computerDAO, ComputerMapper computerMapper) {
		this.computerDAO = computerDAO;
		this.computerMapper = computerMapper;
	}

	@Transactional
	public void update(Computer computer) throws DatabaseDAOException {
		computerDAO.update(computer);
	}

	@Transactional
	public void add(Computer computer) throws DatabaseDAOException{
		computerDAO.create(computer);
	}
	@Transactional
	public void addDTO(ComputerDTO computerDTO) {
		Computer computerToCreate = computerMapper.fromComputerDTOToComputer(computerDTO);
		computerDAO.create(computerToCreate);
	}


	@Transactional
	public void delete(int iD) throws DatabaseDAOException{
		computerDAO.delete(iD);
	}
	
	@Transactional
	public void deleteByGroup(String idSelectionAsList) {
		
		computerDAO.deleteByGroup(getListIDToDelete(idSelectionAsList));
	}

	@Transactional
	public Optional<Computer> findByID(int ID) throws DatabaseDAOException {

		Optional<Computer> Optionalcomputer = Optional.empty();		
		Optionalcomputer = Optional.of(computerDAO.findByID(ID).get(0));
		
		return Optionalcomputer;
	}

	@Transactional
	public List<Computer> getAllComput() throws DatabaseDAOException{
		List<Computer> listComputer = new ArrayList<>();
		listComputer = computerDAO.findAll();

		return listComputer;

	}

	@Transactional
	public List<Computer> getAllPaginateComput(int ligneDebutOffSet, int taillePage) throws DatabaseDAOException{
		List<Computer> listComputer = new ArrayList<>();
		listComputer = computerDAO.findAllPaginate(ligneDebutOffSet, taillePage);
		return listComputer;

	}
	
	@Transactional
	public List<Computer> findAllPaginateSearchLike(String search, int ligneDebutOffSet, int taillePage) throws DatabaseDAOException{
		List<Computer> listComputer = new ArrayList<>();
		listComputer = computerDAO.findAllPaginateSearchLike(search,ligneDebutOffSet, taillePage);

		return listComputer;
	}
	
	@Transactional
	public List<Computer> findAllPaginateOrder(int ligneDebutOffSet, int taillePage, String order) throws DatabaseDAOException{
		
		String orderBy = getCorrectOrder(order.toUpperCase());
		List<Computer> listComputer = new ArrayList<>();
		listComputer = computerDAO.findAllPaginateOrder(ligneDebutOffSet, taillePage,orderBy);
		
		return listComputer;
	}

	@Transactional
	public int getNbRows() throws DatabaseDAOException{

		return (int)computerDAO.getNbRow();
	}
	
	@Transactional
	public int getNbRowsSearch(String search) throws DatabaseDAOException{

		return (int)computerDAO.getNbRowSearch(search);
	}
	
	@Transactional
	private List<Integer> getListIDToDelete(String idSelectionAsList) {
		
		String[] splitChoiceToDelete = idSelectionAsList.split(",",-1);
		List<String> listOfIdString = convertArrayToList(splitChoiceToDelete);
		
		return listOfIdString.stream().map(stringID -> Integer.parseInt(stringID)).collect(Collectors.toList());
	}
	
	@SuppressWarnings("hiding")
	private <String> List<String> convertArrayToList(String array[]) {
		return Arrays.stream(array).collect(Collectors.toList());
	}
	
	private String getCorrectOrder (String order){
		
			switch (order) {
			case "NAME":
				return (EnumOrderBy.COMPUTER_DAO.getMessage());
			case "INTRODUCED":
				return (EnumOrderBy.INTRODUCED_DATE_DAO.getMessage());
			case "DISCONTINUED":
				return (EnumOrderBy.DISCONTINUED_DATE_DAO.getMessage());
			case "COMPANY":
				return (EnumOrderBy.COMPANY_DAO.getMessage());
			default:
				return (EnumOrderBy.COMPUTER_DAO.getMessage());
			}
			
	}


}
