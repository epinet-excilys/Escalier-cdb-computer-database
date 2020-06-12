package fr.excilys.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.dto.DashBoardParameterDTO;
import fr.excilys.exception.DatabaseManipulationException;
import fr.excilys.pagination.Paginate;
import fr.excilys.validator.EnumMessageErrorValidation;

@Service
public class DashBoardService {
	
	private ComputerService computerService;
	private Paginate page;
	private Map<String,String> valuesToReturn;
 
	
	public DashBoardService(ComputerService computerService, Paginate page) {
		super();
		this.computerService = computerService;
		this.page = page;
	}
	
	
	public List<ComputerDTO> loadPage(DashBoardParameterDTO dashBoardParameterDTO) throws DatabaseManipulationException {
		
		try {
			page.getValues(dashBoardParameterDTO);
			List<ComputerDTO>listToReturn = page.paginate();
			setValueToTransfert(page);
			return listToReturn;
			
		} catch (DatabaseManipulationException databaseManipulationException) {
			throw new DatabaseManipulationException();
		}
		
	}
	
	public void deleteComputer(String idSelectionAsList) {

		if(idSelectionAsList != null && !idSelectionAsList.isBlank()) {
			computerService.deleteByGroup(idSelectionAsList);
			valuesToReturn.put("successMessage", EnumMessageErrorValidation.SUCCESS_DELETE.getMessage());
		}
	}
	
	private void setValueToTransfert(Paginate page) {
		this.valuesToReturn = page.returnMapedValues();
	}
	
	public Map<String,String> getValuesToTransfer() {
		return valuesToReturn;
		
	}

	

}
