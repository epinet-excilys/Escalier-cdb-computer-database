package fr.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.mapper.CompanyMapper;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Computer;
import fr.excilys.pagination.Paginate;
import fr.excilys.service.ComputerService;


@WebServlet(name = "DashBoardServlet", urlPatterns = "/DashBoard")
public class DashBoardServlet extends HttpServlet {
	private int pageIterator = 0;
	private int pageSize = 20;
	private double maxPage = 0.00;
	private int NbRowComputer = 0;
	private String searchTerm;
	
	
	private static final String DASHBOARD = "/WEB-INF/views/dashboard.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		NbRowComputer = ComputerService.getInstance().getNbRows();
		Paginate pagination = new Paginate();
		List<ComputerDTO> computerDTOList = pagination.paginate(request, response);
		request.setAttribute("computerList", computerDTOList);
		request.getRequestDispatcher(DASHBOARD).forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("taillePage") != null) {
			pageSize = Integer.parseInt(request.getParameter("taillePage"));
		}
		String selectionToDelete = request.getParameter("selection");
		String[] splitChoiceToDelete = selectionToDelete.split(",", pageSize);
		List<String> listToDeleteAsString = convertArrayToList(splitChoiceToDelete);
		List<Integer> listIdToDelete= new ArrayList<>();
		listToDeleteAsString.stream()
		.forEach(idToDelete -> listIdToDelete.add(Integer.parseInt(idToDelete)));
		for(int ID :listIdToDelete) {
			ComputerService.getInstance().delete(ID);
		}
		doGet(request, response);
	}
	
	private <String> List<String> convertArrayToList(String array[]) 
    { 
        return Arrays 
            .stream(array) 
            .collect(Collectors.toList()); 
    } 

}
