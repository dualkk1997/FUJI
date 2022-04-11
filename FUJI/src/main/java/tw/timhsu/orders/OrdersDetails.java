package tw.timhsu.orders;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

import tw.timhsu.products.Products;

@Entity @Table(name = "ordersdetails")
@Component
public class OrdersDetails {
	
	@Id @Column(name = "ODID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int odid;
	
	@Column(name = "QUANTITY")
	private int quantity;
	
	@Column(name = "UNITPRICE")
	private int unitprice;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pid")
	private Products products;
	
	@Transient
	private int oid;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="oid")
	private Orders orders;

	public int getOdid() {
		return odid;
	}

	public void setOdid(int odid) {
		this.odid = odid;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(int unitprice) {
		this.unitprice = unitprice;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}
	
	
	
}
