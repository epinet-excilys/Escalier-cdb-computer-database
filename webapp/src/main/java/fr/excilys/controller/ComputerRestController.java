package fr.excilys.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.model.Computer;
import fr.excilys.service.ComputerService;

@RestController
@RequestMapping("/computers")
public class ComputerRestController {
	
	List<Computer> listComputers = new ArrayList<>();
	
	private ComputerService computerService;

	public ComputerRestController(ComputerService computerService) {
		this.computerService = computerService;
	}
	
	@GetMapping
	public ResponseEntity<List<Computer>> getAllComput() throws DatabaseDAOException{
		listComputers = computerService.getAllComput();

		return new ResponseEntity<>(listComputers,HttpStatus.OK);

	}
	
	@GetMapping("/{ID}")
	public ResponseEntity<Computer> findByID(@PathVariable Integer ID) throws DatabaseDAOException {

		Optional<Computer> Optionalcomputer = Optional.empty();		
		Computer computer = computerService.findByID(ID).get();
		
		return new ResponseEntity<>(computer,HttpStatus.OK);
	}
	
	@GetMapping("/{ID}/name")
	public ResponseEntity<String> findComputerNameById(@PathVariable Integer ID) throws DatabaseDAOException {

		Optional<Computer> Optionalcomputer = Optional.empty();		
		Computer computer = computerService.findByID(ID).get();
		return new ResponseEntity<>(computer.getName(),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ComputerDTO> createComputer(@RequestBody ComputerDTO computerDTO) throws DatabaseDAOException {
		computerService.addDTO(computerDTO);
		return new ResponseEntity<>(computerDTO,HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<Integer> deleteComputer(@PathVariable Integer ID) throws DatabaseDAOException {
		computerService.delete(ID);
		return new ResponseEntity<>(ID,HttpStatus.OK);
	}
	
	
	
	
	
	

}
