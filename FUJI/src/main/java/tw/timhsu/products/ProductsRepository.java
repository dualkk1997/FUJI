package tw.timhsu.products;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;




public interface ProductsRepository extends JpaRepository<Products, Integer> {
	
	public List<Products> findByCategory_categoryid(int categoryid);
	
	@Query(value="select p from Products p where "
			+"p.productname Like %?1% OR p.pid Like %?1% OR p.productprice Like %?1%")			
	public Page<Products> findByKeyword(String Keyword,Pageable pageable);
	
	
}
