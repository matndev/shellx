package app.shellx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.shellx.dao.RoomRepository;
import app.shellx.model.Room;

@Service
public class RoomService {

	@Autowired
	private RoomRepository roomRepository;
	
	@Transactional
	public Room add(Room room) {
		return this.roomRepository.save(room);
	}
	
	@Transactional(readOnly = true)
	public Room findRoomById(int id) {
		return this.roomRepository.findById(id);
	}
}
