package tw.timhsu.orders;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import tw.timhsu.users.Users;

public interface OrdersDetailsRepository extends JpaRepository<OrdersDetails, Integer> {

	public List<OrdersDetails> findByOrders_oid(int oid);

	@Query(value = "select top 5 o.pid,p.productname,sum(o.quantity)  from OrdersDetails as o Join Products  as p on o.pid=p.pid  group by o.pid,p.productname order by 3 desc", nativeQuery = true)
	public List<Object> getHight();


}
