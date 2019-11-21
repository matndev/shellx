package app.shellx.dto;

import java.io.Serializable;
import java.time.LocalDate;

import app.shellx.model.Room;

public class RoomDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 689488752381752561L;
	

	protected long id;
	protected String name;
	protected String description;
	protected long roomAdmin;
	protected LocalDate dateCreation;
	protected boolean enabled;
	protected boolean modePrivate;
//	protected int userCount;
	
	public RoomDto() {
		
	}
	
	public RoomDto(long id, String name, long roomAdmin, boolean modePrivate) {
		this.id = id;
		this.name = name;
		this.roomAdmin = roomAdmin;
		this.modePrivate = modePrivate;
	}
	
	public RoomDto(Room room) {
		this.id = room.getId();
		this.name = room.getName();
		this.roomAdmin = room.getRoomAdmin();
		this.modePrivate = room.isModePrivate();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getRoomAdmin() {
		return roomAdmin;
	}

	public void setRoomAdmin(long roomAdmin) {
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

//	public int getUserCount() {
//		return userCount;
//	}
//
//	public void setUserCount(int userCount) {
//		this.userCount = userCount;
//	}	
}
