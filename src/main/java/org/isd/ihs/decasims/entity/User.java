package org.isd.ihs.decasims.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class User: User entity class is a simple java object where the bean represents
 * an actual database table and each field represents the database column. The annotations and
 * attributes indicates relationship, and other properties specific to each column. An User
 * item is each user stored in deca sim system. Current we only have two users.
 *
 * ref: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
 */
@Entity
@Table(name = "users")
public class User {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false, unique = true)
	private Long id;

	/** The student id. */
	@Column(name = "isd_student_id", nullable = false, unique = true)
	private Long studentId;

	/** The user email. */
	@Column(name = "user_email", nullable = false, unique = true)
	private String userEmail;

	/** The password. */
	@Column(name = "password")
	private String password;

	/** The role. */
	@Column(name = "role")
	private String role;

	/** The enabled. */
	@Column(name = "enabled")
	private boolean enabled;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Gets the student id.
	 *
	 * @return the student id
	 */
	public Long getStudentId() {
		return studentId;
	}

	/**
	 * Sets the student id.
	 *
	 * @param studentId the new student id
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	/**
	 * Gets the user email.
	 *
	 * @return the user email
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * Sets the user email.
	 *
	 * @param userEmail the new user email
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Checks if is enabled.
	 *
	 * @return true, if is enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled the new enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * To string: returns string representation of this object.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", userEmail=" + userEmail
				+ ", password=" + password + ", role=" + role + ", enabled=" + enabled + "]";
	}
}



