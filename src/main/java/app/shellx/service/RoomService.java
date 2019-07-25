package app.shellx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.shellx.dao.RoomRepository;
import app.shellx.dto.RoomDto;
import app.shellx.model.Room;

@Service
public class RoomService {

	@Autowired
	private RoomRepository roomRepository;
	
	@Transactional
	public Room add(Room room) {
		return this.roomRepository.save(room);
	}
	
	// GET Room with all messages
	@Transactional(readOnly = true)
	public Room findCompleteRoomById(int id) {
		return this.roomRepository.findById(id);
	}
	
	// GET Room only
	@Transactional(readOnly = true)
	public RoomDto findRoomById(int id) {
		Room room = this.roomRepository.findById(id);
		return new RoomDto(room.getId(), room.getName(), room.getRoomAdmin(), room.isModePrivate());
	}
}
