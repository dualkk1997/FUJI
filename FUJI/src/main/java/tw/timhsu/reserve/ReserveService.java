package tw.timhsu.reserve;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.timhsu.message.messageModel;
import tw.timhsu.products.Products;



@Service
@Transactional
public class ReserveService {
	
	@Autowired
	private ReserveRepository rRep;
	
	public List<Reserve> findAll(){
		return rRep.findAll();
	}
	
	public void deleteReserveById(int id) {
		rRep.deleteById(id);
	}
	
	public Page<Reserve> listAll(int pageNum,String keyword) {
		int pageSize = 10;
		Sort sort = Sort.by("reserveid").descending();
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize,sort);
				
		if(keyword!=null) {
			return rRep.findByKeyword(keyword, pageable);
		}
		
		return rRep.findAll(pageable);
	}
	
		public Reserve update(Reserve reserve) {
		
			return rRep.save(reserve);
	}
		
		
	
	
}
