package app.shellx.model.projections;

import app.shellx.model.User;

// NOT FINALLY USED
// Cause : getUser not eagerly fetched so NullPointerException
public interface UserlistByRoomProj {
	public User getUser();
	public int getRole();
}
