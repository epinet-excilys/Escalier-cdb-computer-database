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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.excilys.dto.CompanyDTO;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.dto.RestControllerParameter;
import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.service.CompanyService;

@RestController
@CrossOrigin(origins="*", allowedHeaders ="*")
@RequestMapping("/companies")
public class CompanyRestController {

	private CompanyService companyService;

	public CompanyRestController(CompanyService companyService) {
		this.companyService = companyService;
	}

	@GetMapping
	public ResponseEntity<List<CompanyDTO>> getAllComput() {

		try {
			return new ResponseEntity<>(companyService.getAllCompanyDTO(), HttpStatus.OK);
		} catch (DatabaseDAOException databaseDAOException) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("ExceptionError", databaseDAOException.getMessage());
			return new ResponseEntity<>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/{ID}")
	public ResponseEntity<CompanyDTO> findByID(@PathVariable Integer ID) {

		try {
			if (companyService.findByIDDTO(ID).isEmpty()) {
				return new ResponseEntity<>(new CompanyDTO(), HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(companyService.findByIDDTO(ID).get(), HttpStatus.OK);
			}
		} catch (DatabaseDAOException databaseDAOException) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("ExceptionError", databaseDAOException.getMessage());
			return new ResponseEntity<>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// Correspond To
	// "http://localhost:8080/cdb-computer-database/companys?page=1&size=10"
	@GetMapping(params = { "page", "size" })
	public ResponseEntity<List<CompanyDTO>> getCompanyPaginated(RestControllerParameter restControllerParameter) {

		try {

			return new ResponseEntity<>(companyService.getAllPaginateCompany(restControllerParameter.getPage(),
					restControllerParameter.getSize()), HttpStatus.OK);

		} catch (DatabaseDAOException databaseDAOException) {

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("ExceptionError", databaseDAOException.getMessage());
			return new ResponseEntity<>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
	

	// Correspond To
	// "http://localhost:8080/cdb-computer-database/companies?search=inch&page=1&size=10"
	@GetMapping(params = { "search", "page", "size" })
	public ResponseEntity<List<CompanyDTO>> getCompanySearchPaginated(RestControllerParameter restControllerParameter) {

		try {

			return new ResponseEntity<>(companyService.findAllPaginateSearchLike(restControllerParameter.getSearch(),
					restControllerParameter.getPage(), restControllerParameter.getSize()), HttpStatus.OK);

		} catch (DatabaseDAOException databaseDAOException) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("ExceptionError", databaseDAOException.getMessage());
			return new ResponseEntity<>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@DeleteMapping("/{ID}")
	public ResponseEntity<Integer> deleteCompany(@PathVariable Integer ID) {

		try {
			companyService.deleteCompany(ID);
			return new ResponseEntity<>(ID, HttpStatus.OK);
		} catch (DatabaseDAOException databaseDAOException) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("ExceptionError", databaseDAOException.getMessage());
			return new ResponseEntity<>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<CompanyDTO> createComputer(@RequestBody CompanyDTO companyDTO) {

		try {
			companyService.add(companyDTO);
			return new ResponseEntity<>(companyDTO, HttpStatus.OK);

		} catch (DatabaseDAOException databaseDAOException) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("ExceptionError", databaseDAOException.getMessage());
			return new ResponseEntity<>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
