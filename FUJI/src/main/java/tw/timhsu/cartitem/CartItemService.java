package tw.timhsu.cartitem;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class CartItemService {
	@Autowired
	public CartItemRepository cartRepo;

	public List<CartItem> listCartItems(Users users) {
		return cartRepo.findByUsers(users);
	}

	public Integer addProduct(Integer productId, Integer quantity, Users users) {
		Integer updateQuantity = quantity;
		Products products = new Products(productId);

		CartItem cartItem = cartRepo.findByUsersAndProducts(users, products);

		if (cartItem != null) {
			updateQuantity = cartItem.getQuantity() + quantity;
		} else {
			cartItem = new CartItem();
			cartItem.setUsers(users);
			cartItem.setProducts(products);
		}
		cartItem.setQuantity(updateQuantity);
		cartRepo.save(cartItem);

		return updateQuantity;
	}
	
	public void removeProduct(Users users,Integer productId) {
		cartRepo.deleteByUsersAndProducts(users.getUid(), productId);
	}
	public void deleteByUsers(Users users) {
		cartRepo.deleteByUsers(users.getUid());
	}
}
