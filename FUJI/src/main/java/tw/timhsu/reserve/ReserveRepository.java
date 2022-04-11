package tw.timhsu.reserve;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface ReserveRepository extends JpaRepository<Reserve, Integer> {

	
	@Query(value="select r from Reserve r where "
			+"r.birthmonth Like ?1 OR r.birthdate Like ?1 ")			
	public Page<Reserve> findByKeyword(String Keyword,Pageable pageable);
}
