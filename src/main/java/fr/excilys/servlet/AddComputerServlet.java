package fr.excilys.servlet;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.dto.CompanyDTO;
import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.exception.DatabaseManipulationException;
import fr.excilys.mapper.CompanyMapper;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.service.CompanyService;
import fr.excilys.service.ComputerService;
import fr.excilys.validator.Validator;

@Controller
public class AddComputerServlet extends HttpServlet {

	private static final String ADD_COMPUTER = "addComputer";
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
		if (errorMessage.isEmpty() && !errorMessage.isBlank()) {
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
			Computer computer = ComputerMapper.fromComputerDTOToComputer(computerDTO);
			pcService.create(computer);

			message = ShowMessages.SUCCESS_MSG_UPDATE.getMsg();
			modelAndView.addObject("successMsg", message);
			modelAndView.setViewName("redirect:/dashboard");
		} catch (ValidationException vld) {
			modelAndView.addObject("errorMsg", vld.getMessage());
			modelAndView.setViewName("redirect:/addComputer");
		}

		return modelAndView;

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String computerName = request.getParameter("computerName");
		LocalDate introducedDate = computerMapper.fromStringToLocalDate(request.getParameter("introduced"));
		LocalDate discontinuedDate = computerMapper.fromStringToLocalDate(request.getParameter("discontinued"));
		int companyId = Integer.parseInt(request.getParameter("companyId"));

		Company company = (companyId != 0 ? (companyService.findByID((companyId))).get() : (null));

		Computer computer = new Computer.Builder().setNameBuild(computerName).setIntroducedDateBuild(introducedDate)
				.setDiscontinuedDateBuild(discontinuedDate).setIdCompagnyBuild(company).build();

		ValidateComputer(computer);

		doGet(request, response);
	}

	private void ValidateComputer(Computer computer) {
		try {
			if (Validator.getInstance().Validation(computer)) {
				computerService.add(computer);
			}
		} catch (DatabaseDAOException databaseDAOException) {
			// TODO EXCEPTION Catching
		} catch (DatabaseManipulationException databaseManipulationException) {
			// TODO EXCEPTION Catching
		}
	}

}
