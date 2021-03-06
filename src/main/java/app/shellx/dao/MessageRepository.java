package app.shellx.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.shellx.model.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
	
	public List<Message> findAll();
	
	@Modifying
	@Query(nativeQuery = true, value = "SELECT * FROM messages WHERE messages_id_room = :id")
	public List<Message> findAllByRoom(@Param("id") long id);
	
	public Message findById(long id);
	
	public Message findByMessageIdAndMessageEnabledTrue(long id);
	
	public void deleteById(long id);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE messages SET messages_visible = :visibility WHERE messages_id = :id")
	public void updateVisibility(@Param("id") long id, @Param("visibility") boolean setStateVisibility);
}