package fr.excilys.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CompanyService companyService = CompanyService.getInstance();

		List<Company> companyList = companyService.getAllCompany();

		List<CompanyDTO> companyDTOList = new ArrayList<>();
		companyList.stream()
				.forEach(company -> companyDTOList.add(CompanyMapper.getInstance().fromCompanyToCompanyDTO(company)));

		request.setAttribute("companyDTOList", companyDTOList);

		this.getServletContext().getRequestDispatcher(ADD_COMPUTER).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CompanyService companyService = CompanyService.getInstance();

		String computerName = request.getParameter("computerName");
		LocalDate introducedDate = ComputerMapper.getInstance()
				.fromStringToLocalDate(request.getParameter("introduced"));
		LocalDate discontinuedDate = ComputerMapper.getInstance()
				.fromStringToLocalDate(request.getParameter("discontinued"));
		int companyId = Integer.parseInt(request.getParameter("companyId"));

		Company company = (companyId != 0 ? (companyService.findByID((companyId))).get() : (null));

		Computer computer = new Computer.Builder().setNameBuild(computerName).setIntroducedDateBuild(introducedDate)
				.setDiscontinuedDateBuild(discontinuedDate).setIdCompagnyBuild(company).build();

		ValidateComputer(computer);

		doGet(request, response);
	}

	private void ValidateComputer(Computer computer) {
		ComputerService computerService = ComputerService.getInstance();
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
