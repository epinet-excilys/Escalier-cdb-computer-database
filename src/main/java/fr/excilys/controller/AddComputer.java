package fr.excilys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.dto.AddAndEditComputerParameterDTO;
import fr.excilys.exception.ValidatorException;
import fr.excilys.service.AddComputerService;
import fr.excilys.validator.EnumMessageErrorValidation;

@Controller
@RequestMapping(value = "/addComputer")
public class AddComputer {

	private static final String ADD_COMPUTER = "addComputer";
	private static final String DASHBOARD = "dashboard";
	private AddComputerService addComputerService; 
	

	public AddComputer(AddComputerService addComputerService) {

		this.addComputerService = addComputerService;

	}

	@GetMapping
	public ModelAndView companyDTOListView(
			@RequestParam(required = false, value = "errorMessage") String errorMessage) {

		ModelAndView modelAndView = new ModelAndView();
		
		if (errorMessage != null && !errorMessage.isBlank()) {
			modelAndView.addObject("errorMessage", errorMessage);
		}

		modelAndView.addObject("companyDTOList", addComputerService.getCompanyDTOListInModelView());
		modelAndView.setViewName(ADD_COMPUTER);

		return modelAndView;

	}

	@PostMapping
	public ModelAndView addComputerView(AddAndEditComputerParameterDTO addAndEditComputerParameterDTO) {

		ModelAndView modelAndView = new ModelAndView();
		
		try {
			
			addComputerService.addComputerInService(addAndEditComputerParameterDTO);
			
			modelAndView.addObject("successMessage", EnumMessageErrorValidation.SUCCESS_CREATE.getMessage());
			modelAndView.setViewName("redirect:/"+ DASHBOARD);
		} catch(ValidatorException validatorException) {
			modelAndView.addObject("errorMessage", validatorException.getMessage());
			modelAndView.setViewName("redirect:/"+ADD_COMPUTER);
		}
		
		return modelAndView;

	}

}
