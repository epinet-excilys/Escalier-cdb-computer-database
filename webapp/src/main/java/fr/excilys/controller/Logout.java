package fr.excilys.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Logout {

	@GetMapping(value = "/logoutSuccessful")
	public ModelAndView loginPage() {
		return new ModelAndView("logoutSuccessful");
	}

}
