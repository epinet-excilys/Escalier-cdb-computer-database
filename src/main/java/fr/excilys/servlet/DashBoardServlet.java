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

		maxPage = Math.ceil(NbRowComputer / pageSize);
		
		List<Computer> computerList = new ArrayList<>();
		List<ComputerDTO> computerDTOList = new ArrayList<>();

		if (request.getParameter("taillePage") != null) {
			pageSize = Integer.parseInt(request.getParameter("taillePage"));
		}
		if (request.getParameter("pageIterator") != null) {
			pageIterator = Integer.parseInt(request.getParameter("pageIterator"));
		}
		
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("NbRowComputer", NbRowComputer);
	
		if( (request.getParameter("search") != null) && !request.getParameter("search").isBlank() ) {
			searchTerm = request.getParameter("search");
			NbRowComputer = ComputerService.getInstance().getNbRowsSearch(searchTerm);
			computerList = ComputerService.getInstance().findAllPaginateSearchLike(searchTerm, pageIterator * pageSize, pageSize);
			request.setAttribute("search", searchTerm);
			request.setAttribute("NbRowComputer", NbRowComputer);
		} else {
			computerList = ComputerService.getInstance().getAllPaginateComput(pageIterator * pageSize, pageSize);
		}
	
		if(request.getParameter("order") != null) {
			computerList = ComputerService.getInstance().findAllPaginateAlphabeticOrder(pageIterator * pageSize, pageSize);
			request.setAttribute("order", "checked");
		}
		
		computerList.stream().forEach(
				computer -> computerDTOList.add(ComputerMapper.getInstance().fromComputerToComputerDTO(computer)));


		request.setAttribute("pageIterator", pageIterator);
		request.setAttribute("computerList", computerList);
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
