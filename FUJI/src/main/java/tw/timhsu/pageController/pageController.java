package tw.timhsu.pageController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("home")
public class pageController {
	@GetMapping("aboutus")
	public String aboutusPage() {
		return "/aboutUs";
	}
	@GetMapping("contactus")
	public String contentusPage() {
		return "/contactUs";
	}
}
