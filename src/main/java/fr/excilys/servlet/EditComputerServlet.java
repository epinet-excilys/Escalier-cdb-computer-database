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
import fr.excilys.dto.ComputerDTO;
import fr.excilys.mapper.CompanyMapper;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.service.CompanyService;
import fr.excilys.service.ComputerService;
import fr.excilys.validator.Validator;


@WebServlet(name = "EditComputerServlet", urlPatterns = "/editComputer")
@Controller
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String EDIT_COMPUTER = "/WEB-INF/views/editComputer.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		int computerToEditID = Integer.parseInt(request.getParameter("computerId"));
		
		ComputerService computerService = ComputerService.getInstance();
		CompanyService companyService = CompanyService.getInstance();

		List<Company> companyList = companyService.getAllCompany();

		List<CompanyDTO> companyDTOList = new ArrayList<>();
		companyList.stream()
				.forEach(company -> companyDTOList.add(CompanyMapper.getInstance().fromCompanyToCompanyDTO(company)));
		Computer computer = computerService.findByID(computerToEditID).get();
		ComputerDTO computerDTO = ComputerMapper.getInstance()
				.fromComputerToComputerDTO(computer);

		request.setAttribute("companyDTOList", companyDTOList);
		request.setAttribute("computerDTO", computerDTO);
		request.setAttribute("companyOfComputer",computer.getCompany().getName());
		request.setAttribute("introducedDate", computer.getIntroducedDate());
		request.setAttribute("discontinuedDate", computer.getDiscontinuedDate());
		request.getRequestDispatcher(EDIT_COMPUTER).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerService computerService = ComputerService.getInstance();
		CompanyService companyService = CompanyService.getInstance();

		int computerId = Integer.parseInt(request.getParameter("computerId"));
		String computerName = request.getParameter("computerNameArenvoyer");		
		LocalDate introducedDate = ComputerMapper.getInstance()
				.fromStringToLocalDate(request.getParameter("introduced"));
		LocalDate discontinuedDate = ComputerMapper.getInstance()
				.fromStringToLocalDate(request.getParameter("discontinued"));		
		int companyId = Integer.parseInt(request.getParameter("companyId"));
		Company company = (companyId != 0 ?(companyService.findByID((companyId))).get():(null));
		Computer computer = new Computer.Builder().setIdBuild(computerId).setNameBuild(computerName).setIntroducedDateBuild(introducedDate)
				.setDiscontinuedDateBuild(discontinuedDate).setIdCompagnyBuild(company).build();
		
		if(Validator.getInstance().Validation(computer)) {
			computerService.update(computer);
			
		}
		doGet(request, response);
	}

}
