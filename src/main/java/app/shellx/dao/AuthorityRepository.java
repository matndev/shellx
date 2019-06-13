package app.shellx.dao;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.shellx.model.Authority;
import app.shellx.model.Role;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Integer> {

	//public Set<Authority> findByRole(int role);
}
