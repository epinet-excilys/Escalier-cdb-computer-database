package fr.excilys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.dto.DashBoardParameterDTO;
import fr.excilys.exception.DatabaseManipulationException;
import fr.excilys.pagination.Paginate;
import fr.excilys.service.ComputerService;
import fr.excilys.service.DashBoardService;
import fr.excilys.validator.EnumMessageErrorValidation;

@Controller
@RequestMapping(value = "/dashboard")
public class Dashboard {

	private static final String DASHBOARD = "dashboard";
	private static final String DELETE_COMPUTER = "deleteComputer";
	private final String ErrorMessage = "errorMessage";
	private final String SuccessMessage = "successMessage";
	
	private DashBoardService dashBoardService;
	
	public static Logger LOGGER = LoggerFactory.getLogger(Dashboard.class);

	public Dashboard(DashBoardService dashBoardService) {
		this.dashBoardService = dashBoardService;
	}

	@GetMapping
	public ModelAndView dashboard(DashBoardParameterDTO dashBoardParameterDTO) {

		ModelAndView modelAndView = new ModelAndView();
		setBothMessage(dashBoardParameterDTO, modelAndView);
		try {
			
			setModelAndViewWithMappedValueAndComputerList(
					modelAndView, 
					dashBoardService.loadPage(dashBoardParameterDTO), 
					dashBoardService.getValuesToTransfer());
			
		} catch (DatabaseManipulationException databaseManipulationException) {
			setMessage(EnumMessageErrorValidation.ERROR_PAGINATION.getMessage(), ErrorMessage, modelAndView);;
		}
		
		return modelAndView;

	}

	@PostMapping(value = "/" + DELETE_COMPUTER)
	public ModelAndView deleteComputer(@RequestParam(value = "selection") String idSelectionAsList) {
		
		ModelAndView modelAndView = new ModelAndView("redirect:/"+DASHBOARD);
		dashBoardService.deleteComputer(idSelectionAsList); 
		setConfirmation(dashBoardService.getValuesToTransfer(), modelAndView);
		return modelAndView;
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
	
	private void setModelAndViewWithMappedValueAndComputerList(ModelAndView modelAndView,List<ComputerDTO> computerDTOList,Map<String,String> valuesToReturn) {

		modelAndView.addObject("NbRowComputer", valuesToReturn.get("NbRowComputer"));
		modelAndView.addObject("maxPage", valuesToReturn.get("maxPage"));
		modelAndView.addObject("pageIterator", valuesToReturn.get("pageIterator"));
		modelAndView.addObject("order", valuesToReturn.get("order"));
		modelAndView.addObject("search",  valuesToReturn.get("search"));
		modelAndView.addObject("taillePage", valuesToReturn.get("taillePage"));
		modelAndView.addObject("computerList", computerDTOList);
	}
	
	private void setConfirmation(Map<String,String> valuesToReturn, ModelAndView modelAndView) {
		setMessage(valuesToReturn.get(SuccessMessage), SuccessMessage, modelAndView);
	}
	
	

}
