package tw.timhsu.orders;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import tw.timhsu.users.Users;



public interface OrdersRepository extends JpaRepository<Orders, Integer> {
	

	
	@Query(value="select o from Orders o where o.oid Like ?1 and o.status Like ?2 or o.oid Like ?1 and o.status Like ?3 ")			
	public Page<Orders> findByOid(Integer id,Pageable pageable,String status,String status2);
	
	
	@Query(value="select o from Orders o where o.status Like ?1 or o.status Like ?2 ")
	public Page<Orders> findByStatus(String status,String status2 , Pageable pageable);
	
	@Modifying
	@Query(value="Update Orders o Set o.status = ?1 Where o.oid =?2")
	public void UpdateByStatus(String status,int oid);
	
	@Query(value="select sum(totalprice) from Orders where orderdate between ('2022-03-01') and ('2022-03-31')")
	public String getTotalPrice();
	
	@Query(value="select sum(totalprice) from Orders where year(OrderDate)=2022")
	public String getAllTotalPrice();
	
	@Query(value="select Top 7 orderdate,sum(totalprice) as totalprice from Orders group by orderdate order by orderdate DESC",nativeQuery = true)
	public List<Object> getSum();
	
	@Query(value="select COUNT(status) from Orders where status=?1")
	public String getCountStatus(String status);


	@Modifying
	@Query("FROM Orders c WHERE c.users.uid=?1 and Status='結帳中'")
	public Orders findByUsers(Users users);
	

	
	
	
	
}
