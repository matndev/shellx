/*
 * 
 * JwtTokenFilter vérifie que le Token est bien présent dans la requête
 * 
 * ### resolveToken()
 * Il teste l'existence et vérifie la conformité du token Authorization dans le header ("bearer ")
 * 
 * ### validateToken()
 * Vérifie qu'il y a des informations valides dans le token et qu'il n'a pas expiré
 * 
 * Puis le Security Context est mis à jour
 * 
*/

package app.shellx.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Jwts;

@Order(2)
@Component
public class JwtTokenFilter extends GenericFilterBean {
	
	private JwtTokenProvider jwtTokenProvider;
	
	
	@Value("${security.jwt.token.secret.key}")
    private String secretKey;
	
	@PostConstruct
    protected void init() {	
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());       
    }
	
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
			
			if (auth != null) {
				System.out.println("Username : "+auth.getName()+", Authorities :");
				auth.getAuthorities().forEach(System.out::println);
				HttpServletRequest request = ((HttpServletRequest) req);
				HttpServletResponse response = ((HttpServletResponse) res);
				Cookie cookies[] = request.getCookies();
				if (cookies != null) {
					for (Cookie cookie : cookies) {
						if (cookie.getName().equals("access_token")) {
						   
						   
						String cookieValue = cookie.getValue();
						System.out.println("Contenu du cookie : "+cookieValue);
						Date exp = new Date();
						exp = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(cookieValue).getBody().getExpiration();
						LocalDateTime ldt = LocalDateTime.ofInstant(exp.toInstant(), ZoneId.systemDefault());
						System.out.println("Date : "+ldt.getDayOfYear()+"/"+ldt.getDayOfMonth()+"/"+ldt.getYear()+", Time : "+ldt.getSecond()+":"+ldt.getMinute()+":"+ldt.getHour());
						
						     
						List<String> authorities = new ArrayList<String>();
						auth.getAuthorities().forEach(authority -> authorities.add(authority.getAuthority()));
						String newToken = jwtTokenProvider.createToken(auth.getName(), authorities);
						final Cookie newCookie = jwtTokenProvider.createCookieForToken(newToken);
						response.addCookie(newCookie);
						filterChain.doFilter(req, response);
						}
					}
				}
				else {
					filterChain.doFilter(req, res);
				}
			}
			else {
				filterChain.doFilter(req, res);
			}
		}
		else {
			filterChain.doFilter(req, res);
		}
	}
}
