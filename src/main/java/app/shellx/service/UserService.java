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

import app.shellx.dao.AuthorityRepository;
import app.shellx.dao.RoleRepository;
import app.shellx.dao.UserRepository;
import app.shellx.model.Authority;
import app.shellx.model.Role;
import app.shellx.model.User;

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
        //List<GrantedAuthority> authorities = buildUserAuthority(user.getAuthorities());
        //if you're implementing UserDetails you wouldn't need to call this method and instead return the User as it is
        //return buildUserForAuthentication(user, authorities);
        return user;

    }
    
    @Transactional
    public void delete() {
    	userRepository.deleteAll();
    }
    
    /*public User login(String username, String password) {
    	 try {
    	        Authentication request = new UsernamePasswordAuthenticationToken(username, password);
    	        Authentication result = am.authenticate(request);
    	        SecurityContextHolder.getContext().setAuthentication(result);
    	        break;
    	 } catch(AuthenticationException e) {
    	        System.out.println("Authentication failed: " + e.getMessage());
    	 }
    }*/

    /* // Converts user to spring.springframework.security.core.userdetails.User
    private User buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getEmail(), user.getPassword(), authorities, user.isAccountNonExpired(), user.getAvatar());
    }

    private List<GrantedAuthority> buildUserAuthority(Set<Authority> userRoles) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        // add user's authorities
        for (Authority userRole : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
        }

        List<GrantedAuthority> Result = new ArrayList()<GrantedAuthority>(setAuths);

        return Result;
    }*/
 
    public void add(User user, String role) {
    	user.setRole(roleService.findByRole(role));
    	//user.setRefRole(resRole.getId());
    	this.userRepository.save(user);
    	/*role.setAuthorities(authorities);
    	this.roleRepository.save(role);
    	for (Authority auth : authorities) {
    		auth.setRoles(role);
    		this.authorityRepository.save(auth);
    	}*/
    }
    
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
