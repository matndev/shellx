package app.shellx.security;

public class UserAlreadyLoggedException extends RuntimeException {

	public UserAlreadyLoggedException(String message) {
		super(message);
	}
}
