package tw.timhsu.orders;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.timhsu.cartitem.CartItem;
import tw.timhsu.products.Products;
import tw.timhsu.users.Users;



@Service
@Transactional
public class OrdersService {
	
	@Autowired
	private OrdersRepository oRep;
	
	
	public List<Orders> findAll(){
		return oRep.findAll();
	}
	
	public Orders updateStatus(Orders o) {
		return oRep.save(o);
	}
	
	public Orders finById(int id) {
		return oRep.findById(id).get();
	}
	
	
	public Page<Orders> listAll(int pageNum,Integer id,String status,String status2) {
		int pageSize = 5;
		Sort sort = Sort.by("orderdate").descending();
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize,sort);
				
		if(id!=null) {
			return oRep.findByOid(id, pageable,status,status2);
		}
		
		return oRep.findByStatus(status,status2, pageable);
		}
		
		public void UpdateByStatus(String status,int oid) {
			 oRep.UpdateByStatus(status, oid);
		}
		
		public String getTotalPrice() {
			return oRep.getTotalPrice();
		}
		
		public List<Object> getSum(){
			return oRep.getSum();
		}
		
		public String getCountStatus(String status) {
			return oRep.getCountStatus(status);
		}
		
		public String getAllTotalPrice() {
			return oRep.getAllTotalPrice();
		}
		
		public Orders createOrders(Users users ,List<CartItem>cartItems,Integer totalprice) {
			Orders newOrders = new Orders();
			newOrders.setOrderdate(String.format("%tY/%<tm/%<td %<tH:%<tM:%<tS", new Date()));
			newOrders.setUsers(users);
			newOrders.setTotalprice(totalprice);
			newOrders.setStatus("已付款");
			
			Set<OrdersDetails>ordersDetails = newOrders.getOrdersdetails();
			
			for(CartItem cartItem : cartItems) {
				Products products = cartItem.getProducts();
				
				OrdersDetails ordersDetails1 = new OrdersDetails();
				ordersDetails1.setOrders(newOrders);
				ordersDetails1.setProducts(products);
				ordersDetails1.setQuantity(cartItem.getQuantity());
				ordersDetails1.setUnitprice(cartItem.getSubTotal());
				
				ordersDetails.add(ordersDetails1);
			}
			return oRep.save(newOrders);
		}
		public Orders findOrders(Users users) {
			return oRep.findByUsers(users);
		}
		public Orders saveOrders(Orders orders) {
			return oRep.save(orders);
		}
		
}
