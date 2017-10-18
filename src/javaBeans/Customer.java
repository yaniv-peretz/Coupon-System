package javaBeans;

import java.util.HashSet;

public class Customer {
	

	private long id;
	private String custName;
	private String password;
	private HashSet<Coupon> coupons;
	
	/**
	 * 
	 * @param id - Customer ID
	 * @param custName - Customer Name
	 * @param password - Customer login password
	 * 
	 */
	public Customer(long id, String custName, String password) {
		this(id, custName, password, new HashSet<Coupon>());
	}
	
	/**
	 * 
	 * @param id - Customer ID
	 * @param custName - Customer Name
	 * @param password - Customer login password
	 * @param coupons - optional HashSet, if omitted, a new empty HashSet will be assigned.
	 */
	public Customer(long id, String custName, String password, HashSet<Coupon> coupons) {
		super();
		this.setId(id);
		this.custName = custName;
		this.password = password;
		this.coupons = coupons;
	}

	/**
	 * Get customer ID
	 * @return Customer ID
	 */
	public long getId() {
		return id;
	}

	/**
	 * 
	 * @param id must be greater than 0, else set Id as 1
	 * @throws RuntimeException 
	 */
	public void setId(long id) throws RuntimeException {
		if(0 < id) {
			this.id = id;
		}else {
			this.id = 1L;
			throw new RuntimeException("Illegal id: " + id + " (Must be more than 0)");
		}	
	}

	/**
	 * Get Customer Name
	 * @return Customer Name
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * set Customer Name
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * Get Customer password
	 * @return Customer password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set Customer password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get a new copy of customer coupons
	 * @return
	 */
	public HashSet<Coupon> getCoupons() {
		HashSet<Coupon> couponsCopies = new HashSet<>();
		for (Coupon coupon : this.coupons) {
			couponsCopies.add(new Coupon(coupon));
		}
		return couponsCopies;
	}

	/**
	 * Set Coupons
	 * @param coupons
	 */
	public void setCoupons(HashSet<Coupon> coupons) {
		this.coupons = coupons;
	}

	/**
	 * Print customer details and coupons
	 */
	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", password=" + password + ","
				+ "\n coupons=" + coupons.toString() 
				+ "\n ]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Customer other = (Customer) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
