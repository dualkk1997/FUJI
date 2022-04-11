package tw.timhsu.cartitem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import tw.timhsu.products.Products;
import tw.timhsu.users.Users;

@Entity
@Table(name = "cartitem")
public class CartItem {
	@Id
	@Column(name = "CIID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ciid;
	@ManyToOne
	@JoinColumn(name = "UID")
	private Users users;
	@ManyToOne
	@JoinColumn(name = "PID")
	private Products products;
	@Column(name = "quantity")
	private int quantity;
	
	
	public int getCiid() {
		return ciid;
	}

	public void setCi_id(int ciid) {
		this.ciid = ciid;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Transient
	public int getSubTotal() {
		return products.getProductprice() * quantity;
	}

}
