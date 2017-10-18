package javaBeans;

import java.util.HashSet;

public class Company {
	
	private long id;
	private String compName;
	private String password;
	private String email;
	private HashSet<Coupon> coupons;
	
	/**
	 * Create a new company without coupon list.
	 * 
	 * @param id - Company ID
	 * @param compName - Company Name
	 * @param password - Company login password
	 * @param email - Company email
	 * @param coupons - optional HashSet, if omitted, a new empty HashSet will be assigned.
	 */
	public Company(long id, String compName, String password, String email) {
		this(id, compName, password, email, new HashSet<Coupon>());
	}
	
	/**
	 * Create a new company with coupon list.
	 * 
	 * @param id - Company ID
	 * @param compName - Company Name
	 * @param password - Company login password
	 * @param email - Company email
	 * @param coupons - optional HashSet, if omitted, a new empty HashSet will be assigned.
	 */
	public Company(long id, String compName, String password, String email, HashSet<Coupon> coupons) {
		super();
		this.setId(id);
		this.compName = compName;
		this.password = password;
		this.email = email;
		this.coupons = coupons;
	}
	
	
	/**
	 * Copy a company into a new company instance.
	 * @param company
	 */
	public Company(Company company) {
		
		HashSet<Coupon> couponsCopies = new HashSet<>();
		HashSet<Coupon> coupons = company.getCoupons();
		if (!coupons.isEmpty()) {
			
			for(Coupon coupon : coupons) {
				couponsCopies.add(new Coupon(coupon));
			}
		}
		
		this.setId(company.getId());
		this.setCompName(company.getCompName());
		this.setPassword(company.getPassword());
		this.setEmail(company.getEmail());
		this.setCoupons(couponsCopies);
		
	}
	
	
	/**
	 * get company id
	 * @return
	 */
	public long getId() {
		return id;
	}
	
	
	/**
	 * set company id (id must be grater that 0)
	 */
	public void setId(long id) {
		if(0 < id) {
			this.id = id;
		}else {
			this.id = 1L;
			throw new RuntimeException("Illegal id: " + id + " (Must be more than 0)");
		}
	}
	
	
	/**
	 * get company name
	 */
	public String getCompName() {
		return compName;
	}
	
	
	/**
	 * set company name
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}
	
	
	/**
	 * get company password
	 */
	public String getPassword() {
		return password;
	}
	
	
	/**
	 * set company password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	/**
	 * get company email
	 */
	public String getEmail() {
		return email;
	}
	
	
	/**
	 * set company email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	/**
	 * 
	 * @return <b>new copy</b> HashSet of the Company Coupons
	 */
	public HashSet<Coupon> getCoupons() {
		
		HashSet<Coupon> clone = new HashSet<>();
		for (Coupon coupon : this.coupons) {
			clone.add(new Coupon(coupon));
		}
		return clone;
	}
	
	
	/**
	 * set company email
	 */
	public void setCoupons(HashSet<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	
	/**
	 * print Company details to String.
	 */
	@Override
	public String toString() {
		return "Company [id=" + id + ", compName=" + compName + ", password=" + password + ", email=" + email
				+ ", coupons=" + coupons.toString() + "]";
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	
	/**
	 * Compare only by Company Id.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
