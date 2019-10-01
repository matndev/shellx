package app.shellx.dao;

import org.springframework.data.repository.CrudRepository;

import app.shellx.model.RoomUser;
import app.shellx.model.RoomUserId;

public interface RoomUserRepository extends CrudRepository<RoomUser, RoomUserId> {

	public boolean existsById(RoomUserId id);
}
