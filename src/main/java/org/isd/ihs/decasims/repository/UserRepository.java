package org.isd.ihs.decasims.repository;

import java.util.List;

import org.isd.ihs.decasims.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
 
/**
 * The Interface UserRepository:  java interface which represents an access to
 * database table of Users (springboot jpa feature).
 */
public interface UserRepository extends JpaRepository<User, Long> {
 
    /**
     * Gets the user by user email.
     *
     * @param userEmail the user email
     * @return the user by user email
     */
    @Query("SELECT u FROM User u WHERE u.userEmail = :userEmail")
    User getUserByUserEmail(@Param("userEmail") String userEmail);
        
    //List<User> findAll();    
}
