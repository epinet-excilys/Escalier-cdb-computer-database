package fr.excilys.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.dto.RestControllerParameter;
import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.service.ComputerService;

@RestController
@RequestMapping("/computers")
public class ComputerRestController {
	
	
	
	private ComputerService computerService;

	public ComputerRestController(ComputerService computerService) {
		this.computerService = computerService;
	}
	
	@GetMapping
	public ResponseEntity<List<ComputerDTO>> getAllComput(){
		
		return new ResponseEntity<>(computerService.getAllComput(),HttpStatus.OK);

	}
	
	//Correspond To "http://localhost:8080/cdb-computer-database/computers?page=1&size=10"
	@GetMapping(params = {"page","size"})
	public ResponseEntity<List<ComputerDTO>> getComputPaginated(RestControllerParameter restControllerParameter){
		
		return new ResponseEntity<>(computerService.getAllPaginateComput(restControllerParameter.getPage()
				,restControllerParameter.getSize()),HttpStatus.OK);

	}
	
	//Correspond To "http://localhost:8080/cdb-computer-database/computers?search=inch&page=1&size=10"
	@GetMapping(params = {"search","page","size"})
	public ResponseEntity<List<ComputerDTO>> getComputSearchPaginated(RestControllerParameter restControllerParameter){
		
		return new ResponseEntity<>(computerService.findAllPaginateSearchLike(restControllerParameter.getSearch()
				,restControllerParameter.getPage()
				,restControllerParameter.getSize()),HttpStatus.OK);

	}
	
	@GetMapping("/{ID}")
	public ResponseEntity<ComputerDTO> findByID(@PathVariable Integer ID) {
		
		if(computerService.findByID(ID).isEmpty()) {
			return new ResponseEntity<>(new ComputerDTO(),HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(computerService.findByID(ID).get(),HttpStatus.OK);
		}
	
	}
	
	@GetMapping("/{ID}/name")
	public ResponseEntity<String> findComputerNameById(@PathVariable Integer ID) {

		return new ResponseEntity<>(computerService.findByID(ID).get().getName(),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ComputerDTO> createComputer(@RequestBody ComputerDTO computerDTO) {
		computerService.addDTO(computerDTO);
		return new ResponseEntity<>(computerDTO,HttpStatus.OK);
	}
	
	@DeleteMapping("/{ID}")
	public ResponseEntity<Integer> deleteComputer(@PathVariable Integer ID) {
		computerService.delete(ID);
		return new ResponseEntity<>(ID,HttpStatus.OK);
	}
	
	@PutMapping("/{ID}")
	public ResponseEntity<ComputerDTO> updateComputer(@RequestBody ComputerDTO computerDTO) {
		computerService.updateDTO(computerDTO);
		return new ResponseEntity<>(computerDTO,HttpStatus.OK);
	}
	
	
	
	
	
	
	

}
