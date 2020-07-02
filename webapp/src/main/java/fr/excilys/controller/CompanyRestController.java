package fr.excilys.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	//Correspond To "http://localhost:8080/cdb-computer-database/companys?page=1&size=10"
		@GetMapping(params = {"page","size"})
		public ResponseEntity<List<CompanyDTO>> getCompanyPaginated(RestControllerParameter restControllerParameter){
			
			return  new ResponseEntity<>(companyService.getAllPaginateCompany(restControllerParameter.getPage()
					,restControllerParameter.getSize()),HttpStatus.OK);

		}
	
	@DeleteMapping("/{ID}")
	public ResponseEntity<Integer> deleteCompany(@PathVariable Integer ID) throws DatabaseDAOException {
		companyService.deleteCompany(ID);
		return new ResponseEntity<>(ID,HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<CompanyDTO> createComputer(@RequestBody CompanyDTO companyDTO) {
		companyService.add(companyDTO);
		return new ResponseEntity<>(companyDTO,HttpStatus.OK);
	}

}
