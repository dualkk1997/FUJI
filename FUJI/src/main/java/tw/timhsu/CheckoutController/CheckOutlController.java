package tw.timhsu.CheckoutController;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import tw.timhsu.cartitem.CartItem;
import tw.timhsu.cartitem.CartItemService;
import tw.timhsu.orders.Orders;
import tw.timhsu.orders.OrdersService;
import tw.timhsu.products.CategoryService;
import tw.timhsu.products.ProductsService;
import tw.timhsu.users.Users;
import tw.timhsu.users.UsersService;

@Controller
@RequestMapping("/ecpay")

public class CheckOutlController {

	@Autowired
	private OrdersService ordersService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private ProductsService productService;
	@Autowired
	private CategoryService catgeoryService;
	@Autowired
	private JavaMailSenderImpl mailsender;
	@Autowired
	private SpringTemplateEngine templateEngine;

	@PostMapping(value = "/checkout", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String processPayment(@RequestParam("TotalAmount") String TotalAmount, Principal principal,
			@RequestParam("TradeDesc") String TradeDesc, @RequestParam("ItemName") String ItemName) {
		AioCheckOutALL obj = new AioCheckOutALL();
		AllInOne all = new AllInOne("");
		obj.setMerchantTradeNo(String.format("FUJI%d", new Date().getTime()));
		obj.setMerchantTradeDate(String.format("%tY/%<tm/%<td %<tH:%<tM:%<tS", new Date()));
		obj.setTotalAmount(TotalAmount);
		obj.setTradeDesc(TradeDesc);
		obj.setItemName(ItemName);
		obj.setReturnURL("https://220.133.103.95/ecpay/ECPayServer2");
		obj.setOrderResultURL("http://localhost:8081/ecpay/ECPayServer");
		String form = all.aioCheckOut(obj, null);
		return form;
	}

	@GetMapping("ECPayServer")
	@ResponseBody
	public String PaymentResult() {
		return "hi";
	}

	@PostMapping("ECPayServer")
	public String paymentResultString(HttpServletRequest request, Principal principal, Model model)
			throws MessagingException, IOException {
		Hashtable<String, String> dict = new Hashtable<String, String>();
		Enumeration<String> enumeration = request.getParameterNames();

		while (enumeration.hasMoreElements()) {
			String paramName = enumeration.nextElement();
			String paramValue = request.getParameter(paramName);
			dict.put(paramName, paramValue);
		}

		AllInOne all = new AllInOne("");
		boolean checkStatus = all.compareCheckMacValue(dict);

		if ("1".equals(dict.get("RtnCode")) && checkStatus == true) {
			// 付款成工處理
			Users users = usersService.findByusername(principal.getName());
			List<CartItem> cartItems = cartItemService.listCartItems(users);

			Integer sum = findSum(users);

			Orders orders = ordersService.createOrders(users, cartItems, sum);
			cartItemService.deleteByUsers(users);

			sendOrderEmail(orders, cartItems, sum);
			model.addAttribute("total", sum);
			return "redirect:paymentSuccess";
		} else {
			// 付款失敗處理
			System.out.println(dict.toString());
			return "<h1>hiiiii</h1>";
		}
	}

//計算總金額
	private int findSum(Users users) {
		List<CartItem> list = users.getCartItem();
		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			int cartItem = list.get(i).getSubTotal();
			sum += cartItem;
		}
		return sum;
	}

//send email
	public void sendOrderEmail(Orders orders, List<CartItem> cartItem, Integer total)
			throws MessagingException, IOException {

		MimeMessage message = mailsender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		Context ctx = new Context();

		ctx.setVariable("name", orders.getUsers().getUsername());
		ctx.setVariable("status", orders.getStatus());
		ctx.setVariable("cartItem", cartItem);
		ctx.setVariable("orderdate", orders.getOrderdate());
		ctx.setVariable("total", total);

		String html = templateEngine.process("email.html", ctx);
		helper.setText(html, true);
		helper.setSubject("你的訂單已成功訂購");
		helper.setFrom("cowboycowmu@gmail.com");
		helper.setTo("exil4625@hotmail.com.tw");

		mailsender.send(message);
	}
}
