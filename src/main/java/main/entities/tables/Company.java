package main.entities.tables;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "COMPANIES")
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(unique = true, nullable = false)
	private String compName;
	@Column(nullable = false)
	private String password;
	private String email;
	
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Coupon> coupons;
	
	public Company() {
		super();
	}
	
	public Company(String compName, String password) {
		super();
		this.compName = compName;
		this.password = password;
	}
	
	public Company(String compName, String password, String email) {
		this(compName, password);
		this.email = email;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCompName() {
		return compName;
	}
	
	public void setCompName(String compName) {
		this.compName = compName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	// public Set<Coupon> getCoupons() {
	// return companyRepo.findCompanyCoupons(this);
	// }
	
	public void setCoupons(Set<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	@Override
	public String toString() {
		return "Company [id=" + id + ", compName=" + compName + ", password=" + password + ", email=" + email + "]";
	}
	
}
