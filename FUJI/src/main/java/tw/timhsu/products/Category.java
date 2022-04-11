package tw.timhsu.products;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category {

	@Id
	@Column(name = "CATEGORYID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int categoryid;

	@Column(name = "CATEGORYNAME")
	private String categoryname;

	// @OneToMany(fetch=FetchType.LAZY, mappedBy="category"
	// ,cascade=CascadeType.ALL)
	// private List<Product> products;
//	private Set<Product> products = new HashSet<Product>(0);
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private List<Products> productList;

	public List<Products> getProductList() {
		return productList;
	}

	public Category() {

	}

	public int getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public void setProductList(List<Products> productList) {
		this.productList = productList;
	}

//	public Set<Product> getProducts() {
//		return products;
//	}
//
//	public void setProducts(Set<Product> products) {
//		this.products = products;
//	}

}
