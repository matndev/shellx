package app.shellx.model;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "roles_id")
	private int id;
	@Column(name = "roles_name")
	private String role;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="role")
	private Set<User> users;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // EAGER
    @JoinTable(
            name = "roles_authorities", 
            joinColumns = { @JoinColumn(name = "roles_id") }, 
            inverseJoinColumns = { @JoinColumn(name = "authorities_id") }
    )
	private Set<Authority> authorities;
	
	public Role() {
		
	}
	
	public Role(int id) {
		this.id =id;
	}
	
	public Role(String role) {
		this.role = role;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public Set<Authority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}
}
