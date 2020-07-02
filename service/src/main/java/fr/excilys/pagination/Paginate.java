package fr.excilys.pagination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.dto.DashBoardParameterDTO;
import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Computer;
import fr.excilys.service.ComputerService;

@Component
public class Paginate {
	private Integer pageIterator;
	private Integer pageSize;
	private Double maxPage;
	private Integer NbRowComputer;
	private Integer startingValueDefault = 0;
	private Integer pageHeightDefault = 20; 
	private String searchTerm;
	private String orderBy;
	private List<Computer> computerList = new ArrayList<>();
	private List<ComputerDTO> computerDTOListToReturn = new ArrayList<>();
	private ComputerService computerService;
	private ComputerMapper computerMapper;
	private Map<String, String> valuesToTransfert = new HashMap<String, String>();
	
	public Paginate(ComputerService computerService, ComputerMapper computerMapper) {
	 this.computerService=computerService;	
	 this.computerMapper=computerMapper;
	}
	

	public List<ComputerDTO> paginate() {
		
		whichPaginateToCall();
		mapComputerToComputerDTO();
		setValues();
		return computerDTOListToReturn ;

	}
	
	public Map<String, String>  returnMapedValues(){
		
		return valuesToTransfert;
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

	private void setValues() {
		
		valuesToTransfert.put("NbRowComputer", (NbRowComputer).toString());
		valuesToTransfert.put("maxPage", (maxPage).toString());
		valuesToTransfert.put("pageIterator", (pageIterator).toString());
		valuesToTransfert.put("order",orderBy);
		valuesToTransfert.put("search", searchTerm);
		valuesToTransfert.put("pageSize", (pageSize).toString());
		
	}
	
	private void mapComputerToComputerDTO() {
		
		computerDTOListToReturn.clear();
		computerList.stream().forEach(computer -> computerDTOListToReturn
				.add(computerMapper.fromComputerToComputerDTO(computer)));
	}

	private void paginateOrder() throws DatabaseDAOException{
		
		NbRowComputer = computerService.getNbRows();
		computerList.clear();
		computerList = fromDTOToComputerAsList(computerService.findAllPaginateOrder(pageIterator * pageSize, pageSize, orderBy));
		maxPage = Math.ceil(NbRowComputer / pageSize);
		searchTerm = null;
	}

	private void paginateSearchByTerm() throws DatabaseDAOException{
		
		NbRowComputer = computerService.getNbRowsSearch(searchTerm);
		computerList.clear();
		computerList =  fromDTOToComputerAsList(computerService.findAllPaginateSearchLike(searchTerm, pageIterator * pageSize, pageSize));
		maxPage = Math.ceil(NbRowComputer / pageSize);
		orderBy = null;
	}
	
	private void paginateUsual() throws DatabaseDAOException{
		
		NbRowComputer = computerService.getNbRows();
		computerList.clear();
		computerList =  fromDTOToComputerAsList(computerService.getAllPaginateComput(pageIterator * pageSize, pageSize));
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
	
	public List<Computer> fromDTOToComputerAsList (List<ComputerDTO> listComputerDTO){
		return listComputerDTO.stream()
				.map(computerDTO -> computerMapper.fromComputerDTOToComputer(computerDTO))
				.collect(Collectors.toList());
	}
	
	
}
