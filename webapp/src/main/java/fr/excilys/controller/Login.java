package fr.excilys.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Login {

//	@GetMapping(value = "/login")
//	public ModelAndView loginPage() {
//		return new ModelAndView("login");
//	}
//
//	@RequestMapping(value = "/403", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView accessDenied(Principal principal) {
//
//        ModelAndView modelAndView = new ModelAndView("403");
//        if (principal != null) {
//            modelAndView.addObject("message", "Hi " + principal.getName()
//                    + ". You do not have permission to access this page!");
//        } else {
//            modelAndView.addObject("message","Access denied!");
//        }
//        return modelAndView;
//    }
	
	
}