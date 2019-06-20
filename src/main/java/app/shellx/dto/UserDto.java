package app.shellx.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import app.shellx.annotation.PasswordMatches;
import app.shellx.annotation.ValidEmail;

@PasswordMatches
public class UserDto {

	//private long id;
	
	@NotNull
	@NotEmpty
	private String username;
	
	@ValidEmail
	@NotNull
	@NotEmpty
	private String email;
	
	@NotNull
	@NotEmpty
	private String password;
	private String matchingPassword;
	
	public UserDto() {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}
}
