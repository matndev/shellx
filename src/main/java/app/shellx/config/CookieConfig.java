package app.shellx.config;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Value;

import app.shellx.utils.DateTimeManager;

public final class CookieConfig {

	private static String name;
	
	@Value("${security.jwt.token.secret.key}")
	private static int authenticationExpiration = 600;
	
	private CookieConfig() {
		
	}
	
	public static Cookie createCookieForJWT(String token) {
		final Cookie cookie = new Cookie("access_token", token);
		cookie.setSecure(false); // a mettre en commentaire si ca marche pas
		cookie.setHttpOnly(true);
		cookie.setMaxAge(authenticationExpiration);
		cookie.setPath("/");
		return cookie;
	}
	
	public static Cookie createCookieExpirationSession(Date timestamp) {
		ZonedDateTime expirationDate = DateTimeManager.convertDateToZonedDateTime(timestamp);
		String expiration = expirationDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	    final Cookie cookie = new Cookie("_exp", expiration);
		cookie.setSecure(false);
		cookie.setHttpOnly(false);
		cookie.setMaxAge(authenticationExpiration);
		cookie.setPath("/");
		return cookie;		
	}
	
	public static Cookie createCookieWithIdUser(long id) {
	    final Cookie cookie = new Cookie("_id", id+"");
		cookie.setSecure(false);
		cookie.setHttpOnly(false);
		cookie.setMaxAge(1000*60*60*24*31);
		cookie.setPath("/");
		return cookie;		
	}
}
