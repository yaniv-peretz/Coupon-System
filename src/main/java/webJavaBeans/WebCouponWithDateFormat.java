package webJavaBeans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import javaBeans.Coupon;
import javaBeans.CouponType;

@XmlRootElement
public class WebCouponWithDateFormat implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private int amount;
	private String title;
	private String startDate;
	private String endDate;
	private CouponType type;
	private String message;
	private double price;
	private String image;

	public WebCouponWithDateFormat() {
		super();
		System.out.println("creating webCoupon");
	}

	public WebCouponWithDateFormat(long id, String title, String startDate, String endDate, int amount, CouponType type, String message, double price, String image) {
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

	public WebCouponWithDateFormat(Coupon c) {
		super();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");

		this.id = c.getId();
		this.title = c.getTitle();
		this.startDate = sdf.format(c.getStartDate());
		this.endDate = sdf.format(c.getEndDate());
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
		this.startDate = sdf.format(startDate);
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
		this.endDate = sdf.format(endDate);
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

	public static Coupon returnCoupon(WebCouponWithDateFormat webcoupon) {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
		Date startDate = new Date();
		Date endDate = new Date();
		try {
			startDate = sdf.parse(webcoupon.getStartDate());
			endDate = sdf.parse(webcoupon.getEndDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Coupon coupon = new Coupon(webcoupon.getId(),
				webcoupon.getTitle(),
				startDate,
				endDate,
				webcoupon.getAmount(),
				webcoupon.getType(),
				webcoupon.getMessage(),
				webcoupon.getPrice(),
				webcoupon.getImage());
		return coupon;
	}
}
