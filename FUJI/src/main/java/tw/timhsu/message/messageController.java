package tw.timhsu.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class messageController {
	@Autowired
	private messageService messageService;
	
	
	@PostMapping("sendmessage")
	public String sendmessage(messageModel message) {
		messageService.saveMessage(message);
		return "contactusSuccess";
	}
}
