package fr.excilys.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.service.ComputerService;

@RestController
@RequestMapping("/computers")
public class ComputerRestController {
	
	List<Computer> listComputers = new ArrayList<>();
	
	private ComputerService computerService;
	private ComputerMapper computerMapper;

	public ComputerRestController(ComputerService computerService, ComputerMapper computerMapper) {
		this.computerService = computerService;
		this.computerMapper = computerMapper;
	}
	
	@GetMapping
	public List<Computer> getAllComput() throws DatabaseDAOException{
		listComputers = computerService.getAllComput();

		return listComputers;

	}
	
	@GetMapping("/{ID}")
	public Computer findByID(@PathVariable Integer ID) throws DatabaseDAOException {

		Optional<Computer> Optionalcomputer = Optional.empty();		
		Computer computer = computerService.findByID(ID).get();
		
		return computer;
	}
	
	@GetMapping("/{ID}/name")
	public String findComputerNameById(@PathVariable Integer ID) throws DatabaseDAOException {

		Optional<Computer> Optionalcomputer = Optional.empty();		
		Computer computer = computerService.findByID(ID).get();
		
		return computer.getName();
	}
	
	@GetMapping("/{ID}/company")
	public Company findComputerCompanyById(@PathVariable Integer ID) throws DatabaseDAOException {

		Optional<Computer> Optionalcomputer = Optional.empty();		
		Computer computer = computerService.findByID(ID).get();
		
		return computer.getCompany();
	}
	
	
	@PostMapping
	@Transactional
	public void createComputer(@RequestBody ComputerDTO computerDTO) throws DatabaseDAOException {

		System.out.println(computerDTO);
		Computer computerToCreate = computerMapper.fromComputerDTOToComputer(computerDTO);
		computerService.add(computerToCreate);

	}
	
	
	

}
