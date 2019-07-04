package app.shellx.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="rooms")
public class Room implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3638046496783069225L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rooms_id")
	protected int id;
	@NotNull
	@NotEmpty
	@Column(name="rooms_name")
	protected String name;
	@Column(name="rooms_admin")
	protected int roomAdmin;
	@Column(name="rooms_date_creation")
	protected LocalDate dateCreation;
	@Column(name="rooms_enabled")
	protected boolean enabled;
	@Column(name="rooms_mode_private")
	protected boolean modePrivate;
	
	@JsonManagedReference
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="messageRoom")
	@Where(clause = "messages_enabled = true")
	protected Set<Message> messages;
	
	@Transient
	protected List<User> users;
	
	public Room() {
		
	}
	
	public Room(String name, int roomAdmin, boolean modePrivate) {
		this.name = name;
		this.roomAdmin = roomAdmin;
		this.modePrivate = modePrivate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRoomAdmin() {
		return roomAdmin;
	}

	public void setRoomAdmin(int roomAdmin) {
		this.roomAdmin = roomAdmin;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isModePrivate() {
		return modePrivate;
	}

	public void setModePrivate(boolean modePrivate) {
		this.modePrivate = modePrivate;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
