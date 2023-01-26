package org.isd.ihs.decasims.repository;

import org.isd.ihs.decasims.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface OrderItemRepository:  java interface which represents an access to
 * database table of OrderItem (sprignboot jpa feature)
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>	{

}
