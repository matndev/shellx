package app.shellx;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import app.shellx.initializer.ShellxInitializer;


@SpringBootApplication
public class ShellxApp {
	
	@Autowired
	private ShellxInitializer shllxinit;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			SpringApplication.run(ShellxApp.class, args);
	}
	
}
