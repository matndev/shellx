package app.shellx.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "https://localhost:4200", maxAge = 3600)
@RequestMapping("/login2")
public class LoginController {
	
	/*@Autowired
	LoginService loginService;*/
	
	/*@Autowired
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
	}*/
}
