package tw.timhsu.orders;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import tw.timhsu.users.Users;

@Entity @Table(name = "orders")
@Component
public class Orders {
	
	@Id @Column(name = "OID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int oid;
	
	@Column(name = "TOTALPRICE")
	private int totalprice;
	
	@Column(name = "STATUS")
	private String  status;
	
	@Column(name = "ORDERDATE")
	@DateTimeFormat (pattern="yyyy-MM-dd hh:mm")
	private Date orderdate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="uid")
	private Users users;
	
	@OneToMany(mappedBy = "orders",cascade = CascadeType.ALL)
    private Set<OrdersDetails> ordersdetails = new HashSet<>();
	

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public int getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
	
	
	
}
