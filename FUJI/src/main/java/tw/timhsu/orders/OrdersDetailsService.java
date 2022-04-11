package tw.timhsu.orders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrdersDetailsService {
	
	@Autowired
	private OrdersDetailsRepository odRep;

	public OrdersDetails findOrdersId(int oid){
		return odRep.findById(oid).get();
	}
	
	public List<OrdersDetails> findAll(){
		return odRep.findAll();
	}
	
	public List<OrdersDetails> findByOrders_oid(int oid){
		return odRep.findByOrders_oid(oid);
	}
	
	public List<Object> getHight(){
		return odRep.getHight();
	}
}
