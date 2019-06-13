package app.shellx.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.shellx.dao.RoleRepository;
import app.shellx.model.Authority;
import app.shellx.model.Role;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Transactional(readOnly=true)
	public Role findByRole(String role) {
		return roleRepository.findByRole(role);//.orElse(null);
	}
	
	@Transactional
	public void addAll(Set<Role> roles) {
		roleRepository.saveAll(roles);
	}
}
