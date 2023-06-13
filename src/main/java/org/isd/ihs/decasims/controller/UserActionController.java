//Vibha Shivarajan
//Advanced Java Topics DECA SIMS 

package org.isd.ihs.decasims.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.isd.ihs.decasims.entity.User;

import org.isd.ihs.decasims.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.isd.ihs.decasims.security.SimsUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * The Class UserActionController: This is the main controller class which receives the requests coming from the browser. 
 * It serves an entrypoint for each API action related to the user (creation, edit and deletes). This class uses 
 * user service to exchange data with database. This class is instantiated and managed by the spring framework.
 */
@Controller
public class UserActionController {

	/**  The logger: standard logger to log everything we need to log within the application. */
	Logger logger = LoggerFactory.getLogger(UserActionController.class);

	/** The user service. */
	private UserService userService;

	/**
	 * Instantiates a new user action controller: In this constructor we set order service,
	 *  and user service.
	 *
	 * @param userService the user service
	 */
	public UserActionController(UserService userService) {
		super();
		this.userService = userService;
	}

	/**
	 * Current users: this returns all users of the system (user and admin role)
	 *
	 * @param model the model
	 * @param errMsg the err msg
	 * @param authentication the authentication
	 * @param session the session
	 * @param request the request
	 * @return the string
	 */
	@GetMapping("/manage_users")
	public String currentUsers(Model model, String errMsg, Authentication authentication,
			HttpSession session, HttpServletRequest request) {
		logger.info(">>>>>>>>>>>>>>>>>>>>>> /manage_users");

		boolean isAdminUser = isAdminRole(authentication);
		model.addAttribute("isAdminUser", isAdminUser);

		if (!isAdminUser) {
			setAdminOnlyMessage(model);
			return "error";
		}

		model.addAttribute("users", userService.getAllRegisteredUsers());

		return "manage_users";
	}

	/**
	 * Register : This endpoint is used to register new user with blank form (accesible only to admin)
	 *
	 * @param model the model
	 * @param errMsg the err msg
	 * @param authentication the authentication
	 * @param session the session
	 * @param request the request
	 * @return the string
	 */
	@GetMapping("/register")
	public String register(Model model, String errMsg, Authentication authentication,
			HttpSession session, HttpServletRequest request) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>> /register");

		boolean isAdminUser = isAdminRole(authentication);
		model.addAttribute("isAdminUser", isAdminUser);

		// create new User object to hold user form data
		User user = new User();
		model.addAttribute("user", user);

		return "user_update";
	}

	/**
	 * Update user: api to add or update user from html form and save it to database.
	 *
	 * @param user the user
	 * @param model the model
	 * @param authentication the authentication
	 * @return the string
	 */
	@PostMapping("/user/update")
	public String updateUser(@ModelAttribute("user") User user, Model model, 
			Authentication authentication) {

		String loggedInUserName = authentication.getName();
		boolean isAdminUser = isAdminRole(authentication);
		model.addAttribute("isAdminUser", isAdminUser);

		logger.info("Inside method updateUser(...) for user {}, isAdmin {}",
				loggedInUserName, isAdminUser);

		if (!isAdminUser) {
			setAdminOnlyMessage(model);
			return "error";
		}

		// if user id is present (which means existing user or new user otherwise)
		if((user.getId() == null) && userService.getUserByEmail(
				user.getUserEmail()) != null)	{
			model.addAttribute("errorMsg", "User with that email already exists.");
			return "error";
		}

		userService.saveUser(user);
		return "redirect:/manage_users";
	}

	/**
	 * Delete user: delete a user in database by id (main column in user table)
	 *
	 * @param id the id
	 * @param model the model
	 * @param authentication the authentication
	 * @return the string
	 */
	@GetMapping("/user/{id}/delete")
	public String deleteUser(@PathVariable Long id, Model model,
			Authentication authentication) {

		String loggedInUserName = authentication.getName();
		boolean isAdminUser = isAdminRole(authentication);
		model.addAttribute("isAdminUser", isAdminUser);

		logger.info("Inside method deleteUser(...) for user {}, isAdmin {}",
				loggedInUserName, isAdminUser);

		if (!isAdminUser) {
			setAdminOnlyMessage(model);
			return "error";
		}

		userService.deleteUser(id);
		return "redirect:/manage_users";
	}

	/**
	 * Edits the user: api called when edit user is clicked. This retrieve given user from database
	 * and populated edit form 
	 *
	 * @param id the id
	 * @param model the model
	 * @param authentication the authentication
	 * @return the string
	 */
	@GetMapping("/user/{id}/edit")
	public String editUser(@PathVariable Long id, Model model,
			Authentication authentication) {

		String loggedInUserName = authentication.getName();
		boolean isAdminUser = isAdminRole(authentication);
		logger.info("Inside method editUser(...) for user {}, isAdmin {}",
				loggedInUserName, isAdminUser);
		model.addAttribute("isAdminUser", isAdminUser);

		if (!isAdminUser) {
			setAdminOnlyMessage(model);
			return "error";
		}

		User user = userService.getUserById(id);		
		model.addAttribute("user", user);
		return "user_update";
	}



	/**
	 * Checks if is admin role: utility method to retrieve if the logged in
	 *  user is an admin role or not 
	 * 
	 * ref: https://www.baeldung.com/spring-security-check-user-role 
	 *
	 * @param authentication the authentication
	 * @return true, if is admin role
	 */
	private boolean isAdminRole(Authentication authentication) {
		if(authentication == null)	{
			return false;
		}

		return ((SimsUserDetails) authentication.getPrincipal())
				.getAuthorities().stream().anyMatch(
						a -> a.getAuthority().equalsIgnoreCase("admin"));
	}

	/**
	 * Sets the admin only message: just set some canned message to model before returning the flow to page
	 *
	 * @param model the new admin only message
	 */
	private void setAdminOnlyMessage(Model model) {
		model.addAttribute("errorMsg", 
				"Only admin user can perform this action.");
	}
}



