package tw.timhsu.cartitem.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import tw.timhsu.cartitem.CartItemService;
import tw.timhsu.products.CategoryService;
import tw.timhsu.products.ProductsService;
import tw.timhsu.users.UsersService;


@Controller
public class HomeController {


	@Autowired
	private CategoryService catgeoryService;

	@Autowired
	private ProductsService productService;

	@Autowired
	private CartItemService cartItemService;
//	@GetMapping({ "index", "/" })
//	public String index(Model model,Principal principal) {
//
////		int size = cartItems.size();
////		System.out.println(size);
////		model.addAttribute("size",size);
//		model.addAttribute("catgeoryList", catgeoryService.listCategory());
//		model.addAttribute("productList", productService.listProduct());
//		return "profile/shopping";
//	}

//	@GetMapping("login")
//	public String login() {
//		return "login";
//	}
//
//	@GetMapping("signup")
//	public String signup() {
//		return "signup";
//	}

//	@PostMapping("signup")
//	public ModelAndView singnUp(Users users) {
//		ModelAndView mv = new ModelAndView("/index");
//		usersService.save(users);
//		mv.addObject("productList", productService.listProduct());
//		mv.addObject("categoryList", catgeoryService.listCategory());
//		return mv;
//	}

//	@GetMapping("allProduct")
//	public String allProduct(Model model) {
//		model.addAttribute("catgeoryList", catgeoryService.listCategory());
//		model.addAttribute("productList", productService.listProduct());
//		return "index";
//	}

	@GetMapping("getProducts/{categoryId}")
	public ModelAndView getProductFromCategory(@PathVariable("categoryId") String categoryId) {
		ModelAndView mv = new ModelAndView("index");
		int catgeoryId = Integer.parseInt(categoryId);
		System.out.println(catgeoryId);
		mv.addObject("productList", productService.findByCategory(catgeoryId));
		mv.addObject("categoryList", catgeoryService.findAll());
		return mv;
	}



}