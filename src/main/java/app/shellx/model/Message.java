package app.shellx.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="messages")
public class Message implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="messages_id")
	private long messageId;
	@Column(name="messages_author")
	private String messageAuthor;
	@Column(name="messages_receiver")
	private String messageReceiver;
	@Column(name="messages_content")
	private String messageContent;
	@Column(name="messages_visible")
	private boolean messageVisible;
	@Column(name="messages_date")
	private LocalDate messageDate;
	
	protected Message() {}
	
	public Message(String messageAuthor, String messageReceiver, String messageContent, boolean messageVisible, LocalDate messageDate) {
		this.messageAuthor = messageAuthor;
		this.messageReceiver = messageReceiver;
		this.messageContent = messageContent;
		this.messageVisible = messageVisible;
		this.messageDate = messageDate;
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

	public boolean isMessageVisible() {
		return messageVisible;
	}

	public void setMessageVisible(boolean messageVisible) {
		this.messageVisible = messageVisible;
	}
	
	
}
