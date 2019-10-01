package app.shellx.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name="users")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_seq")
//	@SequenceGenerator(name="user_seq", sequenceName="")
	@Column(name = "users_id", updatable = false, nullable = false)
	private long id;
	@Column(name = "users_username", nullable = false, unique = true)
	private String username;
	@Column(name = "users_email", nullable = false, unique = true)
	private String email;
	
	// A TESTER
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "users_password")
	private String password;
	@Column(name = "users_enabled")
	private boolean enabled;
	@Column(name = "users_avatar")
	private String avatar;
	@Column(name = "users_date")
	private LocalDate date;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "users_role")
	/*@JoinTable(
		name = "authorities", 
		joinColumns = @JoinColumn(name = "users_role"), 
		inverseJoinColumns = @JoinColumn(name = "roles_"))*/
	private Role role;
	
	@Transient
	private Set<Authority> authorities;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="user")
	private Set<RoomUser> rooms;

	

	public User() {
		
	}
	
	public User(String username, String email, String password, boolean enabled, String avatar) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.avatar = avatar;
	}

	// return role name and authorities
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authoritiesList = new ArrayList<GrantedAuthority>();
		authoritiesList.add(new SimpleGrantedAuthority(role.getRole()));
		this.authorities.stream().map(p -> new SimpleGrantedAuthority(p.getAuthority())).forEach(authoritiesList::add);
		return authoritiesList;
	}
	public Set<Authority> getOnlyAuthorities() {
		return authorities;
	}
	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}
	public void setUsername(String username) {
		this.username = username;
	}	
	public String getUsername() {
		return username;
	}
	public boolean isEnabled() {
		return enabled;
	}	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isAccountNonExpired() {
		return true;
	}	
	public boolean isAccountNonLocked() {
		return true;
	}
	public boolean isCredentialsNonExpired() {
		return true;
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
	
	public String getAvatar() {
		return avatar;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public String getRoleName() {
		return role.getRole();
	}
	
	public long getId() {
		return id;
	}
	
	public Set<RoomUser> getRooms() {
		return rooms;
	}

	public void setRooms(Set<RoomUser> rooms) {
		this.rooms = rooms;
	}
	
	public String toString() {
		return username;
	}
}
