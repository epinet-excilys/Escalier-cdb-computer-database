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

import fr.excilys.exception.DatabaseManipulationException;
import fr.excilys.pagination.Paginate;
import fr.excilys.service.ComputerService;
import fr.excilys.validator.EnumMessageErrorValidation;

@Controller
@RequestMapping(value = "/dashboard")
public class Dashboard {

	private static final String DASHBOARD = "dashboard";
	private static final String DELETE_COMPUTER = "deleteComputer";
	private ComputerService computerService;
	private Paginate page;
	private Map<String, String> valuesToTransfert = new HashMap<String, String>();

	public static Logger LOGGER = LoggerFactory.getLogger(Dashboard.class);

	public Dashboard(ComputerService computerService, Paginate pagination) {
		this.computerService = computerService;
		this.page = pagination;
	}

	@GetMapping
	public ModelAndView dashboard(@RequestParam(required = false, value = "pageIterator") String pageIterator,
			@RequestParam(required = false, value = "NbRowComputer") String NbRowComputer,
			@RequestParam(required = false, value = "maxPage") String maxPage,
			@RequestParam(required = false, value = "taillePage") String pageSize,
			@RequestParam(required = false, value = "order") String orderBy,
			@RequestParam(required = false, value = "search") String search,
			@RequestParam(required = false, value = "errorMessage") String errorMessage,
			@RequestParam(required = false, value = "successMessage") String successMessage) {

		ModelAndView modelAndView = new ModelAndView();

		setMessage(errorMessage, "errorMessage", modelAndView);
		setMessage(successMessage, "successMessage", modelAndView);

		setValuesToTransfert( pageIterator, NbRowComputer, maxPage, pageSize, orderBy, search);
		try {
			page.paginate(valuesToTransfert, modelAndView);
		} catch (DatabaseManipulationException databaseManipulationException) {
			setMessage(EnumMessageErrorValidation.ERROR_PAGINATION.getMessage(), "errorMessage", modelAndView);
		}

		setValuesToTransfert(pageIterator, NbRowComputer, maxPage, pageSize, orderBy, search);

		return modelAndView;

	}

	@PostMapping(value = "/" + DELETE_COMPUTER)
	public ModelAndView deleteComputer(@RequestParam(value = "selection") String idSelectionAsList) {
		
		ModelAndView modelAndView = new ModelAndView("redirect:/"+DASHBOARD);
		
		if(idSelectionAsList != null && !idSelectionAsList.isBlank()) {
			computerService.deleteByGroup(idSelectionAsList);
			setMessage(EnumMessageErrorValidation.SUCCESS_DELETE.getMessage(), "successMessage", modelAndView);
		}
		
		return modelAndView;
	}
	
	private void setValuesToTransfert(String pageIterator, String NbRowComputer, String maxPage, String pageSize,
			String orderBy, String search) {
		valuesToTransfert.clear();
		valuesToTransfert.put("pageIterator", pageIterator);
		valuesToTransfert.put("NbRowComputer", NbRowComputer);
		valuesToTransfert.put("maxPage", maxPage);
		valuesToTransfert.put("order", orderBy);
		valuesToTransfert.put("search", search);
		valuesToTransfert.put("taillePage", pageSize);
	}
	
	private void setMessage(String errorMessage, String messageTitle, ModelAndView modelAndView) {
		if (( errorMessage != null ) && (!errorMessage.isBlank())) {
			modelAndView.addObject(messageTitle, errorMessage);
		}
	}

}
