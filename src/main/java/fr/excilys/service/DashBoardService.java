package fr.excilys.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.dto.DashBoardParameterDTO;
import fr.excilys.exception.DatabaseManipulationException;
import fr.excilys.pagination.Paginate;
import fr.excilys.validator.EnumMessageErrorValidation;

@Service
public class DashBoardService {
	
	private ComputerService computerService;
	private Paginate page;
	private final String ErrorMessage = "errorMessage";
	private final String SuccessMessage = "successMessage"; 
	
	public DashBoardService(ComputerService computerService, Paginate page) {
		super();
		this.computerService = computerService;
		this.page = page;
	}
	
	
	
	public void setMessage(String errorMessage, String messageTitle, ModelAndView modelAndView) {
		if (( errorMessage != null ) && (!errorMessage.isBlank())) {
			modelAndView.addObject(messageTitle, errorMessage);
		}
	}
	
	public void setBothMessage(DashBoardParameterDTO dashBoardParameterDTO, ModelAndView modelAndView) {
		setMessage(dashBoardParameterDTO.getErrorMessage(), ErrorMessage , modelAndView);
		setMessage(dashBoardParameterDTO.getSuccessMessage(), SuccessMessage, modelAndView);
	}
	
	public void loadPage(DashBoardParameterDTO dashBoardParameterDTO, ModelAndView modelAndView) {
		
		setBothMessage(dashBoardParameterDTO, modelAndView);
		try {
			page.paginate(dashBoardParameterDTO, modelAndView);
		} catch (DatabaseManipulationException databaseManipulationException) {
			setMessage(EnumMessageErrorValidation.ERROR_PAGINATION.getMessage(), ErrorMessage, modelAndView);
		}
		
	}
	
	public void deleteComputer(String idSelectionAsList,ModelAndView modelAndView) {

		if(idSelectionAsList != null && !idSelectionAsList.isBlank()) {
			computerService.deleteByGroup(idSelectionAsList);
			setMessage(EnumMessageErrorValidation.SUCCESS_DELETE.getMessage(), "successMessage", modelAndView);
		}
	}
	
	

}
