/*
 * 
 * JwtTokenFilter v�rifie que le Token est bien pr�sent dans la requ�te
 * 
 * ### resolveToken()
 * Il teste l'existence et v�rifie la conformit� du token Authorization dans le header ("bearer ")
 * 
 * ### validateToken()
 * V�rifie qu'il y a des informations valides dans le token et qu'il n'a pas expir�
 * 
 * Puis le Security Context est mis � jour
 * 
*/

package app.shellx.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Order(2)
@Component
public class JwtTokenFilter extends GenericFilterBean {
	
	private JwtTokenProvider jwtTokenProvider;
	
//	private Logger log = LogManager.getLogger(JwtTokenFilter.class.getName());
	
	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {

		String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
		if (token != null && jwtTokenProvider.validateToken(token)) {
			Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
			SecurityContextHolder.getContext().setAuthentication(auth);
			
			System.out.println("Username : "+auth.getName()+", Authorities :");
			auth.getAuthorities().forEach(System.out::println);
//			HttpServletRequest request = ((HttpServletRequest) req);
//			HttpServletResponse response = ((HttpServletResponse) res);
		}
		filterChain.doFilter(req, res);
	}
}
