package org.isd.ihs.decasims.repository;

import org.isd.ihs.decasims.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface OrderRepository:  java interface which represents an access to
 * database table of Orders (springboot jpa feature)
 */
public interface OrderRepository extends JpaRepository<Order, Long>{

}


