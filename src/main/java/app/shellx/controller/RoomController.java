package app.shellx.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.shellx.dto.RoomDto;
import app.shellx.dto.UserDto;
import app.shellx.model.Message;
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
		Room retRoom = this.roomService.add(room);
		return retRoom;
	}
	
	// GET Room with messages
	@GetMapping(path = {"/get/{id}"}, produces="application/json")
	public void findComplete(@PathVariable int id) {
		Room room = this.roomService.findRoomById(id);
		//System.out.println(room.toString());
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
