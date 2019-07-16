package app.shellx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.shellx.dto.UserDto;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/login")
public class LoginController {
	
	/*@Autowired
	LoginService loginService;*/
	

	@PostMapping(path="/", consumes="application/json", produces="application/json")
	public void login(@RequestBody UserDto userDto) {
		System.out.println(userDto.getEmail());
		System.out.println(userDto.getPassword());
		System.out.println(userDto.getMatchingPassword());
	}
}
