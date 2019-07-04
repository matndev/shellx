package app.shellx.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="authorities")
public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "authorities_id")
	private long id;
	@Column(name = "authorities_name")
	private String authority;
	/*@Column(name = "authorities_role")
	private int role;*/
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy="authorities")
	private Set<Role> roles;
	
	public Authority() {
		
	}
	
	public Authority(String authority) {
		this.authority = authority;
	}
	
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	/*public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}*/
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}
