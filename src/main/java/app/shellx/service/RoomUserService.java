package app.shellx.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.shellx.dao.RoomRepository;
import app.shellx.dao.RoomUserRepository;
import app.shellx.dao.UserRepository;
import app.shellx.dto.RoomDto;
import app.shellx.dto.UserDto;
import app.shellx.model.Room;
import app.shellx.model.RoomUser;
import app.shellx.model.RoomUserId;
import app.shellx.model.User;

@Service
public class RoomUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private RoomUserRepository roomUserRepository;
	
	
	public RoomUserService() {
		
	}
	
	@Transactional
	public RoomDto join(long idRoom, long idUser) throws NullPointerException {
		User user = this.userRepository.findById(idUser);
		Room room = this.roomRepository.findById(idRoom);
		if (user != null && room != null) {
			RoomUser roomUser = new RoomUser(room, user, 3);
			this.roomUserRepository.save(roomUser);
			RoomDto roomDto = new RoomDto(room);
			return roomDto;
		}
		else {
			return null;
		}
	}
	
	@Transactional
	public Map<String, String> leave(long idRoom, long idUser) throws NullPointerException {
		User user = this.userRepository.findById(idUser);
		Room room = this.roomRepository.findById(idRoom);
		if (user != null && room != null) {
			if (room.getRoomAdmin() == idUser) {
				
			}
			RoomUserId id = new RoomUserId(idRoom, idUser);
			RoomUser roomUser = roomUserRepository.findById(id).orElseThrow(NullPointerException::new);
			this.roomUserRepository.delete(roomUser);
			Map<String, String> response = new HashMap<>();
			response.put("response", "ok");
			return response;
		}
		else {
			return null;
		}
	}	
}
