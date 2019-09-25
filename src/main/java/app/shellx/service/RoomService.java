package app.shellx.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.shellx.dao.RoomRepository;
import app.shellx.dto.RoomDto;
import app.shellx.dto.UserDto;
import app.shellx.model.Room;
import app.shellx.model.RoomUser;

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
	public Room findRoomById(int id) {
		return this.roomRepository.findById(id);
	}
	
	// GET Room only
	@Transactional(readOnly = true)
	public RoomDto findRoomDtoById(int id) {
		Room room = this.roomRepository.findById(id);
		return new RoomDto(room.getId(), room.getName(), room.getRoomAdmin(), room.isModePrivate());
	}
	
	//GET All users by room ID
	@Transactional(readOnly = true)
	public Set<UserDto> findUsersByRoomId(int id) {
		Room room = this.roomRepository.findById(id);
		if (room == null) {return null;}
		else {
		Set<UserDto> userList = new HashSet<UserDto>();
		Set<RoomUser> roomUser = room.getUsers();
		roomUser.forEach(user -> userList.add(new UserDto(user.getUser())));
		return userList;
		}
	}

	// GET All rooms by user ID
//	@Transactional(readOnly = true)
//	public List<UserDto> findRoomsByUserId(int id) {
//		return null;//this.roomRepository.find;
//	}	
	
}
