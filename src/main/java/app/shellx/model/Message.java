package app.shellx.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	private String messageAuthor;
	@Column(name="messages_receiver")
	private String messageReceiver;
	@Column(name="messages_content")
	private String messageContent;
	@Column(name="messages_visible")
	private boolean messageVisible;
	@Column(name="messages_enabled")
	private boolean messageEnabled;
	@Column(name="messages_date")
	private LocalDate messageDate;
	
//	@JsonBackReference
//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn(name = "messages_room")
	@Column(name = "messages_id_room")
	private int messageRoom;

	public Message() {}
	
	public Message(String messageAuthor, String messageReceiver, String messageContent, boolean messageVisible, LocalDate messageDate, int messageRoom) {
		this.messageAuthor = messageAuthor;
		this.messageReceiver = messageReceiver;
		this.messageContent = messageContent;
		this.messageVisible = messageVisible;
		this.messageDate = messageDate;
		this.messageRoom = messageRoom;
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

	public int getMessageRoom() {
		return messageRoom;
	}

	public void setMessageRoom(int messageRoom) {
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
