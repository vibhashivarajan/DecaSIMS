package org.isd.ihs.decasims.repository;

import org.isd.ihs.decasims.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
 
/**
 * The Interface UserRepository:  java interface which represents an access to
 * database table of Users (springboot jpa feature)
 */
public interface UserRepository extends JpaRepository<User, Long> {
 
    /**
     * Gets the user by username.
     *
     * @param username the username
     * @return the user by username
     */
    @Query("SELECT u FROM User u WHERE u.username = :username")
    public User getUserByUsername(@Param("username") String username);
}
