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
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

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

@WebServlet(name = "AddComputerServlet", urlPatterns = "/addComputer")
@Controller
public class AddComputerServlet extends HttpServlet {

	private static final String ADD_COMPUTER = "/WEB-INF/views/addComputer.jsp";
	private ComputerService computerService;
	private CompanyService companyService;
	private ComputerMapper computerMapper;

	@Autowired
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@Autowired
	public void setComputerService(ComputerService computerService) {
		this.computerService = computerService;
	}
	
	@Autowired
	public void setComputerMapper(ComputerMapper computerMapper) {
		this.computerMapper = computerMapper;
	}
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
    	SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,config.getServletContext());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Company> companyList = companyService.getAllCompany();

		List<CompanyDTO> companyDTOList = 
		companyList.stream()
				.map(CompanyMapper::fromCompanyToCompanyDTO).collect(toList());

		request.setAttribute("companyDTOList", companyDTOList);
		
		this.getServletContext().getRequestDispatcher(ADD_COMPUTER).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String computerName = request.getParameter("computerName");
		LocalDate introducedDate = computerMapper
				.fromStringToLocalDate(request.getParameter("introduced"));
		LocalDate discontinuedDate = computerMapper
				.fromStringToLocalDate(request.getParameter("discontinued"));
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
