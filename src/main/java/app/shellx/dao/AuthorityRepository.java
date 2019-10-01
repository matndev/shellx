package app.shellx.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.shellx.model.Authority;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Integer> {

	//public Set<Authority> findByRole(int role);
}
