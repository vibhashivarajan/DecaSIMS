package org.isd.ihs.decasims.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * The Class CatalogItem: CatalogItem entity class is a simple java object where the bean represents
 * an actual database table and each field represents the database column. The annotations and attributes 
 * indicates relationship, and other properties specific to each column. An Catalog item is each
 * item in deca sims store catalog.
 *
 * ref: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
 */
@Entity
@Table(name = "catalog_items")
public class CatalogItem {

	/** The product id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id", unique = true, nullable = false)
	private Long productId;

	/** The sku. */
	@Column(name = "sku", unique = true, nullable = false)
	private String sku;

	/** The product name. */
	@Column(name = "product_name", nullable = false)
	private String productName;

	/** The product desc. */
	@Column(name = "product_desc", nullable = true)
	private String productDesc;

	/** The price. */
	@Column(name = "price", nullable = false)
	private double price;

	/** The qty in hand. */
	@Column(name = "qty_in_hand", nullable = false)
	private int qtyInHand;

	/** The item photo name. */
	@Column(name = "item_photo", nullable = true)
	private String itemPhotoName;

	/** The created date. */
	@CreationTimestamp
	@Column(name = "created_date")
	private Date createdDate;

	/** The last update date. */
	@Column(name = "last_update_date")
	@UpdateTimestamp
	private Date lastUpdateDate;

	/**
	 * Instantiates a new catalog item.
	 */
	public CatalogItem()	{
	}

	/**
	 * Instantiates a new catalog item.
	 *
	 * @param sku the sku
	 * @param productName the product name
	 * @param productDesc the product desc
	 * @param price the price
	 * @param qtyInHand the qty in hand
	 * @param itemPhoto the item photo
	 */
	public CatalogItem(String sku, String productName, String productDesc,
			double price, int qtyInHand, String itemPhoto) {
		super();
		this.sku = sku;
		this.productName = productName;
		this.productDesc = productDesc;
		this.price = price;
		this.qtyInHand = qtyInHand;
		this.itemPhotoName = itemPhoto;
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
	 * Gets the sku.
	 *
	 * @return the sku
	 */
	public String getSku() {
		return sku;
	}

	/**
	 * Sets the sku.
	 *
	 * @param sku the new sku
	 */
	public void setSku(String sku) {
		this.sku = sku;
	}

	/**
	 * Gets the product name.
	 *
	 * @return the product name
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * Sets the product name.
	 *
	 * @param productName the new product name
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * Gets the product desc.
	 *
	 * @return the product desc
	 */
	public String getProductDesc() {
		return productDesc;
	}

	/**
	 * Sets the product desc.
	 *
	 * @param productDesc the new product desc
	 */
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
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
	 * Gets the qty in hand.
	 *
	 * @return the qty in hand
	 */
	public int getQtyInHand() {
		return qtyInHand;
	}

	/**
	 * Sets the qty in hand.
	 *
	 * @param qtyInHand the new qty in hand
	 */
	public void setQtyInHand(int qtyInHand) {
		this.qtyInHand = qtyInHand;
	}

	/**
	 * Gets the item photo name.
	 *
	 * @return the item photo name
	 */
	public String getItemPhotoName() {
		return itemPhotoName;
	}

	/**
	 * Sets the item photo name.
	 *
	 * @param itemPhotoName the new item photo name
	 */
	public void setItemPhotoName(String itemPhotoName) {
		this.itemPhotoName = itemPhotoName;
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
	 * Gets the last update date.
	 *
	 * @return the last update date
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "CatalogItem [productId=" + productId + ", sku=" + sku + ", productName=" + productName
				+ ", productDesc=" + productDesc + ", price=" + price + ", qtyInHand=" + qtyInHand + ", itemPhotoName="
				+ itemPhotoName + ", createdDate=" + createdDate + ", lastUpdateDate=" + lastUpdateDate + "]";
	}
}



