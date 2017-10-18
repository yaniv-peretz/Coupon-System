package javaBeans;

import java.util.Calendar;
import java.util.Date;
public class Coupon {

	private long id;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;
	
	/**
	 * 
	 * @param id - Coupon id
	 * @param title - Coupon title
	 * @param startDate - Coupon start date 
	 * @param endDate - Coupon end date
	 * @param amount - Coupon available amount
	 * @param type - Coupon type (Enum CouponType)
	 * @see CouponType
	 * @param message - Coupon message
	 * @param price - Coupon price
	 * @param image - Coupon image
	 */
	public Coupon(long id, String title, Date startDate, Date endDate, int amount, CouponType type, String message,
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

	/**
	 * Create a copy coupon from a coupon
	 * @param coupon
	 */
	public Coupon(Coupon coupon) {
		
		this(coupon.getId(),
				coupon.getTitle(),
				new Date(coupon.getStartDate().getTime()),
				new Date(coupon.getEndDate().getTime()),
				coupon.getAmount(),
				coupon.getType(),
				coupon.getMessage(),
				coupon.getPrice(),
				coupon.getImage()
				);
	}	

	public long getId() {
		return id;
	}

	/**
	 * Set Coupon id (if id in small than 0, set id as 1).
	 * @param id
	 */
	public void setId(long id) {
		if(0 < id) {
			this.id = id;
		}else {
			this.id = 1L;
		}			
	}

	/**
	 * Get Coupon Title
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * set Coupon Title
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get Coupon start date
	 * @return
	 */
	public Date getStartDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		Date clone = cal.getTime();
		return clone;
	}

	/**
	 * set Coupon start date
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * get a Coupon start end copy
	 * @param startDate
	 */
	public Date getEndDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		Date clone = cal.getTime();
		return clone;
	}

	/**
	 * set Coupon end date
	 * @param startDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 
	 * @return
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Set Coupon amount (set to 0 in case of negative amount). 
	 * @param amount
	 */
	public void setAmount(int amount) {
		if(0 <= amount) {
			this.amount = amount;
		}else {
			this.amount = 0;
			throw new RuntimeException("Illegal amount: " + amount + " (Must be more than 0)");
		}	
	}

	/**
	 * get Coupon Type 
	 * @return
	 * @see CouponType
	 */
	public CouponType getType() {
		return type;
	}
	
	/**
	 * set Coupon Type 
	 * @return
	 * @see CouponType
	 */
	public void setType(CouponType type) {
		this.type = type;
	}

	/**
	 * Get coupon message
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set coupon message
	 * @return
	 */	
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Get coupon price
	 * @return
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * set coupon price (set to 1 in case of 0 or negative number)
	 * @return
	 */
	public void setPrice(double price){
		if(0 < id) {
			this.price = price;
		}else {
			this.price = 1L;
			throw new RuntimeException("Illegal Price: " + price + " (Must be more than 0)");
		}			
	}

	/**
	 * Get Coupon image
	 * @return
	 */
	public String getImage() {
		return image;
	}

	/**
	 * Set Coupon image
	 * @param image
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * Print Company to String
	 */
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", StartDate=" + startDate + ", EndDate=" + endDate
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	/**
	 * Compare Coupons by Id.
	 */
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
		
	
}
