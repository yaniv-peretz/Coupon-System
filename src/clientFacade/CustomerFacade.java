package clientFacade;

import java.util.Date;
import java.util.HashSet;
import dao.CouponDBDAO;
import dao.CustomerDBDAO;
import javaBeans.Coupon;
import javaBeans.CouponType;
import javaBeans.Customer;

public class CustomerFacade implements CouponClientFacade {

	private static CustomerDBDAO custDAO;
	private static CouponDBDAO coupDAO;
	private Customer customer;
	
	public CustomerFacade() {
		super();
		
	}
	
	@Override
	public CustomerFacade login(String custName, String password, ClientType clientType) {
		
		CustomerFacade custFacade = null;
		
		CustomerDBDAO loginDAO = new CustomerDBDAO();
		if(clientType == ClientType.CUSTOMER && loginDAO.login(custName, password)) {
			
			custDAO = new CustomerDBDAO();
			coupDAO = new CouponDBDAO();
			customer = custDAO.getLoginCustomer(custName, password);
			
			custFacade = this;
		}
	
		return custFacade;			
		
	}
	
	/**
	 * Add Coupon to customer
	 * @param coupon
	 */
	public void purchaseCoupon(Coupon coupon) {
		
		boolean Owned = customer.getCoupons().contains(coupon);
		int amount = coupon.getAmount();
		Date endDate = coupon.getEndDate();
		Date today = new Date(System.currentTimeMillis());
		
		//purchase coupon only if not already owned, amount not zero, and end date is valid
		if(!Owned && 0 < amount && today.before(endDate) ) {
			//reduce amount after purchase
			coupon.setAmount(amount-1);
			coupDAO.updateCoupon(coupon);
			
			//add coupon to customer
			HashSet<Coupon> coupons = customer.getCoupons();
			coupons.add(coupon);
			customer.setCoupons(coupons);
			
			//add coupon to customer DB
			custDAO.addCouponToCustmer(customer.getId(), coupon.getId());
			
		}else {
			String messege;
			if(Owned) {
				messege = "coupon: " + coupon.getId() + " already owned by customer";
				
			}else if(amount == 0){
				messege = "coupon: " + coupon.getId() + " amount is 0";
				
			}else {
				messege = "coupon " + coupon.getId() + " end date: " + endDate.toString() + " is before today: " + today.toString();
			}
			
			System.err.println(messege);
			System.err.println("coupon was not purchased");
		}	
		
	}

	/**
	 * Get all coupons
	 * @return
	 */
	public HashSet<Coupon> allPurchasedCoupons(){
		HashSet<Coupon> coupons = this.customer.getCoupons();
		return coupons;
		
	}
	
	/**
	 * Get all coupons of Type
	 * @return
	 */
	public HashSet<Coupon> allPurchasedCouponsByType(CouponType type){
		HashSet<Coupon> coupons = allPurchasedCoupons();
		HashSet<Coupon> typedCoupons = new HashSet<>();
		
		for (Coupon coupon : coupons ) {
			if(coupon.getType() == type) {
				Coupon couponCopy = new Coupon(coupon);				
				typedCoupons.add(couponCopy);
			}
		}
		
		return typedCoupons;	
	}
	
	/**
	 * Get all coupons with price under param
	 * @return
	 */
	public HashSet<Coupon> allPurchasedCouponsByPrice(double price){
		HashSet<Coupon> coupons = allPurchasedCoupons();
		HashSet<Coupon> pricedCoupons = new HashSet<>();
		
		for (Coupon coupon : coupons ) {
			if(coupon.getPrice() < price) {
				Coupon couponCopy = new Coupon(coupon);				
				pricedCoupons.add(couponCopy);
			}
		}
		
		return pricedCoupons;	
	}
}
