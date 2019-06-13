package app.shellx.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.shellx.dao.AuthorityRepository;
import app.shellx.model.Authority;

@Service
public class AuthorityService {
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	/*@Transactional(readOnly=true)
	public Set<Authority> findByRole(int role) {
		return authorityRepository.findByRole(role);//.orElse(null);
	}*/
	
	@Transactional
	public void addAll(Set<Authority> authorities) {
		authorityRepository.saveAll(authorities);
	}
}
