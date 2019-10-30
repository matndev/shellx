package app.shellx.dao;

import java.util.Set;
import org.springframework.data.repository.CrudRepository;

import app.shellx.dto.RoomUserDto;
import app.shellx.model.RoomUser;
import app.shellx.model.RoomUserId;

public interface RoomUserRepository extends CrudRepository<RoomUser, RoomUserId> {

	public boolean existsById(RoomUserId id);
	
//	Using projection	
//	public List<UserlistByRoomProj> findByIdRoomId(long id);
	
	public Set<RoomUser> findByRoomId(long id);
	
	public void save(RoomUserDto entity);
}
