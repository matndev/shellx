package app.shellx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.shellx.dao.MessageRepository;
import app.shellx.dto.MessageDto;
import app.shellx.model.Message;
import app.shellx.model.Room;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private RoomService roomService;
	
	/*@Autowired
	private Message message;*/
	
	/*@Autowired
	public MessageServiceImpl(MessageRepository<Message> messageRepository) {
		this.messageRepository = messageRepository;
	}*/
	
	@Transactional
	public void add(MessageDto messageDto) throws NullPointerException {
		Message message = new Message();
		message.setMessageAuthor(messageDto.getMessageAuthor());
		message.setMessageReceiver(messageDto.getMessageReceiver());
		message.setMessageContent(messageDto.getMessageContent());
		message.setMessageDate(messageDto.getMessageDate());
		message.setMessageVisible(true);
		message.setMessageEnabled(true);
		try {
			Room room = roomService.findRoomById(messageDto.getMessageRoom());
			message.setMessageRoom(room);
			this.messageRepository.save(message);
		} catch(Exception e) {
			// LOGGER
			System.out.println("Error : message can't be saved");
		}
	}
	
	@Transactional(readOnly = true)
	public List<Message> findAll() {
		return this.messageRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Message findById(long id) {
		return this.messageRepository.findByMessageIdAndMessageEnabledTrue(id);
	}
	
	@Transactional
	public void update(Message message) {
		this.messageRepository.save(message);
	}
	
	@Transactional
	public void deleteById(long id) {
		this.messageRepository.deleteById(id);
	}
	
	@Transactional
	public void updateVisibility(long id, boolean setStateVisibility) {
		this.messageRepository.updateVisibility(id, setStateVisibility);
	}
	
}
