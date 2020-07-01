package fr.excilys.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.dto.UserDTO;
import fr.excilys.service.SecurityUserServiceCDB;

@Controller
@RequestMapping(value = "/addUser")
public class AddUser {

	@Autowired
    private final SecurityUserServiceCDB securityUserServiceCDB;
	
	private final String SuccessMessage = "successMessage";

    public AddUser(SecurityUserServiceCDB securityUserServiceCDB) {
        this.securityUserServiceCDB = securityUserServiceCDB;
    }

    @GetMapping
    public ModelAndView register() {
        return new ModelAndView("addUser");
    }

    @PostMapping
    @Transactional
    public ModelAndView register(UserDTO userDTO) {
        ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");

        securityUserServiceCDB.addUser(userDTO);

        setMessage("user Added", SuccessMessage, modelAndView);

        return modelAndView;
    }

	public void setMessage(String errorMessage, String messageTitle, ModelAndView modelAndView) {
		if (( errorMessage != null ) && (!errorMessage.isBlank())) {
			modelAndView.addObject(messageTitle, errorMessage);
		}
	}
}
