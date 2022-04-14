package tw.timhsu.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface messageRepository extends JpaRepository<messageModel, Integer> {
	
	@Query(value="select m from MessageContent m where "
			+"m.fullname Like %?1% OR m.phone Like %?1%",nativeQuery = true)			
	public Page<messageModel> findByKeyword(String Keyword,Pageable pageable);
	
}
