package tw.timhsu.message;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "messagecontent")
public class messageModel {
	@Id
	@Column(name = "mcid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int mcid;
	@Column(name = "fullname")
	private String fullname;
	@Column(name = "email")
	private String email;
	@Column(name = "phone")
	private String phone;
	@Column(name = "message")
	private String message;

	public int getMcid() {
		return mcid;
	}

	public void setMcid(int mcid) {
		this.mcid = mcid;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
