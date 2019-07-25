package app.shellx.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class RoomDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 689488752381752561L;
	

	protected int id;
	protected String name;
	protected int roomAdmin;
	protected LocalDate dateCreation;
	protected boolean enabled;
	protected boolean modePrivate;
	
	public RoomDto() {
		
	}
	
	public RoomDto(int id, String name, int roomAdmin, boolean modePrivate) {
		this.id = id;
		this.name = name;
		this.roomAdmin = roomAdmin;
		this.modePrivate = modePrivate;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

}
