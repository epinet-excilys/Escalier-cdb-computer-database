package fr.excilys.controller;

import javax.servlet.http.HttpServlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.exception.ValidatorException;
import fr.excilys.service.EditComputerService;
import fr.excilys.validator.EnumMessageErrorValidation;

@Controller
@RequestMapping(value = "/editComputer")
public class EditComputer extends HttpServlet {

	private static final String EDIT_COMPUTER = "editComputer";
	private static final String DASHBOARD = "dashboard";
	private EditComputerService editComputerService;
	
	public EditComputer(EditComputerService editComputerService) {

		this.editComputerService = editComputerService;
		
	}
	
	@GetMapping
	public ModelAndView getComputerToEdit(@RequestParam(value = "computerId") String computerId,
			@RequestParam(required = false, value = "errorMessage") String errorMessage) {
		ModelAndView modelAndView = new ModelAndView();

		if(errorMessage != null && !errorMessage.isBlank()) {
			modelAndView.addObject("errorMessage",errorMessage);
		}

		try {
			
			modelAndView.addObject("computerDTO", editComputerService.computerDTOtoDisplayForEdit(computerId));
			modelAndView.addObject("companyDTOList", editComputerService.companyDTOListToDisplay());
			modelAndView.addObject("computerId", Integer.parseInt(computerId));
			modelAndView.setViewName(EDIT_COMPUTER);

		} catch (ValidatorException validationException) {
			modelAndView.addObject("errorMessage",  validationException.getMessage());
			modelAndView.setViewName("redirect:/"+ DASHBOARD);
		}
		return modelAndView;
	}
	
	@PostMapping
	public ModelAndView editComputer(@RequestParam(value = "computerId") String computerId,
			@RequestParam(value = "computerNameArenvoyer") String computerName,
			@RequestParam(required = false, value = "introduced") String introduced,
			@RequestParam(required = false, value = "discontinued") String discontinued,
			@RequestParam(required = false, value = "companyId") String companyId){
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("computerId", Integer.parseInt(computerId));
		
		try {
			
			editComputerService.editComputerInService(computerId, computerName, introduced, discontinued, companyId);
			modelAndView.addObject("successMessage", EnumMessageErrorValidation.SUCCESS_UPDATE.getMessage());
			modelAndView.setViewName("redirect:/" + DASHBOARD);
			
		} catch (ValidatorException validationException) {
			
			modelAndView.addObject("errorMessage", validationException.getMessage());
			modelAndView.setViewName("redirect:/" + EDIT_COMPUTER);	
		}
		return modelAndView;
	}

	
	
}
