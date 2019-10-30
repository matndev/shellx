package app.shellx.dto;

import app.shellx.model.RoomUser;

public class RoomUserDto {

	private long idRoom;
	private long idUser;
	private int role;
	private String roomname;
	private String username;
	
	
	public RoomUserDto() {
		
	}

	public RoomUserDto(long idRoom, long idUser, int role) {
		this.idRoom = idRoom;
		this.idUser = idUser;
		this.role = role;
	}
	
	public RoomUserDto(RoomUser roomUser) {
		this.idRoom = roomUser.getId().getRoomId();
		this.idUser = roomUser.getId().getUserId();
		this.role = roomUser.getRole();
		this.roomname = roomUser.getRoom().getName();
		this.username = roomUser.getUser().getUsername();
	}
	
	
	public long getIdRoom() {
		return idRoom;
	}
	public void setIdRoom(long idRoom) {
		this.idRoom = idRoom;
	}
	public long getIdUser() {
		return idUser;
	}
	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getRoomname() {
		return roomname;
	}
	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String toString() {
		return "Class: RoomUserDto, toString() method\n"+
		"Room ID : "+this.idRoom+"\n"+
		"Room name : "+this.roomname+"\n"+
		"User ID : "+this.idUser+"\n"+
		"User name : "+this.username+"\n"+
		"Role Id : "+this.role;
	}
}
