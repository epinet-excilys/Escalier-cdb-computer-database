package fr.excilys.pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.dto.DashBoardParameterDTO;
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
	private int startingValueDefault = 0;
	private int pageHeightDefault = 20; 
	private String searchTerm;
	private String orderBy;
	private List<Computer> computerList = new ArrayList<>();
	private List<ComputerDTO> computerDTOListToReturn = new ArrayList<>();
	private ComputerService computerService;
	private ComputerMapper computerMapper;
	
	public Paginate(ComputerService computerService, ComputerMapper computerMapper) {
	 this.computerService=computerService;	
	 this.computerMapper=computerMapper;
	}
	

	public void paginate(DashBoardParameterDTO dashBoardParameterDTO,ModelAndView modelAndView) {
		
		getValues(dashBoardParameterDTO);
		whichPaginateToCall();
		mapComputerToComputerDTO();
		setValues(modelAndView);

	}
	


	public void getValues(DashBoardParameterDTO dashBoardParameterDTO) {
		
		setPageSize(dashBoardParameterDTO);
		setPageIterator(dashBoardParameterDTO);
		setSearchTerm(dashBoardParameterDTO);
		setOrderBy(dashBoardParameterDTO);
		
	}


	private void setOrderBy(DashBoardParameterDTO dashBoardParameterDTO) {
		if ((dashBoardParameterDTO.getOrder() != null)  && !(dashBoardParameterDTO.getOrder().isBlank()))  {
			this.orderBy = dashBoardParameterDTO.getOrder() ;
		} else {
			this.orderBy = null;
		}
	}

	private void setSearchTerm(DashBoardParameterDTO dashBoardParameterDTO) {
		if ((dashBoardParameterDTO.getSearch() != null) && !dashBoardParameterDTO.getSearch().isBlank()) {
			this.searchTerm = dashBoardParameterDTO.getSearch() ;
		} else {
			this.searchTerm = null;
		}
	}

	private void setPageIterator(DashBoardParameterDTO dashBoardParameterDTO) {
		if (dashBoardParameterDTO.getPageIterator()  != null) {
			this.pageIterator = Integer.parseInt(dashBoardParameterDTO.getPageIterator());
		} else {
			this.pageIterator = startingValueDefault;
		}
	}

	private void setPageSize(DashBoardParameterDTO dashBoardParameterDTO) {
		if (dashBoardParameterDTO.getPageSize()  != null) {
			this.pageSize = Integer.parseInt(dashBoardParameterDTO.getPageSize()  );
		} else {
			this.pageSize = pageHeightDefault;
		}
	}

	private void setValues(ModelAndView modelAndView) {
		
		modelAndView.addObject("NbRowComputer", NbRowComputer);
		modelAndView.addObject("maxPage", maxPage);
		modelAndView.addObject("pageIterator", pageIterator);
		modelAndView.addObject("order",orderBy);
		modelAndView.addObject("search", searchTerm);
		modelAndView.addObject("taillePage", pageSize);
		modelAndView.addObject("computerList", computerDTOListToReturn);
	}
	
	private void mapComputerToComputerDTO() {
		
		computerDTOListToReturn.clear();
		computerList.stream().forEach(computer -> computerDTOListToReturn
				.add(computerMapper.fromComputerToComputerDTO(computer)));
	}

	private void paginateOrder() throws DatabaseDAOException{
		
		NbRowComputer = computerService.getNbRows();
		computerList.clear();
		computerList = computerService.findAllPaginateOrder(pageIterator * pageSize, pageSize, orderBy);
		maxPage = Math.ceil(NbRowComputer / pageSize);
		searchTerm = null;
	}

	private void paginateSearchByTerm() throws DatabaseDAOException{
		
		NbRowComputer = computerService.getNbRowsSearch(searchTerm);
		computerList.clear();
		computerList =computerService.findAllPaginateSearchLike(searchTerm, pageIterator * pageSize, pageSize);
		maxPage = Math.ceil(NbRowComputer / pageSize);
		orderBy = null;
	}
	
	private void paginateUsual() throws DatabaseDAOException{
		
		NbRowComputer = computerService.getNbRows();
		computerList.clear();
		computerList = computerService.getAllPaginateComput(pageIterator * pageSize, pageSize);
		maxPage = Math.ceil(NbRowComputer / pageSize);
		orderBy = null;
		searchTerm = null;
	}

	private void whichPaginateToCall() {
		if(searchTerm != null && !searchTerm.equals("") ) {
			paginateSearchByTerm();
		}else if (orderBy != null &&  !orderBy.equals("")) {
			paginateOrder();
		}else {
			paginateUsual();
		}
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,ToStringStyle.JSON_STYLE);
	}
	
}
