package fr.excilys.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

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
	
	public void deleteByGroup(HttpServletRequest request) throws DatabaseDAOException{
		
		computerDAO.deleteByGroup(getListIDToDelete(request));
	}

	public Optional<Computer> findByID(int ID) throws DatabaseDAOException {
		Optional<Computer> Optionalcomputer = Optional.empty();		
		Optionalcomputer = Optional.of(computerDAO.findByID(ID).get(0));
		
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
	
	public List<Computer> findAllPaginateOrder(int ligneDebutOffSet, int taillePage, String order) throws DatabaseDAOException{
		
		String orderBy = getCorrectOrder(order.toUpperCase());
		List<Computer> listComputer = new ArrayList<>();
		listComputer = computerDAO.findAllPaginateOrder(ligneDebutOffSet, taillePage,orderBy);
		
		return listComputer;
	}

	public int getNbRows() throws DatabaseDAOException{

		return computerDAO.getNbRow();
	}
	
	public int getNbRowsSearch(String search) throws DatabaseDAOException{

		return computerDAO.getNbRowSearch(search);
	}
	
	private List<Integer> getListIDToDelete(HttpServletRequest request) {
		
		int pageSize = 20;		
		if (request.getParameter("taillePage") != null) {
			pageSize = Integer.parseInt(request.getParameter("taillePage"));
		}
		String selectionToDelete = request.getParameter("selection");
		String[] splitChoiceToDelete = selectionToDelete.split(",", pageSize);
		List<String> listOfIdString = convertArrayToList(splitChoiceToDelete);
		
		return listOfIdString.stream().map(stringID -> Integer.parseInt(stringID)).collect(Collectors.toList());
	}
	
	private <String> List<String> convertArrayToList(String array[]) {
		return Arrays.stream(array).collect(Collectors.toList());
	}
	
	private String getCorrectOrder (String order){
		
			switch (order) {
			case "COMPUTER":
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
