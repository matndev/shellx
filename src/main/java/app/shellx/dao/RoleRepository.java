package app.shellx.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.shellx.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

	public Role findByRole(String role);
}
