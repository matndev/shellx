/***
 * 
 * Application main class
 * 
 * ----------------------
 * 
 * 
 * ### Auto-configuration
 * 
 * Spring Boot auto-configuration attempts to automatically configure your Spring application based on the jar dependencies 
 * that you have added. [...]
 * You need to opt-in to auto-configuration by adding the @EnableAutoConfiguration or @SpringBootApplication annotations to 
 * one of your @Configuration classes.
 * You should only ever add one @SpringBootApplication or @EnableAutoConfiguration annotation. 
 * We generally recommend that you add one or the other to your primary @Configuration class only. 
 * 
 * A single @SpringBootApplication annotation can be used to enable those three features, that is:
 * 
 * @EnableAutoConfiguration: enable Spring Boot’s auto-configuration mechanism
 * @ComponentScan: enable @Component scan on the package where the application is located (see the best practices)
 * @Configuration: allow to register extra beans in the context or import additional configuration classes
 * 
 * 
 * ### Link all configurations classes to this root class:
 * 
 * You need not put all your @Configuration into a single class. 
 * The @Import annotation can be used to import additional configuration classes. 
 * Alternatively, you can use @ComponentScan to automatically pick up all Spring components, including @Configuration classes
 * 
 * 
 * ### Spring Beans and Dependency Injection
 * 
 * If you structure your code as suggested above (locating your application class in a root package), 
 * you can add @ComponentScan without any arguments. 
 * All of your application components (@Component, @Service, @Repository, @Controller etc.) are automatically registered as Spring Beans.
 * 
 * 
 * ----------------------------------
 * 
 * https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html
 * 
 */

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
