package app.shellx.dao;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.shellx.model.Notification;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {

	public Set<Notification> findAllByUser(long id);
	
	public void deleteByRoomAndUser(long idRoom, long idUser);
	
	public Notification findById(long idNotif);
}
