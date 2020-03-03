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

import fr.excilys.dto.CompanyDTO;
import fr.excilys.mapper.CompanyMapper;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.service.CompanyDAOService;
import fr.excilys.service.ComputerDAOService;
import fr.excilys.validator.Validator;

@WebServlet(name = "AddComputerServlet", urlPatterns = "/addComputer")
public class AddComputerServlet extends HttpServlet {

	private static final String ADD_COMPUTER = "/WEB-INF/views/addComputer.jsp";


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CompanyDAOService companyService = CompanyDAOService.getInstance();

		List<Company> companyList = companyService.getAllCompany();

		List<CompanyDTO> companyDTOList = new ArrayList<>();
		companyList.stream()
				.forEach(company -> companyDTOList.add(CompanyMapper.getInstance().fromCompanyToCompanyDTO(company)));

		request.setAttribute("companyDTOList", companyDTOList);

		this.getServletContext().getRequestDispatcher(ADD_COMPUTER).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ComputerDAOService computerService = ComputerDAOService.getInstance();
		CompanyDAOService companyService = CompanyDAOService.getInstance();

		String computerName = request.getParameter("computerName");		
		LocalDate introducedDate = ComputerMapper.getInstance()
				.fromStringToLocalDate(request.getParameter("introduced"));
		LocalDate discontinuedDate = ComputerMapper.getInstance()
				.fromStringToLocalDate(request.getParameter("discontinued"));		
		int companyId = Integer.parseInt(request.getParameter("companyId"));

		Company company = (companyService.findByID((companyId))).isPresent()?(companyService.findByID((companyId))).get():new Company.Builder().build();

		Computer computer = new Computer.Builder().setNameBuild(computerName).setIntroducedDateBuild(introducedDate)
				.setDiscontinuedDateBuild(discontinuedDate).setIdCompagnyBuild(company).build();
		
		if(Validator.getInstance().Validation(computer))computerService.add(computer);


		doGet(request, response);
	}

	

}
