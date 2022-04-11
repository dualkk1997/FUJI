package tw.timhsu.products;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import tw.timhsu.products.Category;

@Entity @Table(name="products")
public class Products {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pid;
	
	private String productname;
	
	private int productprice;
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String productimage;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="categoryid")
	private Category category;
	
	public Products() {
		
	}
	public Products(int pid,String productname,int productprice, String productimage,Category category) {
		this.pid=pid;
		this.productname=productname;
		this.productprice=productprice;
		this.productimage=productimage;
		this.category=category;
	}
	
	
	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public int getProductprice() {
		return productprice;
	}

	public void setProductprice(int productprice) {
		this.productprice = productprice;
	}

	public String getProductimage() {
		return productimage;
	}

	public void setProductimage(String productimage) {
		this.productimage = productimage;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


	

}