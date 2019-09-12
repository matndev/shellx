package app.shellx.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.shellx.dto.MessageDto;
import app.shellx.model.Message;
import app.shellx.service.MessageService;

@RestController
@RequestMapping("/message")
public class MessageController {

	@Autowired
	private MessageService messageService;
	
	
	@SubscribeMapping("/message/get/all")
	public List<Message> findAll() {
		return this.messageService.findAll();
	}
	
	
	@PostMapping(path="/add", consumes="application/json", produces="application/json")
	public Message add(@RequestBody MessageDto messageDto) {
		System.out.println("### LOG : Message sent from controller to MessageService");
		return this.messageService.add(messageDto);
	}
	
/*	@RequestMapping("/add")
	public void add() {
		System.out.println("### LOG : Message sent from controller to MessageService");
		//this.messageService.add(new Message("Pierre", "Alan", "Bonjour c'est Pierre", true, LocalDate.now(), new Room()));
	}
*/	
	@RequestMapping(path = "/update", method = RequestMethod.GET, produces = "application/json")
	public String update() {
		System.out.println("### LOG : Message sent from controller to MessageService for update");
		return "ok";
		//this.messageService.update(new Message("Pierre", "Alan", "Bonjour c'est Pierre", true, LocalDate.now(), new Room()));
	}
	
	@GetMapping(path = {"/find/{id}"})
	public Message find(@PathVariable long id) {
		System.out.println("### LOG : Get a message from database");
		return this.messageService.findById(id);
	}
	
	@GetMapping(path = {"/delete/{id}"})
	public void delete(@PathVariable long id) {
		System.out.println("### LOG : Get a message from database");
		this.messageService.deleteById(id);
	}
	
	@GetMapping(path = {"/visibility/{id}/{setStateVisibility}"})
	public void updateVisibility(@PathVariable("id") long id, @PathVariable("setStateVisibility") boolean setStateVisibility) {
		System.out.println("### LOG : Change visilibity for message with id");
		this.messageService.updateVisibility(id, setStateVisibility);
	}
}
