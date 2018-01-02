package webComponents;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import main.entities.tables.Coupon;
import main.entities.tables.enums.CouponType;

@XmlRootElement
public class WebCoupon implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int amount;
	private String title;
	private long startDate;
	private long endDate;
	private CouponType type;
	private String message;
	private double price;
	private String image;
	
	public WebCoupon() {
		super();
	}
	
	public WebCoupon(int id, String title, long startDate, long endDate, int amount, CouponType type,
			String message, double price, String image) {
		super();
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
	}
	
	public WebCoupon(Coupon c) {
		super();
		
		this.id = c.getId();
		this.title = c.getTitle();
		this.startDate = c.getStartDate();
		this.endDate = c.getEndDate();
		this.amount = c.getAmount();
		this.type = c.getType();
		this.message = c.getMessage();
		this.price = c.getPrice();
		this.image = c.getImage();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
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
	
	public static Coupon returnCoupon(WebCoupon webcoupon) {
		return new Coupon(webcoupon.getTitle(), webcoupon.getAmount(), webcoupon.getStartDate(), webcoupon.getEndDate(),
				webcoupon.getType(), webcoupon.getMessage(), webcoupon.getPrice(), webcoupon.getImage(), null);
	}
	
}