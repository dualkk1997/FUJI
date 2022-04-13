package tw.timhsu.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("backend")
public class messageController {
	@Autowired
	private messageService messageService;
	
	
	@PostMapping("sendmessage")
	public String sendmessage(messageModel message) {
		messageService.saveMessage(message);
		return "contactusSuccess";
	}
	
	@RequestMapping("/messagelist/page/{pageNum}")
	public String viewPage(Model model,
			@PathVariable(name = "pageNum") int pageNum,
			@Param("keyword") String keyword) {
		
		
		Page<messageModel> page = messageService.listAll(pageNum, keyword);
		
		List<messageModel> message = page.getContent();
		
		model.addAttribute("currentPage", pageNum);    
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		
		model.addAttribute("keyword", keyword);
		
		
		model.addAttribute("message", message);
		
		
		return "homepageMessage";
	}
	
	@RequestMapping("/messagelist/page/1")
	public String viewHomePage(Model model ,String keyword) {
		return viewPage(model, 1, keyword);
	}
	
}
