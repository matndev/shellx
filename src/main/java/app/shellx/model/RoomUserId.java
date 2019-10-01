/**
 * Class RoomUserId created because of JPA standard
 * Define two primary keys for the rooms_users table
 * hashCode and equals methods are mandatory
 */

package app.shellx.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
public class RoomUserId implements Serializable{

	@Column(name="rooms_id")
	private int roomId; // int
	
	@Column(name="users_id")
	private long userId; // long

	public RoomUserId(int roomId, long userId) {
		this.roomId = roomId;
		this.userId = userId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + roomId;
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoomUserId other = (RoomUserId) obj;
		if (roomId != other.roomId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	
}
