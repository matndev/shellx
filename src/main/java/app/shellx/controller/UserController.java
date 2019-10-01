package app.shellx.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.shellx.model.Authority;
import app.shellx.model.User;
import app.shellx.service.RoleService;
import app.shellx.service.UserService;

@RestController
//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	/*@PostMapping(path="/add", consumes="application/json")
	public void add(@RequestBody UserDto userDto) {
		System.out.println("### LOG : User sent from controller to UserService");
		//this.userService.add(new User("pierrho", "eboyfr@gmail.com", passwordEncoder.encode("123456"), true, "avatar.jpg"), "ROLE_ADMIN"); //LocalDate.now()));
		String password = passwordEncoder.encode(userDto.getPassword());
		userDto.setPassword(password);
		try {
			this.userService.registerNewUserAccount(userDto);
		} catch (EmailExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}*/
	
	@RequestMapping("/delete")
	public void delete() {
		System.out.println("### LOG : Delete all users");
		
		this.userService.delete();
	}
	
	@RequestMapping("/update")
	public void update(User user) {
		this.userService.update(user);
	}
	
	@RequestMapping(value="/find/{username}", method=RequestMethod.GET, produces="application/json")
	public void findByUsername(@PathVariable("username") String username) {
		System.out.println(username);
		User user = this.userService.loadUserByUsername(username);
		System.out.println("Role : "+user.getRoleName());
		Set<Authority> authorities = user.getOnlyAuthorities();
		System.out.println("Authorities : ");
		for (Authority temp : authorities) {
			System.out.println(temp.getAuthority());
		}
		System.out.println("Utilisateur : "+user.getUsername());
	}
	
//	@RequestMapping(value="/login3/{id}", method=RequestMethod.GET, produces="application/json")
//	public UserDto login(@PathVariable("id") long id) {
//		UserDto userDto = this.userService.loadUserById(id);
//		return userDto;
//	}
}
