package fr.excilys.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.dto.CompanyDTO;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.service.CompanyDAOService;
import fr.excilys.service.ComputerDAOService;
import fr.excilys.mapper.CompanyMapper;
import fr.excilys.mapper.ComputerMapper;


@WebServlet(name = "EditComputerServlet", urlPatterns = "/editComputer")
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String EDIT_COMPUTER = "/WEB-INF/views/editComputer.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		int computerToEditID = Integer.parseInt(request.getParameter("computerId"));
		
		ComputerDAOService computerService = ComputerDAOService.getInstance();
		CompanyDAOService companyService = CompanyDAOService.getInstance();

		List<Company> companyList = companyService.getAllCompany();
		List<CompanyDTO> companyDTOList = companyList.stream().map(company -> CompanyMapper
				.fromCompanyToCompanyDTO(company)).collect(Collectors.toList());
		
		Computer computer = computerService.findByID(computerToEditID).get();
		ComputerDTO computerDTO = ComputerMapper.getInstance()
				.fromComputerToComputerDTO(computer);

		request.setAttribute("companysDTO", companyDTOList);
		request.setAttribute("computerDTO", computerDTO);
		request.setAttribute("companyOfComputer",computer.getCompany().getName());
		request.setAttribute("introducedDate", computer.getIntroducedDate());
		request.setAttribute("discontinuedDate", computer.getDiscontinuedDate());
		request.getRequestDispatcher(EDIT_COMPUTER).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
