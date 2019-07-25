package app.shellx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.shellx.dto.RoomDto;
import app.shellx.model.Room;
import app.shellx.service.RoomService;

@RestController
@RequestMapping("/rooms")
public class RoomController {

	@Autowired
	private RoomService roomService;
	
	@PostMapping(path="/add", consumes="application/json", produces="application/json")
	public Room add(@RequestBody Room room) {
		Room retRoom = this.roomService.add(room);
		return retRoom;
	}
	
	// GET Room only
	@GetMapping(path = {"/c/{id}"}, produces="application/json")
	public Room findComplete(@PathVariable int id) {
		return this.roomService.findCompleteRoomById(id);
	}
	
	// GET Room only
	@GetMapping(path = {"/{id}"}, produces="application/json")
	public RoomDto find(@PathVariable int id) {
		return this.roomService.findRoomById(id);
	}
}
