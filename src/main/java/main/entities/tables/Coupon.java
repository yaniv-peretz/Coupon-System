package main.entities.tables;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import main.entities.tables.enums.CouponType;

@Entity
@Table(name = "COUPONS")
public class Coupon implements Comparable<Coupon> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(unique = true, nullable = false)
	private String title;
	@Column(nullable = false)
	private int amount;
	@Column(nullable = false)
	private long startDate;
	@Column(nullable = false)
	private long endDate;
	@Column(nullable = false)
	private CouponType type;
	private String message;
	@Column(nullable = false)
	private double price;
	private String image;
	
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;
	
	@ManyToMany(mappedBy = "coupons")
	private List<Customer> customers;
	
	public Coupon() {
		super();
	}
	
	public Coupon(String title, int amount, long startDate, long endDate, CouponType type, String message, double price,
			String image, Company company) {
		super();
		this.title = title;
		this.amount = amount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
		this.company = company;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public long getStartDate() {
		return startDate;
	}
	
	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}
	
	public long getEndDate() {
		return endDate;
	}
	
	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}
	
	public CouponType getType() {
		return type;
	}
	
	public void setType(CouponType type) {
		this.type = type;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", amount=" + amount + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "companyId=" + company.getId() + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coupon other = (Coupon) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public int compareTo(Coupon coupon) {
		return this.getId() - coupon.getId();
	}
	
}
