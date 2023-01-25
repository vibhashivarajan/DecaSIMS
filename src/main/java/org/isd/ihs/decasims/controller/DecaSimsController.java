package org.isd.ihs.decasims.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.isd.ihs.decasims.entity.CatalogItem;
import org.isd.ihs.decasims.entity.Order;
import org.isd.ihs.decasims.entity.OrderItem;
import org.isd.ihs.decasims.entity.User;
import org.isd.ihs.decasims.service.InventoryService;
import org.isd.ihs.decasims.service.OrderService;
import org.isd.ihs.decasims.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.isd.ihs.decasims.security.SimsUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * The Class DecaSimsController.
 */
@Controller
public class DecaSimsController {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(DecaSimsController.class);

	/** The Constant CART_SESSION. */
	private static final String CART_SESSION = "userCartContent";

	/** The inventory service. */
	private InventoryService inventoryService;

	/** The order service. */
	private OrderService orderService;

	/** The user service. */
	private UserService userService;

	/**
	 * Instantiates a new deca sims controller.
	 *
	 * @param inventoryService the inventory service
	 * @param orderService the order service
	 * @param userService the user service
	 */
	public DecaSimsController(InventoryService inventoryService,
			OrderService orderService, UserService userService) {
		super();
		this.inventoryService = inventoryService;
		this.orderService = orderService;
		this.userService = userService;
	}

	/**
	 * Register.
	 *
	 * @return the string
	 */
	@GetMapping("/")
	public String register()	{
		return "redirect:/inventory";
	}

	/**
	 * List inventory.
	 *
	 * @param model the model
	 * @param authentication the authentication
	 * @param session the session
	 * @param request the request
	 * @return the string
	 */
	// handler method to handle list students and return mode and view
	@GetMapping("/inventory")
	public String listInventory(Model model, Authentication authentication,
			HttpSession session, HttpServletRequest request) {

		String loggedInUserName = authentication.getName();
		boolean isAdminUser = isAdminRole(authentication);

		logger.info("Inside method listInventory(...) for user {}, isAdmin {}",
				loggedInUserName, isAdminUser);

		model.addAttribute("catalogItems", inventoryService.getAllCatalogItems());		

		// return different view template based on if logged-in user is admin or user
		if(isAdminUser)	{
			return "inventory_admin";
		}

		return "inventory_user";
	}

	/**
	 * Creates the catalog item.
	 *
	 * @param model the model
	 * @param authentication the authentication
	 * @return the string
	 */
	@GetMapping("/inventory_admin/new")
	public String createCatalogItem(Model model, Authentication authentication) {

		String loggedInUserName = authentication.getName();
		boolean isAdminUser = isAdminRole(authentication);
		logger.info("Inside method createCatalogItem(...) for user {}, isAdmin {}",
				loggedInUserName, isAdminUser);

		if(!isAdminUser)	{
			return "error";
		}	

		// create catalog object to hold catalog form data
		CatalogItem catalogItem = new CatalogItem();
		model.addAttribute("catalogItem", catalogItem);
		return "create_inventory";		
	}

	/**
	 * Save catalog item.
	 *
	 * @param catalogItem the catalog item
	 * @param authentication the authentication
	 * @return the string
	 */
	@PostMapping("/inventory_admin")
	public String saveCatalogItem(@ModelAttribute("catalogItem") CatalogItem catalogItem,
			Authentication authentication) {

		String loggedInUserName = authentication.getName();
		boolean isAdminUser = isAdminRole(authentication);
		logger.info("Inside method saveCatalogItem(...) for user {}, isAdmin {}",
				loggedInUserName, isAdminUser);

		if(!isAdminUser)	{
			return "error";
		}	

		inventoryService.saveCatalogItem(catalogItem);
		return "redirect:/inventory";
	}

	/**
	 * Edits the catalog item form.
	 *
	 * @param id the id
	 * @param model the model
	 * @param authentication the authentication
	 * @return the string
	 */
	@GetMapping("/inventory_admin/edit/{id}")
	public String editCatalogItemForm(@PathVariable Long id, Model model,
			Authentication authentication) {

		String loggedInUserName = authentication.getName();
		boolean isAdminUser = isAdminRole(authentication);
		logger.info("Inside method editCatalogItemForm(...) for user {}, isAdmin {}",
				loggedInUserName, isAdminUser);

		if(!isAdminUser)	{
			return "error";
		}

		model.addAttribute("catalogItem", inventoryService.getCatalogItemById(id));
		return "edit_inventory";
	}

	/**
	 * Update catalog item.
	 *
	 * @param id the id
	 * @param catalogItem the catalog item
	 * @param model the model
	 * @param authentication the authentication
	 * @return the string
	 */
	@PostMapping("/inventory_admin/{id}")
	public String updateCatalogItem(@PathVariable Long id,
			@ModelAttribute("catalogItem") CatalogItem catalogItem, Model model,
			Authentication authentication) {

		String loggedInUserName = authentication.getName();
		boolean isAdminUser = isAdminRole(authentication);
		logger.info("Inside method updateCatalogItem(...) for user {}, isAdmin {}",
				loggedInUserName, isAdminUser);

		if(!isAdminUser)	{
			return "error";
		}

		// get catalog item from database by id
		CatalogItem existingCatalogItem = inventoryService.getCatalogItemById(id);

		// load all field from database for this catalog
		existingCatalogItem.setProductId(id);
		existingCatalogItem.setSku(catalogItem.getSku());
		existingCatalogItem.setProductName(catalogItem.getProductName());
		existingCatalogItem.setProductDesc(catalogItem.getProductDesc());
		existingCatalogItem.setPrice(catalogItem.getPrice());
		existingCatalogItem.setQtyInHand(catalogItem.getQtyInHand());
		existingCatalogItem.setItemPhotoName(catalogItem.getItemPhotoName());

		// save updated catalog item object
		inventoryService.updateCatalogItem(existingCatalogItem);
		return "redirect:/inventory";		
	}

