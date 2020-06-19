package fr.excilys.mapper;

import fr.excilys.dto.UserDTO;
import fr.excilys.model.UserRole;
import fr.excilys.model.Users;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserMapper {

	public static Users fromUserDTOtoUser(UserDTO userDTO) {

		String passwordHash = new BCryptPasswordEncoder().encode(userDTO.getPassword());
		Users user = new Users(userDTO.getUsername(), passwordHash, true);
		UserRole userRole = new UserRole(user, userDTO.getRole());
		Set<UserRole> userRoleSet = new HashSet<>();

		userRoleSet.add(userRole);
		user.setUserRole(userRoleSet);

		return user;

	}


}
