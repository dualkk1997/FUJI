package tw.timhsu.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	
	public Page<messageModel> listAll(int pageNum,String keyword) {
		int pageSize = 9;
		Sort sort = Sort.by("mcid").descending();
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize,sort);
	
				
		if(keyword!=null) {
			return messageRepository.findByKeyword(keyword, pageable);
		}
		
		return messageRepository.findAll(pageable);
	}
	
}
