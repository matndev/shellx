/***
 * 
 * ### ShellxInitializer
 * 
 * Class used to setup data structure and values before starting the application and after the bootstrap of the Spring application
 * context.
 * 
 * ### ApplicationRunner
 * 
 * Similar to CommandLineRunner, Spring boot also provides an ApplicationRunner interface with a run() method to be invoked 
 * at application startup. However, instead of raw String arguments passed to the callback method, we have an instance of the 
 * ApplicationArguments class.
 * The ApplicationArguments interface has methods to get argument values that are options and plain argument values. 
 * An argument that is prefixed with – – is an option argument.
 * 
 * ### Others ways to config beans or properties after Spring application context initialized:
 * 
 * The @PostConstruct Annotation
 * The InitializingBean Interface
 * An ApplicationListener
 * The @Bean Initmethod Attribute
 * Constructor Injection
 * Spring Boot CommandLineRunner
 * 
 * -------------------------------
 * 
 * https://www.baeldung.com/running-setup-logic-on-startup-in-spring
 * 
 */

package app.shellx.initializer;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import app.shellx.annotation.EmailExistsException;
import app.shellx.model.Authority;
import app.shellx.model.Role;
import app.shellx.model.User;
import app.shellx.service.AuthorityService;
import app.shellx.service.RoleService;
import app.shellx.service.UserService;

@Component
public class ShellxInitializer implements ApplicationRunner {
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private UserService userService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String hibernateInitializationMode;
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public ShellxInitializer() {

	}

    public void run(ApplicationArguments args) {
    	if (hibernateInitializationMode.equals("create") || hibernateInitializationMode.equals("create-drop")) {
    		
    			// AJOUT DES ROLES ET DROITS
    		
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
						role.setAuthorities(authorities);
					}
				}
				
				roleService.addAll(roles);
				
				authorityService.addAll(authorities);
				
				
				// AJOUT DES UTILISATEURS
				
				User account = new User("pierrho", "eboyfr@gmail.com", "12345678", true, null);
				String password = passwordEncoder.encode(account.getPassword());
				account.setPassword(password);
				
				User account2 = new User("cucu", "pierrematn@gmail.com", "12345678", true, null);
				String password2 = passwordEncoder.encode(account2.getPassword());
				account2.setPassword(password2);
				
				try {
					this.userService.registerNewUserAccount(account);
					this.userService.registerNewUserAccount(account2);
				} catch (EmailExistsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// AJOUT DES ROOMS
				
				// AJOUT DES MESSAGES
				
    	}
    }	
	
}
