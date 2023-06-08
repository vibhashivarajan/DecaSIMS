package org.isd.ihs.decasims.service;

import java.util.List;

import org.isd.ihs.decasims.entity.User;
import org.isd.ihs.decasims.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The Class UserService: An user service class simply forward the requests received to
 * specific repository (in this case UserRepository). Not much going on here. @Service 
 * indicates spring managed bean.
 */
@Service
public class UserService {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(UserService.class);

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
	 * Gets the user by email.
	 *
	 * @param email the email
	 * @return the user by email
	 */
	public User getUserByEmail(String email) {
		return userRepository.getUserByUserEmail(email);
	}
	
	public User getUserById(Long id) {
		return userRepository.getOne(id);
	}

	/**
	 * Gets the all registered users.
	 *
	 * @return the all registered users
	 */
	public List<User> getAllRegisteredUsers() {
		return userRepository.findAll();
	}

	/**
	 * Save user.
	 *
	 * @param user the user
	 */
	public void saveUser(User user)	{
		user.setPassword(new BCryptPasswordEncoder().encode(
				user.getPassword()));
		userRepository.save(user);
		logger.info("Successfully saved user {} to database!", user);
	}
	
	public void deleteUser(Long id)	{
		userRepository.deleteById(id);
		logger.info("Successfully delete user id {} from database!", id);
	}
	
	
	
}



