package app.shellx.dao;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import app.shellx.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

	public User findByUsername(String username);
	public User findByEmail(String email);
}
