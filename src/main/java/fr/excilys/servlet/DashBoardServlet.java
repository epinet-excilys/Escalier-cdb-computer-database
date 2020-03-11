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

import fr.excilys.dao.ConnexionSQL;
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
	
	public static Logger LOGGER = LoggerFactory.getLogger(ConnexionSQL.class);
	
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
    	SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,config.getServletContext());
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
		int pageSize = 20;
		if (request.getParameter("taillePage") != null) {
			pageSize = Integer.parseInt(request.getParameter("taillePage"));
		}
		String selectionToDelete = request.getParameter("selection");
		String[] splitChoiceToDelete = selectionToDelete.split(",", pageSize);
		List<String> listToDeleteAsString = convertArrayToList(splitChoiceToDelete);
		List<Integer> listIdToDelete = new ArrayList<>();
		listToDeleteAsString.stream().forEach(idToDelete -> listIdToDelete.add(Integer.parseInt(idToDelete)));
		try {
			for (int ID : listIdToDelete) {
				computerService.delete(ID);
			}
		} catch (DatabaseDAOException databaseDAOException) {
			// TODO EXCEPTION Catching
		} catch (DatabaseManipulationException databaseManipulationException) {
			// TODO EXCEPTION Catching
		}

		doGet(request, response);
	}

	private <String> List<String> convertArrayToList(String array[]) {
		return Arrays.stream(array).collect(Collectors.toList());
	}
	
	
}
