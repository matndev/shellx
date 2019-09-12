package app.shellx.controller;

import java.util.List;

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
import app.shellx.model.Message;
import app.shellx.model.Room;
import app.shellx.service.RoomService;

@RestController
@RequestMapping("/rooms")
public class RoomController {

	@Autowired
	private RoomService roomService;
	
	
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
