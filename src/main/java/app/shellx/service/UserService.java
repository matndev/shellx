package app.shellx.service;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.shellx.annotation.EmailExistsException;
import app.shellx.dao.UserRepository;
import app.shellx.dto.RoomDto;
import app.shellx.dto.UserDto;
import app.shellx.model.Role;
import app.shellx.model.Room;
import app.shellx.model.RoomUser;
import app.shellx.model.User;
import app.shellx.security.EmailNotFoundException;

@Service
public class UserService implements UserDetailsService {

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private RoleService roleService;

    @Transactional(readOnly=true)
    public User loadUserByUsername(final String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        Role role = user.getRole();
        user.setAuthorities(role.getAuthorities());
        return user;

    }
    
    // Server-side purpose
    @Transactional(readOnly=true)
    public User loadUserByEmail(final String email) throws EmailNotFoundException {

    	User user = userRepository.findByEmail(email);
        Role role = user.getRole();
        user.setAuthorities(role.getAuthorities());
        return user;
        
    } 
    
    // Client-side purpose
    @Transactional(readOnly=true)
    public UserDto loadUserById(final long id) {

        User user = userRepository.findById(id);
        if (user != null) {
        	UserDto userDto = new UserDto(user);
	        return userDto;
        }
        else {
        	return null;
        }

    } 
    
    @Transactional
    public void delete() {
    	userRepository.deleteAll();
    }
    
    
/*	##### REGISTRATION PART ##### */    
    
    @Transactional
    public User registerNewUserAccount(User user) 
      throws EmailExistsException {
         
        if (emailExists(user.getEmail())) {   
            throw new EmailExistsException("There is an account with that email address:" + user.getEmail());
        }  
        //user.setUsername(accountDto.getUsername());
        //user.setPassword(accountDto.getPassword());
        //user.setEmail(accountDto.getEmail());
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
    
	//GET All rooms by user ID
	@Transactional(readOnly = true)
	public Set<RoomDto> findRoomsByUserId(int id) {
		User user = this.userRepository.findById(id);
		if (user == null) {return null;}
		else {
		Set<RoomDto> roomsList = new HashSet<RoomDto>();
		Set<RoomUser> roomUser = user.getRooms();
		roomUser.forEach(room -> roomsList.add(new RoomDto(room.getRoom())));
		return roomsList;
		}
	}    
}
