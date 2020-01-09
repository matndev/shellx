package app.shellx.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.shellx.dao.NotificationRepository;
import app.shellx.dao.RoomUserRepository;
import app.shellx.model.Notification;
import app.shellx.model.RoomUser;
import app.shellx.model.RoomUserId;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;
	
	// Refactoring : TO DELETE
	@Autowired
	private RoomUserRepository roomUserRepository;
	
	@Transactional
	public boolean add(Notification notif) {
		this.notificationRepository.save(notif);
		return true;
	}
	
	@Transactional
	public Set<Notification> getAll(long id) {
		return this.notificationRepository.findAllByUser(id);
	}
	
	@Transactional
	public void deleteInvitationType(long idRoom, long idUser) {
		this.notificationRepository.deleteByRoomAndUser(idRoom, idUser);
	}
	
	@Transactional
	public boolean deleteById(long idNotif) {
		// Refactoring : BAD goal : should only delete notification
		Notification notif = this.notificationRepository.findById(idNotif);
		if (notif.getType().equals("invite")) {
			RoomUserId id = new RoomUserId(notif.getRoom(), notif.getUser());
			RoomUser roomUser = roomUserRepository.findById(id).orElseThrow(NullPointerException::new);
			this.roomUserRepository.delete(roomUser);
		}
		this.notificationRepository.deleteById(idNotif);
		return this.notificationRepository.existsById(idNotif);
	}

}
