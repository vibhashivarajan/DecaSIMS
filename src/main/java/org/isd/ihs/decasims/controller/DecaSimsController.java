//Vibha Shivarajan
//Advanced Java Topics DECA SIMS 

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
 * The Class DecaSimsController: This is the main controller class which receives the requests coming from the browser. 
 * It serves an entrypoint for each API action. This class has other dependences like inventory service, order service, and user
 * service which it utilizes to exchange data with database. This class is instantiated and managed by the spring framework.
 */
@Controller
public class DecaSimsController {

    /** The logger: standard logger to log everything we need to log within the application */
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
     * Instantiating a new deca sims controller: In this constructor we set inventory service, order service, and user service.
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
    public String register() {
        return "redirect:/inventory";
    }

    /**
     * List inventory: This controller method which queries all catalog items from the database table and sets the list of 
     * those catalog items to the model object. It returns the view name "inventory_user" (thymeleaf html template) which contains 
     * html and code to display whatever is put into the model object for us.
     *
     * @param model the model
     * @param authentication the authentication
     * @param session the session
     * @param request the request
     * @return the string
     */
    //
    @GetMapping("/inventory")
    public String listInventory(Model model, Authentication authentication,
        HttpSession session, HttpServletRequest request) {

        String loggedInUserName = authentication.getName();
        boolean isAdminUser = isAdminRole(authentication);

        logger.info("Inside method listInventory(...) for user {}, isAdmin {}",
            loggedInUserName, isAdminUser);

        model.addAttribute("catalogItems", inventoryService.getAllCatalogItems());

        // return different view template based on if logged-in user is admin or user
        if (isAdminUser) {
            return "inventory_admin";
        }

        return "inventory_user";
    }

    /**
     * Creates the catalog item: First this method check of this user is admin, if not it redirects the user to
     * error page (as editing catalog is admin only action). This controller method is used for creating new
     * CatalogItem object. We create empty catalog item object and set it in model and the html page will display
     * catalog item form field as empty (as intended) as user will be filling-in new catalog item details thereon.
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

        if (!isAdminUser) {
            return "error";
        }

        // create catalog object to hold catalog form data
        CatalogItem catalogItem = new CatalogItem();
        model.addAttribute("catalogItem", catalogItem);
        return "create_inventory";
    }

    /**
     * Save catalog item: First this method check of this user is admin, if not it redirects the user to
     * error page (as saving the catalog is admin only action). This controller method get called with the
     * CatalogItem object passed to it from the earlier page. We then save the catalog object to the database,
     * calling/redirecting back to the inventory page (which will load all the items in the inventory including
     * the new one we just saved)
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

        if (!isAdminUser) {
            return "error";
        }

        inventoryService.saveCatalogItem(catalogItem);
        return "redirect:/inventory";
    }

    /**
     * Edits the catalog item form:  First this method check of this user is admin, if not it redirects the user to
     * error page (as fetching the catalog for edit is admin only action). This controller method gets called with
     * the unique CatalogItem item id passed to it from the earlier page. We then query the catalog database for
     * the specific, catalog object product id returning the saved catalog item back to the html page. The page once
     * receives the item, it will display the catalog item form with pre-populated values (which method had just set)
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

        if (!isAdminUser) {
            return "error";
        }

        model.addAttribute("catalogItem", inventoryService.getCatalogItemById(id));
        return "edit_inventory";
    }

    /**
     * Update catalog item: First this method check of this user is admin, if not it redirects the user to
     * error page (as updating the catalog is admin only action). This controller method gets called with the 
     * updated CatalogItem item object passed to it from the earlier page. We then query the catalog database
     * for the specific catalog object by product id and we then proceed updating every field of the catalog
     * item object with the fields we received the user. We then proceed to save the updated catalog item to the
     * database. We then call/redirect back to the inventory page (which will load all the items in the inventory
     * including the new updates we just made to the specific catalog item. 
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

        if (!isAdminUser) {
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
     * Delete catalog item:  First this method check of this user is admin, if not it redirects the user to
     * error page (as deleting the catalog is admin only action). This controller method gets called with the 
     * unique CatalogItem item product id passed to it from the earlier page. We then directly update catalog
     * database deleting specific catalog object. We then call/redirect back to the inventory page (which will
     * load all the items in the inventory excluding the just now deleted specific catalog item. 
     *
     * @param id the id
     * @param authentication the authentication
     * @return the string
     */
    @GetMapping("/inventory_admin/{id}")
    public String deleteCatalogItem(@PathVariable Long id,
        Authentication authentication) {

        String loggedInUserName = authentication.getName();
        boolean isAdminUser = isAdminRole(authentication);
        logger.info("Inside method deleteCatalogItem(...) for user {}, isAdmin {}",
            loggedInUserName, isAdminUser);

        if (!isAdminUser) {
            return "error";
        }

        inventoryService.deleteCatalogItem(id);
        return "redirect:/inventory";
    }

