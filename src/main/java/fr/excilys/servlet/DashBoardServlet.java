package fr.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Computer;
import fr.excilys.service.ComputerService;


@WebServlet(name = "DashBoardServlet", urlPatterns = "/DashBoard")
public class DashBoardServlet extends HttpServlet {
	private int pageIterator = 0;
	private int pageSize = 20;
	private double maxPage = 0.00;
	private int NbRowComputer = 0;
	
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
			computerList = ComputerService.getInstance().getAllPaginateComput(pageIterator * pageSize, pageSize);
		} else {
			pageIterator = 0;
			computerList = ComputerService.getInstance().getAllPaginateComput(0, 20);
		}

		computerList.stream().forEach(
				computer -> computerDTOList.add(ComputerMapper.getInstance().fromComputerToComputerDTO(computer)));

		request.setAttribute("maxPage", maxPage);
		request.setAttribute("pageIterator", pageIterator);
		request.setAttribute("NbRowComputer", NbRowComputer);
		request.setAttribute("computerList", computerList);
		request.getRequestDispatcher(DASHBOARD).forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
