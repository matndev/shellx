package app.shellx.dao;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.shellx.dto.RoomDto;
import app.shellx.model.Room;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {

	public Set<Room> findByName(String name);
	
	public Room findById(int id);
}