	/**
	 * Delete catalog item.
	 *
	 * @param id the id
	 * @param authentication the authentication
	 * @return the string
	 */
	// handler method to handle delete student request	
	@GetMapping("/inventory_admin/{id}")
	public String deleteCatalogItem(@PathVariable Long id,
			Authentication authentication) {

		String loggedInUserName = authentication.getName();
		boolean isAdminUser = isAdminRole(authentication);
		logger.info("Inside method deleteCatalogItem(...) for user {}, isAdmin {}",
				loggedInUserName, isAdminUser);

		if(!isAdminUser)	{
			return "error";
		}

		inventoryService.deleteCatalogItem(id);
		return "redirect:/inventory";
	}	

	/**
	 * Adds the to cart.
	 *
	 * @param id the id
	 * @param model the model
	 * @param authentication the authentication
	 * @param session the session
	 * @return the string
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/addToCart/{id}")
	public String addToCart(@PathVariable Long id, Model model,
			Authentication authentication, HttpSession session) {

		String loggedInUserName = authentication.getName();
		boolean isAdminUser = isAdminRole(authentication);
		logger.info("Inside method addToCart(...) for user {}, isAdmin {}",
				loggedInUserName, isAdminUser);

		List<Long> cartItems = (List<Long>) session.getAttribute(CART_SESSION);
		if (cartItems == null) {	// if user just logged-in or new browser
			cartItems = new ArrayList<Long>();			
		}
		cartItems.add(id); // add supplied product id to cart
		session.setAttribute(CART_SESSION, cartItems);		
		model.addAttribute(CART_SESSION, cartItems);

		model.addAttribute("catalogItems", inventoryService.getAllCatalogItems());		
		return "inventory_user";
	}

	/**
	 * Adds the to cart.
	 *
	 * @param model the model
	 * @param authentication the authentication
	 * @param session the session
	 * @return the string
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/checkout")
	public String addToCart(Model model,
			Authentication authentication, HttpSession session) {

		String loggedInUserName = authentication.getName();
		boolean isAdminUser = isAdminRole(authentication);
		logger.info("Inside method addToCart(...) for user {}, isAdmin {}",
				loggedInUserName, isAdminUser);

		List<Long> cartItemIds = (List<Long>) session.getAttribute(CART_SESSION);

		if (cartItemIds == null) {	// if user just logged-in or new browser
			return "error";
		}

		session.setAttribute(CART_SESSION, cartItemIds);		
		model.addAttribute(CART_SESSION, cartItemIds);

		List<CatalogItem> checkoutList = new ArrayList<CatalogItem>();
		double orderTotal = 0;
		// only get those catalog items which are added to cart
		for(Long cartItemId: cartItemIds) {
			CatalogItem cartItem = inventoryService.getCatalogItemById(cartItemId);
			checkoutList.add(cartItem);
			orderTotal = orderTotal + cartItem.getPrice();
		}

		model.addAttribute("catalogItems", checkoutList);		
		model.addAttribute("totalPrice", roundValue(orderTotal));		

		return "place_order";
	}


	/**
	 * Place order.
	 *
	 * @param model the model
	 * @param authentication the authentication
	 * @param session the session
	 * @return the string
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/placeorder")
	public String placeOrder(Model model,
			Authentication authentication, HttpSession session) {

		String loggedInUserName = authentication.getName();
		boolean isAdminUser = isAdminRole(authentication);
		logger.info("Inside method placeOrder(...) for user {}, isAdmin {}",
				loggedInUserName, isAdminUser);

		List<Long> cartItemIds = (List<Long>) session.getAttribute(CART_SESSION);

		if (cartItemIds == null) {	// if user just logged-in or new browser
			return "error";
		}

		double orderTotal = 0;

		// create list of order items & an order
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		Order order = new Order();

		// only get those catalog items which are in session after checkout
		for(Long cartItemId: cartItemIds) {
			CatalogItem cartItem = inventoryService.getCatalogItemById(cartItemId);
			OrderItem orderItem = new OrderItem(1, cartItem.getPrice(), cartItem.getProductId());
			orderItems.add(orderItem);
			orderTotal = orderTotal + cartItem.getPrice();				
		}

		User loggedInUser = userService.getUserByUsername(loggedInUserName);
		order.setUserId(loggedInUser.getId());
		order.setTotalPrice(roundValue(orderTotal));
		order.addOrderItems(orderItems);

		Order savedOrder = orderService.saveOrder(order);

		model.addAttribute("savedOrder", savedOrder);
		model.addAttribute("userEmail", loggedInUser.getUserEmail());	


		List<Order> allOrders = orderService.getAllOrders();
		model.addAttribute("allOrders", allOrders);		

		// clear the cart stored in session
		session.setAttribute(CART_SESSION, null);
		return "order_history";
	}


	/**
	 * Checks if is admin role.
	 *
	 * @param authentication the authentication
	 * @return true, if is admin role
	 */
	private boolean isAdminRole(Authentication authentication)	{
		return ((SimsUserDetails) authentication.getPrincipal()).getAuthorities().stream().anyMatch(
				a -> a.getAuthority().equalsIgnoreCase("admin"));
	}

	/**
	 * Round value.
	 *
	 * @param totalPrice the total price
	 * @return the double
	 */
	private double roundValue(double totalPrice) {
		BigDecimal bd = BigDecimal.valueOf(totalPrice);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}





