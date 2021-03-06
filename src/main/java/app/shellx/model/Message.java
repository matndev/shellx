package app.shellx.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="messages")
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8089787350563535412L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="messages_id")
	private long messageId;
	@Column(name="messages_author")
	private long messageAuthor;
	@Column(name="messages_receiver")
	private long messageReceiver;
	@Column(name="messages_content")
	private String messageContent;
	@Column(name="messages_visible")
	private boolean messageVisible;
	@Column(name="messages_enabled")
	private boolean messageEnabled;
	@Column(name="messages_date")
	private LocalDateTime messageDate;
	
//	@JsonBackReference
//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn(name = "messages_room")
	@Column(name = "messages_id_room")
	private long messageRoom;

	public Message() {}
	
	public Message(long messageAuthor, long messageReceiver, String messageContent, boolean messageVisible, LocalDateTime messageDate, long messageRoom) {
		this.messageAuthor = messageAuthor;
		this.messageReceiver = messageReceiver;
		this.messageContent = messageContent;
		this.messageVisible = messageVisible;
		this.messageDate = messageDate;
		this.messageRoom = messageRoom;
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

	public LocalDateTime getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(LocalDateTime messageDate) {
		this.messageDate = messageDate;
	}

	public boolean isMessageVisible() {
		return messageVisible;
	}

	public void setMessageVisible(boolean messageVisible) {
		this.messageVisible = messageVisible;
	}

	public long getMessageRoom() {
		return messageRoom;
	}

	public void setMessageRoom(long messageRoom) {
		this.messageRoom = messageRoom;
	}

	public boolean isMessageEnabled() {
		return messageEnabled;
	}

	public void setMessageEnabled(boolean messageEnabled) {
		this.messageEnabled = messageEnabled;
	}
	
	public String toString() {
		return messageContent;
	}
	
}
