package app.shellx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/login")
public class LoginController {
	
	/*@Autowired
	LoginService loginService;
	
	@RequestMapping("/{username}/{password}")
	public void login(@PathVariable("username") String username, @PathVariable("password") String password) {
		this.loginService.login(username, password);
	}*/
}
