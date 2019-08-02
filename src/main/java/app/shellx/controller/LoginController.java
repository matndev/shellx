package app.shellx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.shellx.dto.UserDto;
import app.shellx.security.CustomDaoAuthenticationProvider;
import app.shellx.security.JwtTokenProvider;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/login")
public class LoginController {
	
	/*@Autowired
	LoginService loginService;*/
	@Autowired
	CustomDaoAuthenticationProvider authenticationManager; 
	
	@Autowired 
	JwtTokenProvider jwtTokenProvider;

	@PostMapping(path="/", consumes="application/json", produces="application/json")
	public void login(@RequestBody UserDto userDto) {
		System.out.println(userDto.getEmail());
		System.out.println(userDto.getPassword());
        try {
            String email = userDto.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, userDto.getPassword()));
            String token = jwtTokenProvider.createToken(email, this.users.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")).getRoles());            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
	}
}
