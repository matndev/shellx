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
	
	public RoomUser() {
		
	}
	
	public RoomUser(long idRoom, long idUser, int role) {
		this.id = new RoomUserId(idRoom, idUser);
		this.role = role;
	}
	
	public RoomUser(Room room, User user, int role) {
		this.id = new RoomUserId(room.getId(), user.getId());
		this.room = room;
		this.user = user;
		this.role = role;
	}

	
	
	public RoomUserId getId() {
		return id;
	}

	public void setId(RoomUserId id) {
		this.id = id;
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

	public String toString() {
		return "Class: RoomUser, toString() method\n"+
		"Room ID : "+this.id.getRoomId()+"\n"+
		"User ID : "+this.id.getUserId()+"\n"+
		"Room Object : "+this.getRoom().getName()+"\n"+
		"User Object : "+this.getUser().getUsername()+"\n"+
		"Role Id : "+this.getRole();
	}
}
