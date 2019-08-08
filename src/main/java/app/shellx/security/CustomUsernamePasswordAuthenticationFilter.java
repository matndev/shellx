/*
 * CUSTOM FILTER copied from native class UsernamePasswordAuthenticationFilter
 * 
 * 
 * EXTRACT FROM
 * https://stackoverflow.com/questions/19500332/spring-security-and-json-authentication
 * 
 * UsernamePasswordAuthenticationFilter uses params in the request body instead of json
 * We enable the json format for post request with this custom filter
 * 
*/


package app.shellx.security;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

//@Component
public class CustomUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

	private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
	private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
	private boolean postOnly = true;

	
	public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) { // AuthenticationManager authenticationManager
		super(new AntPathRequestMatcher("/login", "POST"));
		this.setAuthenticationManager(authenticationManager);
		System.out.println("Constructor filter");
	}
	
	
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}

		UsernamePasswordAuthenticationToken authRequest = null;
		try {
			authRequest = this.getUserNamePasswordAuthenticationToken(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		
		System.out.println("AVANT AUTHENTIFICATION : "+authRequest.getPrincipal() + " " + authRequest.getCredentials());
		
		Authentication auth2 = this.getAuthenticationManager().authenticate(authRequest);
		System.out.println("APRES AUTHENTIFICATION : "+auth2.getPrincipal() + " " + auth2.getCredentials());
		return auth2;
	}

	private UsernamePasswordAuthenticationToken getUserNamePasswordAuthenticationToken(HttpServletRequest request)  throws IOException {
	    StringBuffer sb = new StringBuffer();
	    BufferedReader bufferedReader = null;
	    String content = "";
	    AuthReq sr = null;

	    try {
	        bufferedReader =  request.getReader();
	        char[] charBuffer = new char[128];
	        int bytesRead;
	        while ( (bytesRead = bufferedReader.read(charBuffer)) != -1 ) {
	            sb.append(charBuffer, 0, bytesRead);
	        }
	        content = sb.toString();
	        ObjectMapper objectMapper = new ObjectMapper();
	        try{
	            sr = objectMapper.readValue(content, AuthReq.class);
	        }catch(Throwable t){
	            throw new IOException(t.getMessage(), t);
	        }
	    } catch (IOException ex) {
	        throw ex;
	        
	    } finally {
	    	
	        if (bufferedReader != null) {
	            try {
	                bufferedReader.close();
	            } catch (IOException ex) {
	                throw ex;
	            }
	        }
	    }
	    System.out.println("email : "+sr.getEmail());
	    System.out.println("password : "+sr.getPassword());
	    return new UsernamePasswordAuthenticationToken(sr.getEmail(), sr.getPassword());
	}	
	
    public static class AuthReq {
        String email;
        String password;
        
        public String getEmail() {
        	return email;
        }
        public String getPassword() {
        	return password;
        }
    }
       

	protected void setDetails(HttpServletRequest request,
			UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	public void setUsernameParameter(String usernameParameter) {
		Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
		this.usernameParameter = usernameParameter;
	}

	public void setPasswordParameter(String passwordParameter) {
		Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
		this.passwordParameter = passwordParameter;
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getUsernameParameter() {
		return usernameParameter;
	}

	public final String getPasswordParameter() {
		return passwordParameter;
	}
}
