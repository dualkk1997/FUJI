package tw.timhsu.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;




public interface UsersRepository extends JpaRepository<Users, Integer> {
	
	@Query(value="select u from Users u where u.username Like %?1% and u.role Like ?2 or u.name Like %?1% and u.role Like ?2 "
			+ "or u.username Like %?1% and u.role Like ?3 or u.name Like %?1% and u.role Like ?3")			
	public Page<Users> findByKeyword(String Keyword,Pageable pageable,String role1,String role2);
	
	@Query(value="select u from Users u where u.role Like ?1 or u.role Like ?2 ")
	public Page<Users> findByUsers(String role1,String role2, Pageable pageable);

	Users findByusername(String username);
}
