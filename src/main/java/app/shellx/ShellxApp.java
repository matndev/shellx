package app.shellx;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import app.shellx.initializer.ShellxInitializer;
import app.shellx.model.Authority;
import app.shellx.model.Role;
import app.shellx.service.AuthorityService;
import app.shellx.service.RoleService;

@SpringBootApplication
public class ShellxApp {
	
	@Autowired
	private ShellxInitializer shllxinit;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			SpringApplication.run(ShellxApp.class, args);
	}
	
}
