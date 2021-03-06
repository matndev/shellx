package app.shellx.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
public class RoomService {

	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private RoomUserRepository roomUserRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public RoomDto add(Room room) {
		User user = this.userRepository.findById(room.getRoomAdmin());
		room.addUser(user);
		Room resRoom = this.roomRepository.save(room);
		return new RoomDto(resRoom);
	}
	
	// GET ROOM / MESSAGES / NO USERS
	@Transactional(readOnly = true)
	public Room findRoomById(long id, Authentication authentication) {

		User user = (User) authentication.getPrincipal();		
		RoomUserId compKey = new RoomUserId(id, user.getId());
		if (this.roomUserRepository.existsById(compKey)) {
			Room room = this.roomRepository.findById(id);	
			room.setUsers(null);
			return room;
		}
		else { return null; }
	}
	
	// GET ROOM ONLY
	@Transactional(readOnly = true)
	public RoomDto findRoomDtoById(long id, Authentication authentication) {
		User user = (User) authentication.getPrincipal();		
		RoomUserId compKey = new RoomUserId(id, user.getId());
		if (this.roomUserRepository.existsById(compKey)) {		
			Room room = this.roomRepository.findById(id);
//			int userCount = this.roomUserRepository.countByRoomId(id);
			RoomDto roomDto = new RoomDto(room.getId(), room.getName(), room.getRoomAdmin(), room.isModePrivate());
			roomDto.setDescription(room.getDescription());
			return roomDto;
		}
		else { return null; }			
	}
	
	//GET All users by room ID
	@Transactional(readOnly = true)
	public Set<UserDto> findUsersByRoomId(long id) {
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
