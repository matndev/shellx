package app.shellx.config;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;

import app.shellx.utils.DateTimeManager;

public final class CookieConfig {

	private static String name;
	
	@Value("${security.cookie.setsecure.enable}")
    private static boolean isSetSecureEnabled;
	
//	@Value("${security.jwt.token.secret.key}")
	private static int authenticationExpiration = 3600; // 600
	
	private CookieConfig() {
		
	}
	
	public static Cookie createCookieForJWT(String token) {
		final Cookie cookie = new Cookie("access_token", token);
		cookie.setSecure(isSetSecureEnabled); // a mettre en commentaire si ca marche pas
		cookie.setHttpOnly(true);
		cookie.setMaxAge(authenticationExpiration);
		cookie.setPath("/");
		return cookie;
	}
	
	public static Cookie createCookieExpirationSession(Date timestamp) {
		ZonedDateTime expirationDate = DateTimeManager.convertDateToZonedDateTime(timestamp);
		String expiration = expirationDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	    final Cookie cookie = new Cookie("_exp", expiration);
		cookie.setSecure(isSetSecureEnabled);
		cookie.setHttpOnly(false);
		cookie.setMaxAge(authenticationExpiration);
		cookie.setPath("/");
		return cookie;		
	}
	
	public static Cookie createCookieWithIdUser(long id) {
	    final Cookie cookie = new Cookie("_id", id+"");
		cookie.setSecure(isSetSecureEnabled);
		cookie.setHttpOnly(false);
		cookie.setMaxAge(1000*60*60*24*31);
		cookie.setPath("/");
		return cookie;		
	}
	
	public static Cookie createCookieXSRFToken() {
	    final Cookie cookie = new Cookie("XSRF-TOKEN", UUID.randomUUID().toString());
		cookie.setSecure(isSetSecureEnabled);
		cookie.setHttpOnly(false);
		cookie.setMaxAge(authenticationExpiration);
		cookie.setPath("/");
		return cookie;		
	}
	
	public static void deleteCookieForLogout(HttpServletRequest req, HttpServletResponse res) {
		Cookie cookies[] = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("access_token") || cookie.getName().equals("_id") || cookie.getName().equals("_exp")) {
					final Cookie newCookie = new Cookie(cookie.getName(), "");
					newCookie.setSecure(isSetSecureEnabled); // a mettre en commentaire si ca marche pas
					newCookie.setHttpOnly(true);
					newCookie.setMaxAge(0);
					newCookie.setPath("/");
					res.addCookie(newCookie);
				}
			}
		}
	}
}
