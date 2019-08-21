/*
 * 		### WebSecurityConfig
 * 
 * Etendre la classe WebSecurityConfigurerAdapter en utilisant l'annotation EnableWebSecurity
 * permet de customiser la classe de base WebSecurity et d'utiliser cette config automatiquement
 * 
 * 
 * 		### Fonctionnement
 * 
 * Spring parcourt tous les AuthenticationProvider en essayant de trouver celui qui correspond le
 * mieux pour authentifier le subject côté client.
 * S'il en trouve un qui match, il utilise sa méthode authenticate()
 * 
 * 
 * FILTER CHAIN
 * 
 * Chaque objet http utilisé dans configure() créé une filterchain spécifique
 * Antmatcher() 		définit l'entrypoint de la filterchain (par défaut "/**" donc toutes les URI)
 * authorizeRequest() 	déclare que les URI suivantes (avec antmatcher()) sont des endpoints restrictifs 
 * 
 * On peut connaitre la liste et l'ordre des filtres de la FilterChain au travers de la classe :
 * @see FilterComparator
 * https://github.com/spring-projects/spring-security/blob/master/config/src/main/java/org/springframework/
 * security/config/annotation/web/builders/FilterComparator.java
 * 
 * Le paramètre form.login de l'objet "http" dans configure() crée un filtre UsernamePasswordAuthenticationFilter
 * Sans cela, il n'y aurait pas de filtre d'authentification
 * 
 * 		### configure(AuthenticationManagerBuilder auth)
 * 
 * Ajoute un AuthenticationProvider à la liste des AuthenticationProvider
 * Cette liste est parcourue par le ProviderManager qui implémente l'AuthenticationManager
 * Le ProviderManager fait appel au travers de sa propre méthode authenticate() aux méthodes authenticate()
 * des AuthenticationProvider. Le premier AuthenticationProvider qui arrive à authentifier fait sortir de la boucle
 * et annule les test des AuthenticationProvider suivants.
 * 
 * 
 * 
 * A AJOUTER AUTRE PART
 * 
 * @Configuration vs @Component
 * Si une classe est annotée avec @Configuration, alors toutes les méthodes annotées @Bean à l'intérieur
 * renverront un singleton de la méthode (proxy par CGLIB).
 * Si elle est annotée @Component, une nouvelle instance de la méthode annotée @Bean sera renvoyée.
 * 
*/

package app.shellx.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import app.shellx.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtTokenFilter jwtTokenFilter;
	
	@Autowired
	private CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter; 
	
	@Autowired
	private ExceptionTokenVerificationHandlerFilter exceptionTokenVerificationHandlerFilter;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic().disable()
			.addFilterBefore(exceptionTokenVerificationHandlerFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
			.sessionManagement()
	        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
			.cors().and()
			.csrf().disable()
			.authorizeRequests() // .antMatchers("/**")
				.antMatchers("/login/**", "/register/**").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")		
				.anyRequest().authenticated()
				.and()
			//.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
			.addFilterAt(customUsernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)	
			/*.formLogin()
				.loginPage("http://localhost:4200/login")//.failureUrl("/login-error")
				.loginProcessingUrl("/login") 
            	.usernameParameter("email")
				.successHandler(customAuthenticationSuccessHandler)
				.and()*/
			.logout() 
				.permitAll();
	}    
    
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider()); // AuthenticationProvider inserted into ProviderManager
	}

	
    @Bean
    public CustomDaoAuthenticationProvider authenticationProvider() {
        CustomDaoAuthenticationProvider authenticationProvider = new CustomDaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }
    
    // Return the AuthenticationManager used by the configure(AuthenticationManagerBuilder auth) method
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
    	System.out.println("Configuration of authenticationManagerBean");
    	return super.authenticationManagerBean();
    }
    
//    @Bean
//    public FilterRegistrationBean<CustomUsernamePasswordAuthenticationFilter> registration(CustomUsernamePasswordAuthenticationFilter filter) {
//    	FilterRegistrationBean<CustomUsernamePasswordAuthenticationFilter> registration = new FilterRegistrationBean<CustomUsernamePasswordAuthenticationFilter>(filter);
//    	registration.setEnabled(false);
//    	return registration;
//    }
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebConfig() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                .allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
                        		"Access-Control-Request-Headers", "Authorization", "Cache-Control",
                        		"Access-Control-Allow-Origin")
                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "set-cookie")
                .allowCredentials(true).maxAge(3600);
            }
        };
    }
}