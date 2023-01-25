package org.isd.ihs.decasims.repository;

import org.isd.ihs.decasims.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface OrderItemRepository.
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>	{

}