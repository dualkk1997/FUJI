package tw.timhsu.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsersService {
	
	@Autowired
	private UsersRepository uRep;
	
	public List<Users> findAll(){
		return uRep.findAll();
	}
	
	public Users findById(int id){
		return uRep.findById(id).get();
	}
	
	public Users update(Users users) {
		return uRep.save(users);
	}
	
	public void deleteUsers(int id) {
		uRep.deleteById(id);
	}
	
	public Page<Users> listAll(int pageNum,String keyword,String role1,String role2) {
		int pageSize = 10;
		Sort sort = Sort.by("uid").descending();
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize,sort);
	
				
		if(keyword!=null) {
			return uRep.findByKeyword(keyword, pageable,role1,role2);
		}
		
		return uRep.findByUsers(role1,role2,pageable);
	}
}
