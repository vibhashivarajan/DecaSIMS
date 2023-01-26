package org.isd.ihs.decasims.security;

import org.isd.ihs.decasims.entity.User;
import org.isd.ihs.decasims.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * The Class SimsUserDetailsServiceImpl: class just loads a given user bu username from
 * user database.
 * 
 */
public class SimsUserDetailsServiceImpl implements UserDetailsService {

	/** The user service. */
	@Autowired
	private UserService userService;

	/**
	 * Load user by username.
	 *
	 * @param username the username
	 * @return the user details
	 * @throws UsernameNotFoundException the username not found exception
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = userService.getUserByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("Could not find user");
		}
		return new SimsUserDetails(user);
	}
}


