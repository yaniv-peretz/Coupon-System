package main.entities.tables;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import main.entities.tables.enums.CouponType;

@Entity(name = "CUSTOMERS")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(unique = true, nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String password;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
	//// @formatter:off
			name = "CUSTOMER_COUPONS",
			joinColumns = @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "id"), 
			inverseJoinColumns = @JoinColumn(name = "COUPON_ID", referencedColumnName = "id"))
// @formatter:on	
	private List<Coupon> coupons;
	
	public Customer() {
		super();
	}
	
	public Customer(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Coupon> getCoupons() {
		return coupons;
	}
	
	public void setCoupons(List<Coupon> coupon) {
		this.coupons = coupon;
	}
	
	@Override
	public String toString() {
		return "Customer [ID=" + id + ", name=" + name + ", password=" + password + "]";
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
