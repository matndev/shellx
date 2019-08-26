package app.shellx.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	private Logger log = LogManager.getLogger(CustomAuthenticationSuccessHandler.class.getName());
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		log.error("#DEBUG : Authentication successed");
		
		// Token doesn't exist so it is a successful authentication by form login
		if (jwtTokenProvider.resolveToken(request) == null) {
			
			System.out.println("JWT Token creation");
			List<String> authorities = authentication.getAuthorities().stream().map(e -> e.getAuthority()).collect(Collectors.toList());
			
			System.out.println("Username : "+authentication.getName());
			authorities.forEach(System.out::println);
			
			String jwtToken = jwtTokenProvider.createToken(authentication.getName(), authorities);
			log.info("Contenu token créé "+jwtToken);
			
			final Cookie cookie = jwtTokenProvider.createCookieForToken(jwtToken);
			response.addCookie(cookie);
			
//			response.setHeader("Authorization", jwtToken);
		}
	}

	
}
