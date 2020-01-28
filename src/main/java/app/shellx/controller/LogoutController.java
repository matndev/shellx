package app.shellx.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.shellx.config.CookieConfig;
import app.shellx.security.JwtTokenProvider;

@RestController
public class LogoutController {

	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
    	String token = jwtTokenProvider.resolveToken(request);
    	if (!token.equals("")) {
	        boolean invalidateSuccess = jwtTokenProvider.invalidate(token);
	        if (invalidateSuccess) {
	        	CookieConfig.deleteCookieForLogout(request, response);
	        	return "redirect:/";
	        }
    	}
    	return "error 500";
    }
}
