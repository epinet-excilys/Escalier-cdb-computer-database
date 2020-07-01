package fr.excilys.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.model.Users;
import fr.excilys.service.SecurityUserServiceCDB;

@RestController
@RequestMapping("/users")
public class UserRestController {
	
	private SecurityUserServiceCDB securityUserServiceCDB;
	
	public UserRestController(SecurityUserServiceCDB securityUserServiceCDB) {
		this.securityUserServiceCDB = securityUserServiceCDB;
	}



	
//	@GetMapping("/{username}")
//	public List<Users> getAllUsers() throws DatabaseDAOException{
//		
//		return securityUserServiceCDB.getAllUsers();
//
//	}
	
	

}
