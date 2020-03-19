package fr.excilys.pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

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
	
	public Paginate(ComputerService computerService, ComputerMapper computerMapper) {
	 this.computerService=computerService;	
	 this.computerMapper=computerMapper;
	}
	

	public void paginate(Map<String,String> valuesToTransfert,ModelAndView modelAndView) {
		
		getValues(valuesToTransfert);
		whichPaginateToCall();
		mapComputerToComputerDTO();
		setValues(modelAndView);

	}
	


	public void getValues(Map<String,String> valuesToTransfert) {
		
		setPageSize(valuesToTransfert);
		setPageIterator(valuesToTransfert);
		setSearchTerm(valuesToTransfert);
		setOrderBy(valuesToTransfert);
		
	}


	private void setOrderBy(Map<String, String> valuesToTransfert) {
		if ((valuesToTransfert.get("order") != null)  && !(valuesToTransfert.get("order").isBlank()))  {
			this.orderBy = valuesToTransfert.get("order");
		} else {
			this.orderBy = null;
		}
	}

	private void setSearchTerm(Map<String, String> valuesToTransfert) {
		if ((valuesToTransfert.get("search") != null) && !valuesToTransfert.get("search").isBlank()) {
			this.searchTerm = valuesToTransfert.get("search");
		} else {
			this.searchTerm = null;
		}
	}

	private void setPageIterator(Map<String,String> valuesToTransfert) {
		if (valuesToTransfert.get("pageIterator") != null) {
			this.pageIterator = Integer.parseInt(valuesToTransfert.get("pageIterator"));
		} else {
			this.pageIterator = 0;
		}
	}

	private void setPageSize(Map<String,String> valuesToTransfert) {
		if (valuesToTransfert.get("taillePage") != null) {
			this.pageSize = Integer.parseInt(valuesToTransfert.get("taillePage") );
		} else {
			this.pageSize = 20;
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
