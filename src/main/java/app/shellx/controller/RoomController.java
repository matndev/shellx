package app.shellx.controller;

import java.util.Map;
import java.util.Set;

import javax.xml.ws.Response;

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
import app.shellx.service.RoomUserService;
import app.shellx.service.UserService;

@RestController
@RequestMapping("/rooms")
public class RoomController {

	@Autowired
	private RoomService roomService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoomUserService roomUserService;
	
	// Don't send messages to the broker (except with SendTo annotation)
//	@SubscribeMapping("{id}/get")
//	public Room findRoomWithMessages(@DestinationVariable int id) {
//		return this.roomService.findCompleteRoomById(id);
//	}
	
	@PostMapping(path="/join", consumes="application/json", produces="application/json")
	public RoomDto join(@RequestBody Map<String, String> payload) {
		if (payload.containsKey("idRoom") 
			&& payload.containsKey("idUser")
			&& payload.get("idRoom") != null
			&& payload.get("idUser") != null
			&& payload.size() == 2) {
			return this.roomUserService.join(Long.parseLong(payload.get("idRoom")), Long.parseLong(payload.get("idUser")));
		}
		else {
			return null;
		}
	}
	
	@PostMapping(path="/leave", consumes="application/json", produces="application/json")
	public Map<String, String> leave(@RequestBody Map<String, String> payload) {
		if (payload.containsKey("idRoom") 
			&& payload.containsKey("idUser")
			&& payload.get("idRoom") != null
			&& payload.get("idUser") != null
			&& payload.size() == 2) {
			return this.roomUserService.leave(Long.parseLong(payload.get("idRoom")), Long.parseLong(payload.get("idUser")));
		}
		else {
			return null;
		}
	}	
	
	@PostMapping(path="/add", consumes="application/json", produces="application/json")
	public RoomDto add(@RequestBody Room room) {
		return this.roomService.add(room);
	}
	
	// GET Room with messages
	@GetMapping(path = {"/get/c/{id}"}, produces="application/json")
	public Room findComplete(@PathVariable long id, Authentication authentication) {
		return this.roomService.findRoomById(id, authentication);
	}
	
	
	// GET Room only (s = simplified) and user count
	@GetMapping(path = {"/get/{id}"}, produces="application/json")
	public RoomDto find(@PathVariable long id, Authentication authentication) {
		return this.roomService.findRoomDtoById(id, authentication);
	}
	
	@GetMapping(path = {"/get/users/{id}"}, produces="application/json")
	public Set<UserDto> findUsersByRoomId(@PathVariable long id) {
		return this.roomService.findUsersByRoomId(id);
	}
	
	@GetMapping(path = {"/get/all/{id}"}, produces="application/json")
	public Set<RoomDto> findRoomsByUserId(@PathVariable long id) {
		return this.userService.findRoomsByUserId(id);
	}	
}
