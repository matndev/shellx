package app.shellx.security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import app.shellx.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	
	@Value("${security.jwt.token.secret.key}")
    private String secretKey;
	
	@Value("${security.jwt.token.expire-length:300000}")
    private long validityInMilliseconds = 300000; // 5min = 300000, 1h = 3600000   
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	@PostConstruct
    protected void init() {	
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());       
    }
	
	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
//		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	}
	
	public String createToken(String username, List<String> roles) {
		
		Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
        			.setClaims(claims)
        			.setIssuedAt(now)
        			.setExpiration(validity)
        			.signWith(getSigningKey())
        			.compact();
    }
	
	public Authentication getAuthentication(String token) {
		User userDetails = (User) this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
	
	public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
	
	public Date getExpirationDate(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
	}
	
	public String resolveToken(HttpServletRequest req) {
//        String accessToken = req.getHeader("Cookie");
//        if (accessToken != null && accessToken.startsWith("access_token")) {
//            return accessToken.substring(13, accessToken.length());
//        }
//        return null;
		Cookie cookies[] = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("access_token")) {
					System.out.println("### DEBUG : ResolveToken: cookie value: "+cookie.getValue());
					return cookie.getValue();
				}
			}
		}
		return null;
    }
	
	public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//            System.out.println("Timestamp token : "+claims.getBody().getExpiration()+", timestamp: new date : "+new Date());
//            if (claims.getBody().getExpiration().before(new Date())) {
//                return false;
//            }
            return true;
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(null, null, "Token expired");
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }
    }
}
