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
	protected long roomAdmin;
	protected LocalDate dateCreation;
	protected boolean enabled;
	protected boolean modePrivate;
	
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

}
