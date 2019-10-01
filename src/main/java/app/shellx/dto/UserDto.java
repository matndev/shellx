package app.shellx.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import app.shellx.annotation.ValidEmail;
import app.shellx.model.User;

//@PasswordMatches
public class UserDto {

	private long id;
	
	@NotNull
	@NotEmpty
	private String username;
	
	@ValidEmail
	@NotNull
	@NotEmpty
	private String email;
	
	/*@NotNull
	@NotEmpty*/
	private String password;
	//private String matchingPassword;
	
	private String avatar;
	private int role;
	
	public UserDto() {
		
	}
	
	public UserDto(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		//this.email = user.getEmail();
		this.avatar = user.getAvatar();
		this.role = user.getRole().getId();
	}
	
	public UserDto(long id, String username, String avatar, int role) {
		this.id = id;
		this.username = username;
		this.avatar = avatar;
		this.role = role;
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

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/*public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}*/

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
	
}
