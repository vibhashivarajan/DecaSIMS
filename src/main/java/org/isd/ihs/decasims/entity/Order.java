package org.isd.ihs.decasims.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * The Class Order: Order entity class is a simple java object where the object represents an actual
 * database table and each field represents the actual database column. The annotations and attributes 
 * indicates relationship, and other properties specific to each column. This class represents a 
 * row in order table. It gets created as user place order for items in deca store. Note, each order
 * can ave mutiple order items associated with it (one to many relatonship)
 *
 * ref: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
 */
@Entity
@Table(name = "orders")
public class Order {

	/** The order id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id", updatable = false, nullable = false, unique = true)
	private Long orderId;

	/** The order items. */
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();

	/** The user id. */
	@Column(name = "user_id", nullable = false)
	private Long userId;

	/** The session id. */
	@Column(name = "session_id")
	private String sessionId;

	/** The total price. */
	@Column(name = "total_price")
	private Double totalPrice;

	/** The created date. */
	@CreationTimestamp
	@Column(name = "created_date")
	private Date createdDate;

	/** The last update date. */
	@Column(name = "last_update_date")
	@UpdateTimestamp
	private Date lastUpdateDate;

	/**
	 * Instantiates a new order.
	 */
	public Order()	{		
	}

	/**
	 * Instantiates a new order.
	 *
	 * @param userId the user id
	 * @param sessionId the session id
	 * @param totalPrice the total price
	 */
	public Order(Long userId, String sessionId, Double totalPrice)	{
		super();
		this.userId = userId;
		this.sessionId = sessionId;
		this.totalPrice = totalPrice;
	}

	/**
	 * Gets the order id.
	 *
	 * @return the order id
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * Sets the order id.
	 *
	 * @param orderId the new order id
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * Gets the order items.
	 *
	 * @return the order items
	 */
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	/**
	 * Adds the order items.
	 *
	 * @param orderItems the order items
	 */
	public void addOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
		orderItems.forEach(orderItem -> orderItem.setOrder(this));		
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Gets the session id.
	 *
	 * @return the session id
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * Sets the session id.
	 *
	 * @param sessionId the new session id
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * Gets the total price.
	 *
	 * @return the total price
	 */
	public Double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * Sets the total price.
	 *
	 * @param totalPrice the new total price
	 */
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * Gets the created date.
	 *
	 * @return the created date
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * Sets the created date.
	 *
	 * @param createdDate the new created date
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Gets the last update date.
	 *
	 * @return the last update date
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * Sets the last update date.
	 *
	 * @param lastUpdateDate the new last update date
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderItems=" + orderItems + 
				", userId=" + userId + ", sessionId=" + sessionId + ", totalPrice=" + 
				totalPrice + ", createdDate=" + createdDate + ", lastUpdateDate="
				+ lastUpdateDate + "]";
	}
}



