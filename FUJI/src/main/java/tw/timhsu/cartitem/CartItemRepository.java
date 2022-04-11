package tw.timhsu.cartitem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import tw.timhsu.products.Products;
import tw.timhsu.users.Users;


public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	public CartItem findByUsersAndProducts(Users users, Products products);

	public List<CartItem> findByUsers(Users users);
	
	
	@Modifying
	@Query("DELETE FROM CartItem c WHERE c.users.uid= ?1 AND c.products.pid = ?2")
	public void deleteByUsersAndProducts(Integer usersId,Integer productId);
	
	@Modifying
	@Query("DELETE CartItem c where c.users.uid = ?1")
	public void deleteByUsers(Integer usersId);
}