    /**
     * Adds the to cart: We allow this method to e called by any logged in user. This controller method
     * gets called when the user tried to add an item to cart. The specific catalog product item id is 
     * passed into this method. We then query http session object (http session is like in-memory storage 
     * space for every logged in user which will exists till the user logs outs). If we find any existing
     * catalog item in it, we update it with this newly added catalog item, or if not, we create a new list of
     * catalog item, and save it the session and model as well. We then call/redirect back to the inventory
     * user page which will show the count of items in user cart and also show all items in the catalog.
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

        List < Long > cartItems = (List < Long > ) session.getAttribute(CART_SESSION);
        if (cartItems == null) { // if user just logged-in or new browser
            cartItems = new ArrayList < Long > ();
        }
        cartItems.add(id); // add supplied product id to cart
        session.setAttribute(CART_SESSION, cartItems);
        model.addAttribute(CART_SESSION, cartItems);

        model.addAttribute("catalogItems", inventoryService.getAllCatalogItems());
        return "inventory_user";
    }

    /**
     * Adds the to cart: We allow this method to e called by any logged in user. This controller method
     * gets called when the user is ready to checkout. At this point all the items he/se has added to cart
     * is now available in the session. We fetch all the catalog product id added to session by the user, 
     * retrieve each and every catalog item belonging to those product ids and get price of each of the item, 
     * while creating te total cost of all items in the cart (rounded). We also create a list of all catalog
     * items in the cart and set it model to display items in cart as well. We then call "place_order" page 
     * which will show the items of the users cart and total total along with place order link.
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

        List < Long > cartItemIds = (List < Long > ) session.getAttribute(CART_SESSION);

        if (cartItemIds == null) { // if user just logged-in or new browser
            return "error";
        }

        session.setAttribute(CART_SESSION, cartItemIds);
        model.addAttribute(CART_SESSION, cartItemIds);

        List < CatalogItem > checkoutList = new ArrayList < CatalogItem > ();
        double orderTotal = 0;
        // only get those catalog items which are added to cart
        for (Long cartItemId: cartItemIds) {
            CatalogItem cartItem = inventoryService.getCatalogItemById(cartItemId);
            checkoutList.add(cartItem);
            orderTotal = orderTotal + cartItem.getPrice();
        }

        model.addAttribute("catalogItems", checkoutList);
        model.addAttribute("totalPrice", roundValue(orderTotal));

        return "place_order";
    }


    /**
     * Place order: We allow this method to e called by any logged in user. This important controller method
     * gets called when the user clicks the place order button. At this point we know all the items the user
     * has added to the cart (from the session). We fetch all the catalog product id added to session by the user, 
     * retrieve each and every catalog item belonging to those product. We then create List of OrderItem objects
     * setting all details of order items from the catalog item object, and also create an order object where each
     * order object will contain multiple order items within it. We just called our service to save the order items
     * and both order and order items gets saved to the database. Once saved, we get the saved order and we set
     * the details of order (just placed) back to model so the page can display order number and also the email
     * order is being sent to (actually, we do not send any emails yet nor have any code for it)
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

        List < Long > cartItemIds = (List < Long > ) session.getAttribute(CART_SESSION);

        if (cartItemIds == null) { // if user just logged-in or new browser
            return "error";
        }

        double orderTotal = 0;

        // create list of order items & an order
        List < OrderItem > orderItems = new ArrayList < OrderItem > ();
        Order order = new Order();

        // only get those catalog items which are in session after checkout
        for (Long cartItemId: cartItemIds) {
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

        List < Order > allOrders = orderService.getAllOrders();
        model.addAttribute("allOrders", allOrders);

        // clear the cart stored in session
        session.setAttribute(CART_SESSION, null);
        return "order_history";
    }


    /**
     * Checks if is admin role: utility method to retrieve if the logged in user is an admin role 
     * or not 
     * 
     * ref: https://www.baeldung.com/spring-security-check-user-role 
     *
     * @param authentication the authentication
     * @return true, if is admin role
     */
    private boolean isAdminRole(Authentication authentication) {
        return ((SimsUserDetails) authentication.getPrincipal()).getAuthorities().stream().anyMatch(
            a -> a.getAuthority().equalsIgnoreCase("admin"));
    }

    /**
     * Round value: utility method to round the $ amount to two decimal places
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
