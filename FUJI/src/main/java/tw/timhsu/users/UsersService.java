package tw.timhsu.users;

import java.util.List;
import java.util.Optional;

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
	
//	 private final UsersRepository userRepository;
//	  // private final ModelMapper modelMapper;

	    @Autowired
	    public UsersService(UsersRepository userRepository) {
	        this.uRep = userRepository;
	     //   this.modelMapper = modelMapper;
	    }
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
	public Users findByusername(String name) {
		return uRep.findByusername(name);
	}
	 public Optional<Users> findByUsername(String username){return uRep.findByUsername(username);}
	    public Users findUserByUsername(String username){return uRep.findUserByUsername(username);}
	    public Users findByEmail(String email){return uRep.findByEmail(email);}
	    
	    public boolean userExists(String username){
	        return findByUsername(username).isPresent();
	    }

	    public Users createUser(Users user){
	        user.setRole("USER");
	        return uRep.save(user);
	    }


		public void createNewCustomerAfterOAuthLoginSuccess(String email ,String name) {
			Users user = new Users();
			user.setEmail(email);
			user.setName(name);
			user.setPassword("GOOGLE");
			user.setUsername("GOOGLE");
			user.setPhone("0987654321");
			user.setRole("USER");
			uRep.save(user);
		}




		public void updateCustomerAfterOAuthLoginSuccess(Users user, String name) {
			user.setName(name);
			
			uRep.save(user);
		}
}
