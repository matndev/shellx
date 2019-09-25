package app.shellx.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import app.shellx.dto.UserDto;

@Entity
public class RoomUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2891938868124560462L;
	
	@Id
	@ManyToOne
	@JoinColumn(name="rooms_id")
	private Room room; // int
	@Id
	@ManyToOne
	@JoinColumn(name="users_id")
	private User user; // long
	private int role;
	
	RoomUser() {
		
	}
	
	RoomUser(Room room, User user, int role) {
		this.room = room;
		this.user = user;
		this.role = role;
	}

	public Room getRoom() {
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

}
