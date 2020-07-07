package fr.excilys.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.dto.RestControllerParameter;
import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.service.ComputerService;

@RestController
@CrossOrigin(origins="*" ,allowedHeaders ="*")
@RequestMapping("/computers")
public class ComputerRestController {

	private ComputerService computerService;

	public ComputerRestController(ComputerService computerService) {
		this.computerService = computerService;
	}

	@GetMapping
	public ResponseEntity<List<ComputerDTO>> getAllComput() {

		try {
			return new ResponseEntity<>(computerService.getAllComput(), HttpStatus.OK);
		} catch (DatabaseDAOException databaseDAOException) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("ExceptionError", databaseDAOException.getMessage());
			return new ResponseEntity<>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/numbers")
	public ResponseEntity<Integer> getNBComput() {

		try {
			return new ResponseEntity<>(computerService.getNbRows(), HttpStatus.OK);

		} catch (DatabaseDAOException databaseDAOException) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("ExceptionError", databaseDAOException.getMessage());
			return new ResponseEntity<>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(params = { "search" })
	public ResponseEntity<Integer> getNBComput(RestControllerParameter restControllerParameter) {

		try {
			return new ResponseEntity<>(computerService.getNbRowsSearch(restControllerParameter.getSearch()),
					HttpStatus.OK);

		} catch (DatabaseDAOException databaseDAOException) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("ExceptionError", databaseDAOException.getMessage());
			return new ResponseEntity<>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Correspond To
	// "http://localhost:8080/cdb-computer-database/computers?page=1&size=10"
	@GetMapping(params = { "page", "size" })
	public ResponseEntity<List<ComputerDTO>> getComputPaginated(RestControllerParameter restControllerParameter) {

		try {
			return new ResponseEntity<>(computerService.getAllPaginateComput(restControllerParameter.getPage(),
					restControllerParameter.getSize()), HttpStatus.OK);

		} catch (DatabaseDAOException databaseDAOException) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("ExceptionError", databaseDAOException.getMessage());
			return new ResponseEntity<>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Correspond To
	// "http://localhost:8080/cdb-computer-database/computers?search=inch&page=1&size=10"
	@GetMapping(params = { "search", "page", "size" })
	public ResponseEntity<List<ComputerDTO>> getComputSearchPaginated(RestControllerParameter restControllerParameter) {

		try {

			return new ResponseEntity<>(computerService.findAllPaginateSearchLike(restControllerParameter.getSearch(),
					restControllerParameter.getPage(), restControllerParameter.getSize()), HttpStatus.OK);

		} catch (DatabaseDAOException databaseDAOException) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("ExceptionError", databaseDAOException.getMessage());
			return new ResponseEntity<>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Correspond To
	// "http://localhost:8080/cdb-computer-database/computers?page=1&size=10&orderBy=name"
	@GetMapping(params = { "page", "size", "orderBy" })
	public ResponseEntity<List<ComputerDTO>> getComputOrderByPaginated(
			RestControllerParameter restControllerParameter) {

		try {

			return new ResponseEntity<>(computerService.findAllPaginateOrder(restControllerParameter.getPage(),
					restControllerParameter.getSize(), restControllerParameter.getOrderBy()), HttpStatus.OK);
		} catch (DatabaseDAOException databaseDAOException) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("ExceptionError", databaseDAOException.getMessage());
			return new ResponseEntity<>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Correspond to 
	// http://localhost:8080/cdb-computer-database/computers?search=mac&page=1&size=10&orderBy=name
	@GetMapping(params = {"search", "page", "size", "orderBy" })
	public ResponseEntity<List<ComputerDTO>> getComputOrderByPaginatedAndSearch(
			RestControllerParameter restControllerParameter) {

		try {

			return new ResponseEntity<>(computerService.findAllPaginateOrderAndSearch(
					restControllerParameter.getSearch(),
					restControllerParameter.getPage(),
					restControllerParameter.getSize(), restControllerParameter.getOrderBy()), HttpStatus.OK);
		} catch (DatabaseDAOException databaseDAOException) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("ExceptionError", databaseDAOException.getMessage());
			return new ResponseEntity<>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{ID}")
	public ResponseEntity<ComputerDTO> findByID(@PathVariable Integer ID) {

		try {

			if (computerService.findByID(ID).isEmpty()) {
				return new ResponseEntity<>(new ComputerDTO(), HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(computerService.findByID(ID).get(), HttpStatus.OK);
			}

		} catch (DatabaseDAOException databaseDAOException) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("ExceptionError", databaseDAOException.getMessage());
			return new ResponseEntity<>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/{ID}/name")
	public ResponseEntity<String> findComputerNameById(@PathVariable Integer ID) {

		try {
			return new ResponseEntity<>(computerService.findByID(ID).get().getName(), HttpStatus.OK);
		} catch (DatabaseDAOException databaseDAOException) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("ExceptionError", databaseDAOException.getMessage());
			return new ResponseEntity<>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<ComputerDTO> createComputer(@RequestBody ComputerDTO computerDTO) {

		try {
			computerService.addDTO(computerDTO);
			return new ResponseEntity<>(computerDTO, HttpStatus.CREATED);
		} catch (DatabaseDAOException databaseDAOException) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("ExceptionError", databaseDAOException.getMessage());
			return new ResponseEntity<>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{ID}")
	public ResponseEntity<Integer> deleteComputer(@PathVariable Integer ID) {

		try {
			computerService.delete(ID);
			return new ResponseEntity<>(ID, HttpStatus.ACCEPTED);
		} catch (DatabaseDAOException databaseDAOException) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("ExceptionError", databaseDAOException.getMessage());
			return new ResponseEntity<>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{ID}")
	public ResponseEntity<ComputerDTO> updateComputer(@RequestBody ComputerDTO computerDTO) {

		try {
			computerService.updateDTO(computerDTO);
			return new ResponseEntity<>(computerDTO, HttpStatus.ACCEPTED);
		} catch (DatabaseDAOException databaseDAOException) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("ExceptionError", databaseDAOException.getMessage());
			return new ResponseEntity<>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
