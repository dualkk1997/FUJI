package tw.timhsu.reserve;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="reserve")
public class ReserveDetail {
	
	@Id
	@Column(name="RESERVEID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reserveid;
	@Column(name="RESERVENAME")
	private String reservename;
	@Column(name="PHONE")
	private String phone;
	@Column(name="BIRTHMONTH")
	private int birthmonth;
	@Column(name="BIRTHDATE")
	private int birthdate;
	private String period;
	private int total;
	
	
	public int getReserveid() {
		return reserveid;
	}
	public void setReserveid(int reserveid) {
		this.reserveid = reserveid;
	}
	public String getReservename() {
		return reservename;
	}
	public void setReservename(String reservename) {
		this.reservename = reservename;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getBirthmonth() {
		return birthmonth;
	}
	public void setBirthmonth(int birthmonth) {
		this.birthmonth = birthmonth;
	}
	public int getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(int birthdate) {
		this.birthdate = birthdate;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
	
}
