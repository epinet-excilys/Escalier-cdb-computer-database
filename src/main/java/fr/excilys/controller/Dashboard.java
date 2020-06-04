package fr.excilys.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	private DashBoardService dashBoardService;
	
	public static Logger LOGGER = LoggerFactory.getLogger(Dashboard.class);

	public Dashboard(DashBoardService dashBoardService) {
		this.dashBoardService = dashBoardService;
	}

	@GetMapping
	public ModelAndView dashboard(DashBoardParameterDTO dashBoardParameterDTO) {

		ModelAndView modelAndView = new ModelAndView();
		dashBoardService.loadPage(dashBoardParameterDTO, modelAndView);
		return modelAndView;

	}

	@PostMapping(value = "/" + DELETE_COMPUTER)
	public ModelAndView deleteComputer(@RequestParam(value = "selection") String idSelectionAsList) {
		
		ModelAndView modelAndView = new ModelAndView("redirect:/"+DASHBOARD);
		dashBoardService.deleteComputer(idSelectionAsList, modelAndView);
		return modelAndView;
	}

}
