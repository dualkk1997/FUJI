package tw.timhsu.orders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import tw.timhsu.products.Products;
import tw.timhsu.products.ProductsService;

@Controller
public class OrdersDetailsController {
	
	@Autowired
	private OrdersDetailsService odService;
	
	@Autowired
	private OrdersService oService;
	
	@Autowired
	private ProductsService pService;
	
	
	@GetMapping("/ordersdetails/form/{id}")
	public String getOrdersDetailsformOrders(@PathVariable(name="id") int id ,Model m ) {
		m.addAttribute("ordersdetails", odService.findByOrders_oid(id));
		return "homepageOrderDetail";
	}
	

	
	
}
