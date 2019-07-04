package app.shellx.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.shellx.model.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
	
	public Message findById(long id);
	
	public Message findByMessageIdAndMessageEnabledTrue(long id);
	
	public void deleteById(long id);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE messages SET messages_visible = :visibility WHERE messages_id = :id")
	public void updateVisibility(@Param("id") long id, @Param("visibility") boolean setStateVisibility);
}