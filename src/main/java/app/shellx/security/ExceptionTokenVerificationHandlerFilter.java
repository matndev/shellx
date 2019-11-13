package app.shellx.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.shellx.utils.JsonTools;
import io.jsonwebtoken.JwtException;

@Order(1)
@Component
public class ExceptionTokenVerificationHandlerFilter extends OncePerRequestFilter {
	
	private Logger log = LogManager.getLogger(ExceptionTokenVerificationHandlerFilter.class.getName());
	
	@Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
        	log.info("Filter: ExceptionTokenVerificationHandlerFilter (doFilterInternal)");
            filterChain.doFilter(request, response);
        } catch (UserAlreadyLoggedException|InvalidJwtAuthenticationException|JwtException e) { // Add exceptions here and implements new custom exception class which extends RuntimeException
        	
        	String message;
        	switch(e.getMessage()) {
	        	case "Token expired":
	        		message = "token-expired";
	        	break;
	        	case "User already logged":
	        		message = "user-already-logged";
	        	break;
	        	case "Cookie not found":
	        		message = "cookie-not-found";
	        	break;
	        	default:
	        		message = "access-denied";
        	}
        	
        	if (!message.equals("user-already-logged")) {
	        	// if token is expired we delete the old cookie by setting 0 value inside it
				final Cookie newCookie = new Cookie("access_token", "");
				newCookie.setSecure(false); // a mettre en commentaire si ca marche pas
				newCookie.setHttpOnly(true);
				newCookie.setMaxAge(0);
				newCookie.setPath("/");
				response.addCookie(newCookie);
        	}
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write(JsonTools.convertObjectToJson(message));
	    }
	}

//    private String convertObjectToJson(Object object) throws JsonProcessingException {
//        if (object == null) {
//            return null;
//        }
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.writeValueAsString(object);
//    }

}