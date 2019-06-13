package app.shellx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.shellx.dao.MessageRepository;
import app.shellx.model.Message;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;
	
	/*@Autowired
	public MessageServiceImpl(MessageRepository<Message> messageRepository) {
		this.messageRepository = messageRepository;
	}*/
	
	public void add(Message message) {
		this.messageRepository.save(message);
	}
	
	public Message findById(long id) {
		return this.messageRepository.findById(id);
	}
	
	public void update(Message message) {
		this.messageRepository.save(message);
	}
	
	public void deleteById(long id) {
		this.messageRepository.deleteById(id);
	}
	
	public void updateVisibility(long id, boolean setStateVisibility) {
		this.messageRepository.updateVisibility(id, setStateVisibility);
	}
	
//	public Message getMessage() {
//		//return message;
//	}
//	
//	public List<Message> getMessages() {
//		
//	}
	
}
