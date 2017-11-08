package webJavaBeans;

import java.io.Serializable;
import java.util.Date;

import javaBeans.Coupon;
import javaBeans.CouponType;

public class WebCoupon implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;

	public WebCoupon() {
		super();
	}

	public WebCoupon(long id, String title, Date startDate, Date endDate, int amount, CouponType type, String message,
			double price, String image) {
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
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

	public Coupon getCoupon() {
		return new Coupon(id, title, startDate, endDate, amount, type, message, price, image);
	}

}
