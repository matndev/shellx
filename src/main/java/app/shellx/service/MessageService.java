package app.shellx.service;

import java.util.List;

import app.shellx.dto.MessageDto;
import app.shellx.model.Message;

public interface MessageService {

	public void add(MessageDto messageDto);
	
	public List<Message> findAll();
	
	public Message findById(long id);
	
	public void update(Message message);
	
	public void deleteById(long id);
	
	public void updateVisibility(long id, boolean setStateVisibility);
	
//	public Message getMessage();
//	
//	public List<Message> getMessages();
	
}
