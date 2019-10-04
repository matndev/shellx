package app.shellx.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class MessageDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7352317631349868845L;

	private String messageAuthor;
	private String messageReceiver;
	private String messageContent;
	private LocalDate messageDate;
	private int messageRoomId;
	
	public MessageDto() {
		
	}
	
	public String getMessageAuthor() {
		return messageAuthor;
	}

	public void setMessageAuthor(String messageAuthor) {
		this.messageAuthor = messageAuthor;
	}

	public String getMessageReceiver() {
		return messageReceiver;
	}

	public void setMessageReceiver(String messageReceiver) {
		this.messageReceiver = messageReceiver;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public LocalDate getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(LocalDate messageDate) {
		this.messageDate = messageDate;
	}

	public int getMessageRoom() {
		return messageRoomId;
	}

	public void setMessageRoom(int messageRoomId) {
		this.messageRoomId = messageRoomId;
	}
	
	public String toString() {
		return "MESSAGE toString() : \n"+
				"Content : " + this.messageContent +"\n"+
				"Author : " + this.messageAuthor +"\n"+
				"Room : " + this.messageRoomId;
	}
}
