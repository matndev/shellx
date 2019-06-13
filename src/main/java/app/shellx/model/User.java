package app.shellx.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name="users")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "users_id")
	private long id;
	@Column(name = "users_username", nullable = false, unique = true)
	private String username;
	@Column(name = "users_email", nullable = false, unique = true)
	private String email;
	@Column(name = "users_password")
	private String password;
	@Column(name = "users_enabled")
	private boolean enabled;
	@Column(name = "users_avatar")
	private String avatar;
	@Column(name = "users_date")
	private LocalDate date;
	/*@Column(name = "users_role")
	private int refRole;*/
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "users_role")
	/*@JoinTable(
		name = "authorities", 
		joinColumns = @JoinColumn(name = "users_role"), 
		inverseJoinColumns = @JoinColumn(name = "roles_"))*/
	private Role role;
	
	@Transient
	private Set<Authority> authorities;
	
	
	public User() {
		
	}
	
	public User(String username, String email, String password, boolean enabled, String avatar) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.avatar = avatar;
	}

	/*public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Authority> authorities = new HashSet<Authority>();
		authorities = role.getAuthorities();
		return authorities;
	}*/
	public Set<Authority> getAuthorities() {
		/*Set<Authority> authorities = new HashSet<Authority>();
		authorities = role.getAuthorities();*/
		return authorities;
	}
	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}
	public void setName(String username) {
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

	/*public int getRefRole() {
		return refRole;
	}

	public void setRefRole(int refRole) {
		this.refRole = refRole;
	}*/
	
}
