package app.shellx.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.shellx.annotation.EmailExistsException;
import app.shellx.dao.AuthorityRepository;
import app.shellx.dao.RoleRepository;
import app.shellx.dao.UserRepository;
import app.shellx.dto.UserDto;
import app.shellx.model.Authority;
import app.shellx.model.Role;
import app.shellx.model.User;

@Service
public class UserService implements UserDetailsService {

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private RoleService roleService;

	// LOAD BY EMAIL ADDRESS
    @Transactional(readOnly=true)
    public User loadUserByUsername(final String username) throws UsernameNotFoundException {

    	String email = username;
        User user = userRepository.findByEmail(email);
        Role role = user.getRole();
        user.setAuthorities(role.getAuthorities());
        return user;

    }
    
    @Transactional
    public void delete() {
    	userRepository.deleteAll();
    }
    
    
/*	##### REGISTRATION PART ##### */    
    
    @Transactional
    public User registerNewUserAccount(UserDto accountDto) 
      throws EmailExistsException {
         
        if (emailExists(accountDto.getEmail())) {   
            throw new EmailExistsException("There is an account with that email address:" + accountDto.getEmail());
        }
        User user = new User();    
        user.setUsername(accountDto.getUsername());
        user.setPassword(accountDto.getPassword());
        user.setEmail(accountDto.getEmail());
        user.setEnabled(true);
        user.setRole(roleService.findByRole("ROLE_ADMIN"));
        return userRepository.save(user);       
    }
    private boolean emailExists(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
    
/*	##### REGISTRATION END ##### */    
    
    
    @Transactional
    public void update(User user) {
    	this.userRepository.save(user);
    }
	
    public String getCurrentUser() {
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	String username;
    	
    	if (principal instanceof UserDetails) {
    		username = ((User)principal).getUsername();
    	} else {
    		username = principal.toString();
    	}
    	
    	return username;
    }
}
