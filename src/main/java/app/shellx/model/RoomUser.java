package app.shellx.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import app.shellx.dto.UserDto;

@Entity
@Table(name="rooms_users")
public class RoomUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2891938868124560462L;
	
	@EmbeddedId
	RoomUserId id;	
	
	// MapsId link to composite key in RoomUserId.java
	
	@MapsId(value = "roomId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="rooms_id")
	private Room room;
	
	@MapsId(value = "userId")	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="users_id")
	private User user;
	
	private int role;
	
	@Transient
	private UserDto userDto;
	
	RoomUser() {
		
	}
	
	RoomUser(Room room, User user, int role) {
		this.room = room;
		this.user = user;
		this.role = role;
	}

	
	public Room getRoom() {
		room.setUsers(null);
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

}
