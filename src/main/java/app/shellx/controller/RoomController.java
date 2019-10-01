package app.shellx.controller;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.shellx.dto.RoomDto;
import app.shellx.dto.UserDto;
import app.shellx.model.Room;
import app.shellx.service.RoomService;
import app.shellx.service.UserService;

@RestController
@RequestMapping("/rooms")
public class RoomController {

	@Autowired
	private RoomService roomService;
	
	@Autowired
	private UserService userService;
	
	// Don't send messages to the broker (except with SendTo annotation)
//	@SubscribeMapping("{id}/get")
//	public Room findRoomWithMessages(@DestinationVariable int id) {
//		return this.roomService.findCompleteRoomById(id);
//	}
	
	
	@PostMapping(path="/add", consumes="application/json", produces="application/json")
	public Room add(@RequestBody Room room) {
		return this.roomService.add(room);
	}
	
	// GET Room with messages
	@GetMapping(path = {"/get/{id}"}, produces="application/json")
	public Room findComplete(@PathVariable int id, Authentication authentication) {
		return this.roomService.findRoomById(id, authentication);
//		Room room = this.roomService.findRoomById(id);
//		Set<RoomUser> roomsUsers = room.getUsers();
//		boolean isUserOK = roomsUsers.stream().anyMatch(roomUser -> (roomUser.getUser().getUsername().equals(principal.getName())));
//		System.out.println("User in current room : "+isUserOK);
//		// trier les infos pour les users de le room (garder que le username, l'avatar et le role)
//					roomsUsers.forEach(roomUser -> {
//						roomUser.setUserDto(new UserDto(roomUser.getUser()));
//					});
//		if (isUserOK == true) { return room; } else { return null; }
	}
	
	
	
	
	// GET Room only (s = simplified)
	@GetMapping(path = {"/get/s/{id}"}, produces="application/json")
	public RoomDto find(@PathVariable int id) {
		return this.roomService.findRoomDtoById(id);
	}
	
	@GetMapping(path = {"/get/users/{id}"}, produces="application/json")
	public Set<UserDto> findUsersByRoomId(@PathVariable int id) {
		return this.roomService.findUsersByRoomId(id);
	}
	
	@GetMapping(path = {"/get/rooms/{id}"}, produces="application/json")
	public Set<RoomDto> findRoomsByUserId(@PathVariable int id) {
		return this.userService.findRoomsByUserId(id);
	}	
}
