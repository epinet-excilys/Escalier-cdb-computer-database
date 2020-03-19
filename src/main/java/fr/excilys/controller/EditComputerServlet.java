package fr.excilys.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.aop.framework.adapter.DefaultAdvisorAdapterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.dto.CompanyDTO;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.exception.ValidatorException;
import fr.excilys.mapper.CompanyMapper;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.service.CompanyService;
import fr.excilys.service.ComputerService;
import fr.excilys.validator.EnumMessageErrorValidation;
import fr.excilys.validator.Validator;

@Controller
public class EditComputerServlet extends HttpServlet {

	private static final String EDIT_COMPUTER = "editComputer";
	private static final String DASHBOARD = "dashboard";
	private ComputerService computerService;
	private CompanyService companyService;
	private ComputerMapper computerMapper;
	private CompanyMapper companyMapper;
	private Validator validator;

	public EditComputerServlet(ComputerService computerService, CompanyService companyService,
			ComputerMapper computerMapper, CompanyMapper companyMapper, Validator validator) {

		this.computerService = computerService;
		this.companyService = companyService;
		this.computerMapper = computerMapper;
		this.companyMapper = companyMapper;
		this.validator = validator;
		
	}
	
	@GetMapping(value = "/" + EDIT_COMPUTER)
	public ModelAndView getComputerToEdit(@RequestParam(value = "computerId") String computerId,
			@RequestParam(required = false, value = "errorMessage") String errorMessage) {
		ModelAndView modelAndView = new ModelAndView();

		if(errorMessage != null && !errorMessage.isBlank()) {
			modelAndView.addObject("errorMessage",errorMessage);
		}

		try {
			
			int computerToEditID = Integer.parseInt(computerId);
			
			List<Company> companyList = companyService.getAllCompany();
			List<CompanyDTO> companyDTOList = new ArrayList<>();

			companyList.stream()
					.forEach(company -> companyDTOList.add(companyMapper.fromCompanyToCompanyDTO(company)));
			Computer computer = computerService.findByID(computerToEditID).get();
			ComputerDTO computerDTO =computerMapper.fromComputerToComputerDTO(computer);
			
			modelAndView.addObject("computer", computerDTO);
			modelAndView.addObject("companyDTOList", companyDTOList);
			modelAndView.addObject("computerId", computerToEditID);
			modelAndView.setViewName(EDIT_COMPUTER);

		} catch (ValidatorException validationException) {
			modelAndView.addObject("errorMessage",  validationException.getMessage());
			modelAndView.setViewName("redirect:/"+ DASHBOARD);
		}
		return modelAndView;
	}
	
	@PostMapping(value = "/editComputer")
	public ModelAndView editComputer(@RequestParam(value = "computerId") String computerId,
			@RequestParam(value = "computerName") String computerName,
			@RequestParam(required = false, value = "introduced") String introduced,
			@RequestParam(required = false, value = "discontinued") String discontinued,
			@RequestParam(required = false, value = "companyId") String companyId){
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("computerId", Integer.parseInt(computerId));
		try {
			validator.validateFields(computerName, introduced, discontinued, companyId);

			CompanyDTO companyDTO = new CompanyDTO();
			if(!companyId.isBlank() && !companyId.isEmpty() ) {
				companyDTO.setId(Integer.parseInt(companyId));
			}
			ComputerDTO computerDTO = new ComputerDTO(computerName, introduced, discontinued, companyDTO);
			computerDTO.setId(Integer.parseInt(computerId));

			Computer computer = computerMapper.fromComputerDTOToComputer(computerDTO);
			computerService.update(computer);

			modelAndView.addObject("successMessage", EnumMessageErrorValidation.SUCCESS_UPDATE.getMessage());
			modelAndView.setViewName("redirect:/" + DASHBOARD);
		} catch (ValidatorException validationException) {
			modelAndView.addObject("errorMessage", validationException.getMessage());
			modelAndView.setViewName("redirect:/" + EDIT_COMPUTER);	
		}
		return modelAndView;
	}

	private void setMessage(String errorMessage, String messageTitle, ModelAndView modelAndView) {
		if ((!errorMessage.isEmpty()) && (!errorMessage.isBlank())) {
			modelAndView.addObject(messageTitle, errorMessage);
		}
	}
	
}
