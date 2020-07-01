package fr.excilys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Logout {

	@GetMapping(value = "/logoutSuccessful")
	public ModelAndView loginPage() {
		return new ModelAndView("logoutSuccessful");
	}

}
