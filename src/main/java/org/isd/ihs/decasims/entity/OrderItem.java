package org.isd.ihs.decasims.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * The Class OrderItem.
 */
@Entity
@Table(name = "order_items")
public class OrderItem {

	/** The order item id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_item_id", updatable = false, nullable = false, unique = true)
	private Long orderItemId;

	/** The quantity. */
	@Column(name = "quantity")
	private int quantity;

	/** The price. */
	@Column(name = "price")
	private double price;

	/** The order. */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "order_id", nullable = true)
	private Order order;

	/** The product id. */
	@JoinColumn(name = "product_id", nullable = false)
	private Long productId;

	/** The created date. */
	@CreationTimestamp
	@Column(name = "created_date")
	private Date createdDate;

	/** The last update date. */
	@Column(name = "last_update_date")
	@UpdateTimestamp
	private Date lastUpdateDate;

	/**
	 * Instantiates a new order item.
	 */
	public OrderItem()	{		
	}

	/**
	 * Instantiates a new order item.
	 *
	 * @param quantity the quantity
	 * @param price the price
	 * @param productId the product id
	 */
	public OrderItem(int quantity, double price, Long productId) {
		super();
		this.quantity = quantity;
		this.price = price;
		this.productId = productId;
	}

	/**
	 * Gets the order item id.
	 *
	 * @return the order item id
	 */
	public Long getOrderItemId() {
		return orderItemId;
	}

	/**
	 * Sets the order item id.
	 *
	 * @param orderItemId the new order item id
	 */
	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity.
	 *
	 * @param quantity the new quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Gets the order.
	 *
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * Sets the order.
	 *
	 * @param order the new order
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * Gets the product id.
	 *
	 * @return the product id
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * Sets the product id.
	 *
	 * @param productId the new product id
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
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
		return "OrderItem [orderItemId=" + orderItemId + ", quantity=" 
				+ quantity + ", price=" + price + ", productId="
				+ productId + ", createdDate=" + createdDate + ", lastUpdateDate="
				+ lastUpdateDate + "]";
	}
}



