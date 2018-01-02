package main.entities.tables;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import main.entities.tables.enums.CouponType;

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
	
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Coupon> coupons;
	
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
	
	public List<Coupon> getCoupons() {
		return coupons;
	}
	
	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	@Override
	public String toString() {
		return "Company [id=" + id + ", compName=" + compName + ", password=" + password + ", email=" + email + "]";
	}
	
	public List<Coupon> getCouponsByPrice(double price) {
		for (int i = 0; i < coupons.size(); i++) {
			if (price < coupons.get(i).getPrice()) {
				coupons.remove(i);
			}
		}
		return coupons;
	}
	
	public List<Coupon> getCouponsByDate(Long date) {
		for (int i = 0; i < coupons.size(); i++) {
			if (date < coupons.get(i).getEndDate()) {
				coupons.remove(i);
			}
		}
		return coupons;
	}
	
	public List<Coupon> getCouponsByType(CouponType type) {
		for (int i = 0; i < coupons.size(); i++) {
			if (type != coupons.get(i).getType()) {
				coupons.remove(i);
			}
		}
		return coupons;
	}
}
