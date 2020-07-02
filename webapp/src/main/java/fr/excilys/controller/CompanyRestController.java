package fr.excilys.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.excilys.dto.CompanyDTO;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.service.CompanyService;

@RestController
@RequestMapping("/companys")
public class CompanyRestController {
	
	private CompanyService companyService;

	public CompanyRestController(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@GetMapping
	public ResponseEntity<List<CompanyDTO>> getAllComput() throws DatabaseDAOException{
		
		return new ResponseEntity<>(companyService.getAllCompanyDTO(),HttpStatus.OK);

	}
	
	@GetMapping("/{ID}")
	public ResponseEntity<CompanyDTO> findByID(@PathVariable Integer ID) throws DatabaseDAOException {

		return new ResponseEntity<>(companyService.findByIDDTO(ID).get(),HttpStatus.OK);
	}
	
	@DeleteMapping("/{ID}")
	public ResponseEntity<Integer> deleteCompany(@PathVariable Integer ID) throws DatabaseDAOException {
		companyService.deleteCompany(ID);
		return new ResponseEntity<>(ID,HttpStatus.OK);
	}
	

}
