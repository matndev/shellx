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
 * Cette liste est parcourue par l'AuthenticationManager
 * 
*/

package app.shellx.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import app.shellx.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserService userService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

//	@Autowired
//	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenFilter jwtTokenFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic()
				.and()
			.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.and()
			.sessionManagement()
	        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
			.cors().and()
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/css/**", "/login/**", "/register/**").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")		
				.anyRequest().authenticated()
				.and()
			//.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
			.formLogin()
            	.usernameParameter("email")
				//.loginPage("http://localhost:4200/login").failureUrl("/login-error")	
				.and()
			.logout() 
				.permitAll();
		http
			.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
    @Bean
    public CustomDaoAuthenticationProvider authenticationProvider() {
        CustomDaoAuthenticationProvider authenticationProvider = new CustomDaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }
    
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
                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                .allowCredentials(true).maxAge(3600);
            }
        };
    }
}