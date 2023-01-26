package org.isd.ihs.decasims.service;

import org.isd.ihs.decasims.entity.User;
import org.isd.ihs.decasims.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * The Class UserService: An user service class simply forward the requests received to
 * specific repository (in this case UserRepository). Not much going on here. @Service 
 * indicates spring managed bean.
 */
@Service
public class UserService {

	/** The user repository. */
	private UserRepository userRepository;

	/**
	 * Instantiates a new user service.
	 *
	 * @param userRepository the user repository
	 */
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	/**
	 * Gets the user by username.
	 *
	 * @param username the username
	 * @return the user by username
	 */
	public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
	}
}
