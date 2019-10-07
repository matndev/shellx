package app.shellx.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class MessageDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7352317631349868845L;

	private long messageAuthor;
	private long messageReceiver;
	private String messageContent;
	private LocalDate messageDate;
	private long messageRoomId;
	
	public MessageDto() {
		
	}
	
	public long getMessageAuthor() {
		return messageAuthor;
	}

	public void setMessageAuthor(long messageAuthor) {
		this.messageAuthor = messageAuthor;
	}

	public long getMessageReceiver() {
		return messageReceiver;
	}

	public void setMessageReceiver(long messageReceiver) {
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

	public long getMessageRoomId() {
		return messageRoomId;
	}

	public void setMessageRoomId(long messageRoomId) {
		this.messageRoomId = messageRoomId;
	}
	
	public String toString() {
		return "MESSAGE toString() : \n"+
				"Content : " + this.messageContent +"\n"+
				"Author : " + this.messageAuthor +"\n"+
				"Receiver : " + this.messageReceiver +"\n"+
				"Date : " + this.messageDate +"\n"+
				"Room : " + this.messageRoomId;
	}
}
