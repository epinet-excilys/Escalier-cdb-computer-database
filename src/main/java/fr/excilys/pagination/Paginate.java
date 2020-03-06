package fr.excilys.pagination;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Computer;
import fr.excilys.service.ComputerService;

public class Paginate {
	private int pageIterator = 0;
	private int pageSize = 20;
	private double maxPage = 0.00;
	private int NbRowComputer = 0;
	private String searchTerm;
	private String orderBy;
	private List<Computer> computerList = new ArrayList<>();
	private List<ComputerDTO> computerDTOListToReturn = new ArrayList<>();

	public List<ComputerDTO> paginate(HttpServletRequest request, HttpServletResponse response) {
		getValues(request);
		whichPaginateToCall(request, response);
		mapComputerToComputerDTO();
		setValues(request);
		return computerDTOListToReturn;

	}

	private void getValues(HttpServletRequest request) {
		if (request.getParameter("taillePage") != null) {
			this.pageSize = Integer.parseInt(request.getParameter("taillePage"));
		}
		if (request.getParameter("pageIterator") != null) {
			this.pageIterator = Integer.parseInt(request.getParameter("pageIterator"));
		}

		if ((request.getParameter("search") != null) && !request.getParameter("search").isBlank()) {
			this.searchTerm = request.getParameter("search");
		}
		
		if ((request.getParameter("orderName") != null)  && !(request.getParameter("orderName").isBlank()))  {
			this.orderBy = request.getParameter("orderName");
		}
	}

	private void setValues(HttpServletRequest request) {
		
		request.setAttribute("NbRowComputer", NbRowComputer);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("NbRowComputer", NbRowComputer);
		request.setAttribute("pageIterator", pageIterator);
		request.setAttribute("orderName",orderBy);
		request.setAttribute("search", searchTerm);
		request.setAttribute("taillePage", pageSize);
	}
	
	private void mapComputerToComputerDTO() {
		computerList.stream().forEach(computer -> computerDTOListToReturn
				.add(ComputerMapper.getInstance().fromComputerToComputerDTO(computer)));
	}

	private void paginateOrderByName(HttpServletRequest request, HttpServletResponse response) {
		NbRowComputer = ComputerService.getInstance().getNbRows();
		computerList = ComputerService.getInstance().findAllPaginateAlphabeticOrder(pageIterator * pageSize, pageSize);
		maxPage = Math.ceil(NbRowComputer / pageSize);
		searchTerm = "";
	}

	private void paginateSearchByTerm(HttpServletRequest request, HttpServletResponse response) {
		searchTerm = request.getParameter("search");
		NbRowComputer = ComputerService.getInstance().getNbRowsSearch(searchTerm);
		computerList = ComputerService.getInstance().findAllPaginateSearchLike(searchTerm, pageIterator * pageSize, pageSize);
		maxPage = Math.ceil(NbRowComputer / pageSize);
		orderBy = "";
	}
	
	private void paginateUsual(HttpServletRequest request, HttpServletResponse response) {
		NbRowComputer = ComputerService.getInstance().getNbRows();
		computerList = ComputerService.getInstance().getAllPaginateComput(pageIterator * pageSize, pageSize);
		maxPage = Math.ceil(NbRowComputer / pageSize);
		orderBy = "";
		searchTerm = "";
	}

	private void whichPaginateToCall(HttpServletRequest request, HttpServletResponse response) {
		if(searchTerm != null ) {
			paginateSearchByTerm(request,response);
		}else if (orderBy != null) {
			paginateOrderByName(request,response);
		}else {
			paginateUsual(request,response);
		}
	}
}
