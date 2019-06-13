package app.shellx.initializer;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import app.shellx.model.Authority;
import app.shellx.model.Role;
import app.shellx.service.AuthorityService;
import app.shellx.service.RoleService;

@Component
public class ShellxInitializer implements ApplicationRunner {
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private AuthorityService authorityService;
	
	public ShellxInitializer() {

	}

    public void run(ApplicationArguments args) {
		Set<Role> roles = new HashSet<Role>();
		Set<Authority> authorities = new HashSet<Authority>();
		
		roles.add(new Role("ROLE_USER"));
		roles.add(new Role("ROLE_ADMIN"));
		roles.add(new Role("ROLE_MODERATOR"));
		
		authorities.add(new Authority("READ_ACCESS"));
		authorities.add(new Authority("WRITE_ACCESS"));
		authorities.add(new Authority("DELETE_ACCESS"));
		
		for (Role role : roles) {
			if (role.getRole().equals("ROLE_ADMIN")) {
				System.out.println("ROLE_ADMIN found");
				role.setAuthorities(authorities);
			}
		}
		
		roleService.addAll(roles);
		
		authorityService.addAll(authorities);
    }	
	
}
