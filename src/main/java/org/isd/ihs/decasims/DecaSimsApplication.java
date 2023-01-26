package org.isd.ihs.decasims;

import java.util.ArrayList;
import java.util.List;

import org.isd.ihs.decasims.entity.CatalogItem;
import org.isd.ihs.decasims.entity.Order;
import org.isd.ihs.decasims.entity.OrderItem;
import org.isd.ihs.decasims.repository.CatalogItemRepository;
import org.isd.ihs.decasims.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Class DecaSimsApplication: Standard springboot application class wich acts as an entry point
 * for the application to start. Optionally, we can create entries into all the database tables we 
 * have in our project as well.
 * 
 */
@SuppressWarnings("unused")
@SpringBootApplication
public class DecaSimsApplication implements CommandLineRunner{

	/** The order repository. */
	@Autowired
	private OrderRepository orderRepository;

	/** The catalog item repository. */
	@Autowired
	private CatalogItemRepository catalogItemRepository;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(DecaSimsApplication.class, args);
	}

	/**
	 * Run.
	 *
	 * @param args the args
	 * @throws Exception the exception
	 */
	@Override
	public void run(String... args) throws Exception {

		// programmatic ways to insert test data into repository (not needed)
		// we just need user table populated with users (role, admin & user), and this
		//	can be done with h2 console easily at http://localhost:8080/h2-console
		/** 
		CatalogItem c1 = new CatalogItem("pkd-341", "t-shirt", "blah blah t-shirt", 19.99, 90, "sims.jpg");
		catalogItemRepository.save(c1);

		OrderItem oi1 = new OrderItem(1, 3.5, 1L);
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		orderItems.add(oi1);
		Order o1 = new Order(10L, "232fsdfer$dfd", 34.44);
		o1.addOrderItems(orderItems);
		orderRepository.save(o1);
		*/
	}
}



