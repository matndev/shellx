/**
 *  extends RuntimeException for allows to try/catch within the Filterchain
 */

package app.shellx.security;

public class CookieException extends RuntimeException {

	public CookieException(String message) {
		super(message);
	}
	
	public CookieException(String message, Throwable err) {
		super(message, err);
	}
}
