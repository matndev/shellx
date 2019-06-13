package app.shellx.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.shellx.model.Message;
import app.shellx.service.MessageService;

@RestController
@RequestMapping("/message")
public class MessageController {

	@Autowired
	private MessageService messageService;
	
	/*@Autowired
	public TestController(MessageService messageService) {
		this.messageService = messageService;
	}*/
	
	@RequestMapping("/add")
	public void add() {
		System.out.println("### LOG : Message sent from controller to MessageService");
		this.messageService.add(new Message("Pierre", "Alan", "Bonjour c'est Pierre", true, LocalDate.now()));
	}
	
	@RequestMapping(path = "/update")
	public void update() {
		System.out.println("### LOG : Message sent from controller to MessageService for update");
		this.messageService.update(new Message("Pierre", "Alan", "Bonjour c'est Pierre", true, LocalDate.now()));
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
