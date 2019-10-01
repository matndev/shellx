package app.shellx.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import app.shellx.annotation.EmailExistsException;
import app.shellx.model.User;
import app.shellx.service.UserService;

@RestController
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private UserService userService;
	
	//@Autowired
	//private User userRegistered;
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@PostMapping(path="/", consumes="application/json")
	public void registerUserAccount(@RequestBody @Valid User account) {
		System.out.println("### LOG : User sent from controller to UserService");
		String password = passwordEncoder.encode(account.getPassword());
		account.setPassword(password);
		
		//User userRegistered = null;
	    try {
	        this.userService.registerNewUserAccount(account);
	    } catch (EmailExistsException e) {
	        //return null;
	    	System.out.println("Erreur : email exists");
	    }
	}
	
	/*
	@GetMapping(path="/")
    public String showRegistrationForm(final HttpServletRequest request, final Model model) {
        final UserDto accountDto = new UserDto();
        model.addAttribute("user", accountDto);
        return "registration";
	}
	*/
	
	/*
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView registerUserAccount(
		@ModelAttribute("user") @Valid UserDto accountDto, 
		BindingResult result, WebRequest request, Errors errors) {
	     
			System.out.println("username : "+accountDto.getUsername()+", email : "+accountDto.getEmail()+", password : "+accountDto.getPassword()+", matchingPassword : "+accountDto.getMatchingPassword());
		
		    User registered = new User();
		    if (!result.hasErrors()) {
		        registered = createUserAccount(accountDto, result);
		    }
		    if (registered == null) {
		        result.rejectValue("email", "message.regError");
		    }
		    if (result.hasErrors()) {
		        return new ModelAndView("registration", "user", accountDto);
		    } 
		    else {
		        return new ModelAndView("successRegister", "user", accountDto);
		    }
	}
	private User createUserAccount(UserDto accountDto, BindingResult result) {
	    User registered = null;
	    try {
	        registered = this.userService.registerNewUserAccount(accountDto);
	    } catch (EmailExistsException e) {
	        return null;
	    }
	    return registered;
	}
	*/
}
