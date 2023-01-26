package org.isd.ihs.decasims.service;

import java.util.List;

import org.isd.ihs.decasims.entity.Order;
import org.isd.ihs.decasims.repository.OrderRepository;
import org.springframework.stereotype.Service;

/**
 * The Class OrderService: An order service class simply forward the requests received to
 * specific repository (in this case OrderRepository). Not much going on here. @Service 
 * indicates spring managed bean.
 *
 */
@Service
public class OrderService {

	/** The order repository. */
	private OrderRepository orderRepository;

	/**
	 * Instantiates a new order service.
	 *
	 * @param orderRepository the order repository
	 */
	public OrderService(OrderRepository orderRepository) {
		super();
		this.orderRepository = orderRepository;
	}

	/**
	 * Gets the all orders.
	 *
	 * @return the all orders
	 */
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}


	/**
	 * Save order.
	 *
	 * @param order the order
	 * @return the order
	 */
	public Order saveOrder(Order order) {
		return orderRepository.save(order);
	}
}


