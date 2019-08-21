package app.shellx.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
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
        } catch (JwtException e) {
        	
        	String message;
        	if(e.getMessage() == "Token expired") { 
        		message = "token-expired";
        	}
        	else {
        		message = "access-denied";
        	}
            
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(convertObjectToJson(message));
    }
}

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

}