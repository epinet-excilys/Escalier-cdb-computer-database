package fr.excilys.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
