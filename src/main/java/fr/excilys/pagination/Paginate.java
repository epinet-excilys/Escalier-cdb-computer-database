package fr.excilys.pagination;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Computer;
import fr.excilys.service.ComputerService;

@Component
public class Paginate {
	private int pageIterator;
	private int pageSize;
	private double maxPage;
	private int NbRowComputer;
	private String searchTerm;
	private String orderBy;
	private List<Computer> computerList = new ArrayList<>();
	private List<ComputerDTO> computerDTOListToReturn = new ArrayList<>();
	private ComputerService computerService;
	private ComputerMapper computerMapper;
	
	@Autowired
	public Paginate(ComputerService computerService, ComputerMapper computerMapper) {
	 this.computerService=computerService;	
	 this.computerMapper=computerMapper;
	}
	

	public List<ComputerDTO> paginate(HttpServletRequest request, HttpServletResponse response) {
		
		getValues(request);
		whichPaginateToCall(request, response);
		mapComputerToComputerDTO();
		setValues(request);
		
		return computerDTOListToReturn;

	}
	


	private void getValues(HttpServletRequest request) {
		
		setPageSize(request);
		setPageIterator(request);
		if ((request.getParameter("search") != null) && !request.getParameter("search").isBlank()) {
			this.searchTerm = request.getParameter("search");
		}
		
		if ((request.getParameter("order") != null)  && !(request.getParameter("order").isBlank()))  {
			this.orderBy = request.getParameter("order");
		}
	}


	private void setPageIterator(HttpServletRequest request) {
		if (request.getParameter("pageIterator") != null) {
			this.pageIterator = Integer.parseInt(request.getParameter("pageIterator"));
		} else {
			this.pageIterator = 0;
		}
	}


	private void setPageSize(HttpServletRequest request) {
		if (request.getParameter("taillePage") != null) {
			this.pageSize = Integer.parseInt(request.getParameter("taillePage"));
		} else {
			this.pageSize = 20;
		}
	}

	private void setValues(HttpServletRequest request) {
		
		request.setAttribute("NbRowComputer", NbRowComputer);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("NbRowComputer", NbRowComputer);
		request.setAttribute("pageIterator", pageIterator);
		request.setAttribute("order",orderBy);
		request.setAttribute("search", searchTerm);
		request.setAttribute("taillePage", pageSize);
	}
	
	private void mapComputerToComputerDTO() {
		
		computerDTOListToReturn.clear();
		computerList.stream().forEach(computer -> computerDTOListToReturn
				.add(computerMapper.fromComputerToComputerDTO(computer)));
	}

	private void paginateOrder(HttpServletRequest request, HttpServletResponse response) throws DatabaseDAOException{
		
		NbRowComputer = computerService.getNbRows();
		computerList.clear();
		computerList = computerService.findAllPaginateOrder(pageIterator * pageSize, pageSize, orderBy);
		maxPage = Math.ceil(NbRowComputer / pageSize);
		searchTerm = null;
	}

	private void paginateSearchByTerm(HttpServletRequest request, HttpServletResponse response) throws DatabaseDAOException{
		
		searchTerm = request.getParameter("search");
		NbRowComputer = computerService.getNbRowsSearch(searchTerm);
		computerList.clear();
		computerList =computerService.findAllPaginateSearchLike(searchTerm, pageIterator * pageSize, pageSize);
		maxPage = Math.ceil(NbRowComputer / pageSize);
		orderBy = null;
	}
	
	private void paginateUsual(HttpServletRequest request, HttpServletResponse response) throws DatabaseDAOException{
		
		NbRowComputer = computerService.getNbRows();
		computerList.clear();
		System.out.println("Values pageiterator : " + pageIterator + "Values pageSize : " + pageSize );
		computerList = computerService.getAllPaginateComput(pageIterator * pageSize, pageSize);
		maxPage = Math.ceil(NbRowComputer / pageSize);
		orderBy = null;
		searchTerm = null;
	}

	private void whichPaginateToCall(HttpServletRequest request, HttpServletResponse response) {
		
		if(searchTerm != null && !searchTerm.equals("") ) {
			paginateSearchByTerm(request,response);
		}else if (orderBy != null &&  !orderBy.equals("")) {
			paginateOrder(request,response);
		}else {
			paginateUsual(request,response);
		}
	}
	
	
}
