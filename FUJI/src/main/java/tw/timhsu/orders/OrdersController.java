package tw.timhsu.orders;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import tw.timhsu.users.Users;
import tw.timhsu.users.UsersService;

@Controller
@RequestMapping("backend")
public class OrdersController {
	
	@Autowired
	private OrdersService oService;
	
	@Autowired
	private UsersService uService;
	
	@Autowired
	private OrdersDetailsService odService;
	
	@GetMapping("/ordersList")
	public String showOrders(Model m) {
		List<Orders> orders = oService.findAll();
		List<Users> users= uService.findAll();
		m.addAttribute("orders", orders);
		return "orderslist";
	}
	
	
	@RequestMapping("/ordersList/page/{pageNum}")
	public String viewPage(Model model,
			@PathVariable(name = "pageNum") int pageNum,
			@Param("id") Integer id) {
		
		String status ="處理中";
		String status2 ="已付款";
		Page<Orders> page = oService.listAll(pageNum, id,status,status2);
		
		List<Orders> orders = page.getContent();

		
		OrdersDetails ordersdetails = new OrdersDetails();

		
		model.addAttribute("currentPage", pageNum);    
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("id", id);
		model.addAttribute("orders", orders);
		model.addAttribute("ordersdetails", ordersdetails);
		
		return "homepageOrder";
	}
    

	//訂單首頁
    @RequestMapping("/ordersList/page/1")
	public String viewHomePage(Model model ,Integer id) {
		return viewPage(model, 1, id);
	}
    //修改按鈕
    @PostMapping("/orderList/update")
    public String Update(Orders o) {
    	oService.updateStatus(o);
    	return "redirect:/ordersList/page/1";
    }
    

    //接單
    @RequestMapping("/ordersList/catch/{id}")
    public String updateStatusByid(@PathVariable(name="id")int id , String status) {
    	status ="處理中";
    	oService.UpdateByStatus(status, id);
    	return "redirect:/ordersList/page/1";
    }
    //完成訂單
    @RequestMapping("/ordersList/complete/{id}")
    public String updateStatusOkByid(@PathVariable(name="id")int id , String status) {
    	status ="完成訂單";
    	oService.UpdateByStatus(status, id);
    	return "redirect:/ordersList/page/1";
    }
    //完成訂單顯示頁面
    @RequestMapping("/ordersList/success/page/{pageNum}")
	public String viewCompletePage(Model model,
			@PathVariable(name = "pageNum") int pageNum,
			@Param("id") Integer id) {
		
		String status ="完成訂單";
		String status2 ="取消訂單";
		Page<Orders> page = oService.listAll(pageNum, id,status,status2);
		
		List<Orders> orders = page.getContent();

		
		OrdersDetails ordersdetails = new OrdersDetails();

		
		model.addAttribute("currentPage", pageNum);    
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("id", id);
		model.addAttribute("orders", orders);
		model.addAttribute("ordersdetails", ordersdetails);
		
		return "homepageOrderStatus";
	}
    
    @RequestMapping("/ordersList/success/page/1")
   	public String viewCompleteHomePage(Model model ,Integer id) {
   		return viewCompletePage(model, 1, id);
   	}
    
    @GetMapping("/viewSumPage")
    public String viewSumPage(Model m) {
    	String status1 ="已付款";
    	String status2 ="處理中";
    	m.addAttribute("totalprice", oService.getTotalPrice());
    	m.addAttribute("alltotalprice", oService.getAllTotalPrice());
    	m.addAttribute(	"yet", oService.getCountStatus(status1));
    	m.addAttribute(	"ing", oService.getCountStatus(status2));
    	m.addAttribute("sum",oService.getSum());
    	m.addAttribute("hight",odService.getHight());
    	return "homepageIndex";
    }

	
}
