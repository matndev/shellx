package app.shellx.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.shellx.dto.MessageDto;
import app.shellx.dto.RoomUserDto;
import app.shellx.dto.UserDto;
import app.shellx.model.Authority;
import app.shellx.model.Message;
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
	
	
	// Load initial users in the room
	@GetMapping("/room/{id}")
	public Set<UserDto> getUsers(@PathVariable long id) { // Set<User>
		return this.userService.getUsers(id);
	}
	
	@MessageMapping("/user/add/{id}")
	@SendTo("/topic/user/subscribe/{id}")
	public UserDto add(@DestinationVariable long id, UserDto userDto) {
		System.out.println("### LOG : User sent from controller to UserService");
		System.out.println(userDto.toString());
		return this.userService.add(id, userDto);
	}		
	
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
