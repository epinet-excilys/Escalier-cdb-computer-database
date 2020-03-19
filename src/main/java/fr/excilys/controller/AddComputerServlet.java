package fr.excilys.controller;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public class AddComputerServlet {

	private static final String ADD_COMPUTER = "addComputer";
	private static final String DASHBOARD = "DashBoard";
	private ComputerService computerService;
	private CompanyService companyService;
	private ComputerMapper computerMapper;
	private Validator validator;
	

	public AddComputerServlet(ComputerService computerService, CompanyService companyService,
			ComputerMapper computerMapper,  Validator validator) {

		this.computerService = computerService;
		this.companyService = companyService;
		this.computerMapper = computerMapper;
		this.validator = validator;

	}

	@GetMapping(value = "/" + ADD_COMPUTER)
	public ModelAndView companyDTOListView(
			@RequestParam(required = false, value = "errorMessage") String errorMessage) {

		ModelAndView modelAndView = new ModelAndView();
		if (errorMessage != null && !errorMessage.isBlank()) {
			modelAndView.addObject("errorMessage", errorMessage);
		}

		List<Company> companyList = companyService.getAllCompany();

		List<CompanyDTO> companyDTOList = companyList.stream().map(CompanyMapper::fromCompanyToCompanyDTO)
				.collect(toList());

		modelAndView.addObject("companyDTOList", companyDTOList);
		modelAndView.setViewName(ADD_COMPUTER);

		return modelAndView;

	}

	@PostMapping(value = "/" + ADD_COMPUTER)
	public ModelAndView addComputerView(@RequestParam(value = "computerName") String computerName,
			@RequestParam(required = false, value = "introduced") String introduced,
			@RequestParam(required = false, value = "discontinued") String discontinued,
			@RequestParam(required = false, value = "companyId") String companyId,
			@RequestParam(required = false, value = "errorMessage") String errorMessage) {
		ModelAndView modelAndView = new ModelAndView();
		String message = "";

		try {
			validator.validateFields(computerName, introduced, discontinued, companyId);
			CompanyDTO companyDTO = new CompanyDTO();
			if (!companyId.isBlank()) {
				companyDTO.setId(Integer.parseInt(companyId));
			}
			ComputerDTO computerDTO = new ComputerDTO(computerName, introduced, discontinued, companyDTO);
			Computer computer = computerMapper.fromComputerDTOToComputer(computerDTO);
			computerService.add(computer);

			message = EnumMessageErrorValidation.SUCCESS_CREATE.getMessage();
			modelAndView.addObject("successMsg", message);
			modelAndView.setViewName("redirect:/"+ DASHBOARD);
		} catch (ValidatorException validatorException) {
			modelAndView.addObject("errorMsg", validatorException.getMessage());
			modelAndView.setViewName("redirect:/"+ADD_COMPUTER);
		}

		return modelAndView;

	}

}
