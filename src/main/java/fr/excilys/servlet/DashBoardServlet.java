package fr.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.exception.DatabaseManipulationException;
import fr.excilys.pagination.Paginate;
import fr.excilys.service.ComputerService;

@WebServlet(name = "DashBoardServlet", urlPatterns = "/DashBoard")
@Controller
public class DashBoardServlet extends HttpServlet {
	private static final String DASHBOARD = "/WEB-INF/views/dashboard.jsp";
	@Autowired
	private ComputerService computerService;
	@Autowired
	private Paginate pagination;

	public static Logger LOGGER = LoggerFactory.getLogger(DashBoardServlet.class);

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<ComputerDTO> computerDTOList = new ArrayList<>();
		try {
			computerDTOList = pagination.paginate(request, response);
		} catch (DatabaseDAOException databaseDAOException) {
			// TODO EXCEPTION Catching
		} catch (DatabaseManipulationException databaseManipulationException) {
			// TODO EXCEPTION Catching
		}

		request.setAttribute("computerList", computerDTOList);
		request.getRequestDispatcher(DASHBOARD).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			computerService.deleteByGroup(request);
		} catch (DatabaseDAOException databaseDAOException) {
			// TODO EXCEPTION Catching
		} catch (DatabaseManipulationException databaseManipulationException) {
			// TODO EXCEPTION Catching
		}

		doGet(request, response);
	}

}
