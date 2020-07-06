package fr.excilys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.excilys.dto.UserDTO;
import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.service.SecurityUserServiceCDB;

@RestController
@CrossOrigin(origins="**")
@RequestMapping("/users")
public class UserRestController {
	
	
	@Autowired
    private SecurityUserServiceCDB securityUserServiceCDB;

	@PostMapping
	public ResponseEntity<UserDTO> createComputer(@RequestBody UserDTO userDTO) {

		try {
			System.out.println(userDTO.toString());
			securityUserServiceCDB.addUser(userDTO);
			return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
		} catch (DatabaseDAOException databaseDAOException) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("ExceptionError", databaseDAOException.getMessage());
			return new ResponseEntity<>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	//TODO A virer avant CR
	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllComput() {

		try {
			return new ResponseEntity<>(securityUserServiceCDB.getAll(), HttpStatus.OK);
		} catch (DatabaseDAOException databaseDAOException) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("ExceptionError", databaseDAOException.getMessage());
			return new ResponseEntity<>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	
}
