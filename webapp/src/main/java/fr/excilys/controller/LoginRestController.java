package fr.excilys.controller;

import java.util.Objects;

import javax.naming.AuthenticationException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.excilys.dto.UserDTO;
import fr.excilys.jwtToken.JwtTokenUtil;
import fr.excilys.service.SecurityUserServiceCDB;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins="*" , allowedHeaders ="*")
public class LoginRestController {

	private JwtTokenUtil jwtTokenUtil;
	private SecurityUserServiceCDB usersService;

	public LoginRestController(SecurityUserServiceCDB usersService,
			JwtTokenUtil jwtTokenUtil) {
		this.usersService = usersService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@PostMapping
	public ResponseEntity<?> login(@RequestBody UserDTO authentificationRequest) throws AuthenticationException {
		
		authenticate(authentificationRequest.getUsername(), authentificationRequest.getPassword());
		final UserDetails userDetails = usersService.loadUserByUsername(authentificationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(token);
	}
	

	private void authenticate(String username, String password) throws AuthenticationException {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);


		try {
			 UserDetails userDetails = usersService.loadUserByUsername(username);
			 if(!password.equals(userDetails.getPassword())) {
				 throw new AuthenticationException("INVALID_CREDENTIALS" );
			 }
		} catch (UsernameNotFoundException usernameNotFoundException) {
			throw new UsernameNotFoundException("USER_DISABLED" );
		}
	}

}
