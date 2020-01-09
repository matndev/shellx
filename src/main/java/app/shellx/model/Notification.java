package app.shellx.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="notifications")
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="not_id")
	private long id;
	@Column(name="not_type")
	private String type;
	@Column(name="not_title")
	private String title;
	@Column(name="not_description")
	private String description;
	@Column(name="not_user")
	private long user;	
	@Column(name="not_room")
	private long room;
	@Column(name="not_date")
	private LocalDateTime dateCreation;
	@Column(name="not_read")
	private boolean read;
	
	public Notification() {
		
	}
	
	public Notification(String type, String title, String description, long user, long room, LocalDateTime dateCreation, boolean read) {
		this.type = type;
		this.title = title;
		this.description = description;
		this.user = user;
		this.room = room;
		this.dateCreation = dateCreation;
		this.read = read;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}
	
	public long getUser() {
		return user;
	}

	public void setUser(long user) {
		this.user = user;
	}	
	
	public long getRoom() {
		return room;
	}

	public void setRoom(long room) {
		this.room = room;
	}	
}
