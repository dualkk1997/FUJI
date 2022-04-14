package tw.timhsu.cartitem.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tw.timhsu.cartitem.CartItem;
import tw.timhsu.cartitem.CartItemService;
import tw.timhsu.orders.Orders;
import tw.timhsu.orders.OrdersService;
import tw.timhsu.products.CategoryService;
import tw.timhsu.products.Products;
import tw.timhsu.products.ProductsService;
import tw.timhsu.users.Users;
import tw.timhsu.users.UsersService;

@Controller
@RequestMapping("profile")
public class ProductController {
	@Autowired
	private ProductsService productService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private CategoryService catgeoryService;
	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private OrdersService ordersService;

	@GetMapping("shopping")
	public String shoppingpage(Principal principal, Model model) {
		Users users = usersService.findByusername(principal.getName());
		List<CartItem> cartItems = cartItemService.listCartItems(users);
		int size = cartItems.size();
		System.out.println(size);
		model.addAttribute("size", size);
		model.addAttribute("cartItems", cartItems);
		int total = findSum(users);
		model.addAttribute("total", total);
		model.addAttribute("catgeoryList", catgeoryService.findAll());
		String str = "";

		// 產品拼成符合ECPAY的規格
		for (int i = 0; i < cartItems.size(); i++) {
			Products p = cartItems.get(i).getProducts();
			String str1 = p.getProductname();
			int price = p.getProductprice();
			int quantity = cartItems.get(i).getQuantity();
			str += str1 + price + "元x" + quantity + "#";
		}
		model.addAttribute("str", str);
		return "profile/shopping";
	}

	@GetMapping("orders")
	public String orders(Principal principal, Model model) {
		Users users = usersService.findByusername(principal.getName());
		List<Orders> orders = ordersService.listOrders(users);
		model.addAttribute("orders", orders);
		
		return "profile/order";
	}

	@GetMapping("cartProduct")
	public String cartProduct(Model model, Principal principal) {
		Users users = usersService.findByusername(principal.getName());
		List<CartItem> cartItems = cartItemService.listCartItems(users);
		model.addAttribute("cartItems", cartItems.get(0).getProducts());
		int total = findSum(users);
		String str = "";

		// 產品拼成符合ECPAY的規格
		for (int i = 0; i < cartItems.size(); i++) {
			Products p = cartItems.get(i).getProducts();
			String str1 = p.getProductname();
			int price = p.getProductprice();
			int quantity = cartItems.get(i).getQuantity();
			str += str1 + price + "元x" + quantity + "#";

		}
		model.addAttribute("str", str);
		model.addAttribute("total", total);
		model.addAttribute("users", users);
		return "profile/cartProduct";
	}

	// 計算總金額
	private int findSum(Users users) {
		List<CartItem> list = users.getCartItem();
		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			int cartItem = list.get(i).getSubTotal();
			sum += cartItem;
		}
		return sum;
	}

	@GetMapping("getProducts/{categoryId}")
	public ModelAndView getProductFromCategory(@PathVariable("categoryId") String categoryId, Principal principal) {
		ModelAndView mv2 = new ModelAndView();
		mv2.setViewName("profile/index");
		Integer categoryLongId = Integer.parseInt(categoryId);
		System.out.println(categoryLongId);
		mv2.addObject("productList", productService.findByCategory(categoryLongId));
		mv2.addObject("categoryList", catgeoryService.findAll());
		Users users = usersService.findByusername(principal.getName());
		List<CartItem> cartItems = cartItemService.listCartItems(users);
		int size = cartItems.size();
		mv2.addObject("size", size);
		mv2.addObject("cartItems", cartItems);
		int total = findSum(users);
		mv2.addObject("total", total);
		return mv2;
	}

	@PostMapping("addtocarts")
	public String addToCart(@RequestParam("pid") Integer pid, @RequestParam("quantity") int quantity,
			Principal principal, Model model) {
		Users users = usersService.findByusername(principal.getName());
		cartItemService.addProduct(pid, quantity, users);
		model.addAttribute("catgeoryList", catgeoryService.findAll());
		model.addAttribute("productList", productService.getAllProduct());
		List<CartItem> cartItems = cartItemService.listCartItems(users);
		int size = cartItems.size();
		System.out.println(size);
		model.addAttribute("size", size);
		model.addAttribute("cartItems", cartItems);
		int total = findSum(users);
		model.addAttribute("total", total);
		model.addAttribute("catgeoryList", catgeoryService.findAll());
		String str = "";

		// 產品拼成符合ECPAY的規格
		for (int i = 0; i < cartItems.size(); i++) {
			Products p = cartItems.get(i).getProducts();
			String str1 = p.getProductname();
			int price = p.getProductprice();
			int quantity2 = cartItems.get(i).getQuantity();
			str += str1 + price + "元x" + quantity2 + "#";
		}
		model.addAttribute("str", str);
		return "redirect:/profile/shopping";
	}

	@GetMapping("remove/{productId}")
	public String removeProducts(@PathVariable("productId") Integer productId, Principal principal) {
		Users users = usersService.findByusername(principal.getName());
		cartItemService.removeProduct(users, productId);
		return "redirect:/profile/shopping";
	}
	@GetMapping("memberList")
	public String memberListpage() {
		return "/profile/memberList";
	}

}
