package tw.timhsu.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class messageService {
	@Autowired
	private messageRepository messageRepository;
	
	public messageModel saveMessage(messageModel message) {
		return messageRepository.save(message);
	}
}
