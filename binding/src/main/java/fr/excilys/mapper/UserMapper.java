package fr.excilys.mapper;

import fr.excilys.dto.UserDTO;
import fr.excilys.model.UserRole;
import fr.excilys.model.Users;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

	public Users fromUserDTOtoUser(UserDTO userDTO) {

		String passwordHash = new BCryptPasswordEncoder().encode(userDTO.getPassword());
		Users user = new Users(userDTO.getUsername(), passwordHash);

		return user;

	}
	
	public UserRole fromUserDTOtoUserRole(UserDTO userDTO) {
		UserRole userRole = new UserRole(userDTO.getUsername(), userDTO.getRole());
		
		return userRole;
	}


}
