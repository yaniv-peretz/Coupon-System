package clientFacade;

import java.util.Date;
import java.util.HashSet;
import dao.CompanyDBDAO;
import dao.CouponDBDAO;
import javaBeans.Company;
import javaBeans.Coupon;
import javaBeans.CouponType;

public class CompanyFacade implements CouponClientFacade {
	
	private CompanyDBDAO compDAO;
	private CouponDBDAO coupDAO;		
	private Company company;
	
	public CompanyFacade() {
		super();
		
	}
	
	@Override
	public CompanyFacade login(String compName, String password, ClientType clientType) {
		
		CompanyFacade companyFacade = null;
		
		CompanyDBDAO TempCompDAO = new CompanyDBDAO();
		if(TempCompDAO.login(compName, password)) {
			
			compDAO = TempCompDAO;
			coupDAO = new CouponDBDAO();
			company = compDAO.getLoginCompany(compName, password);
			
			companyFacade = this;
		}
				
		return companyFacade;
	}

	/**
	 * Create Coupon and add the Coupon to the loged in Company.
	 * @param coupon
	 */
	public void createCoupon(Coupon coupon) {
		
		HashSet<Coupon> coupons = company.getCoupons();
		coupons.add(coupon);
			
		//add coupon to the company offering
		this.company.setCoupons(coupons);
	
		coupDAO.createCoupon(coupon);
		compDAO.setCoupons(company);
			
	} 

	/**
	 * Remove coupon (only if coupon is owned by the logged in company),
	 * @param coupon
	 */
	public void removeCoupon(Coupon coupon) {
		
		if(company.getCoupons().contains(coupon)) {
			company.getCoupons().remove(coupon);
			coupDAO.removeCoupon(coupon);
			
		}else {
			String message = "Coupon: " + coupon.getId() +" not in company: " + company.getId() + " coupon list";
			throw new RuntimeException(message);
			
		}
	}

	/**
	 * Update Coupon end date and price (only if coupon is owned by the logged in company),
	 * @param coupon
	 */
	public void updateCoupon(Coupon coupon) {
		
		if(company.getCoupons().contains(coupon)) {	
			Coupon updateCoupon = coupDAO.getCoupon(coupon.getId());
			
			// set only End Date & Price - according to design
			updateCoupon.setEndDate(coupon.getEndDate());
			updateCoupon.setPrice(coupon.getPrice());
			coupDAO.updateCoupon(updateCoupon);
			
		}else {
			String messege = "Coupon not in company's coupon list";
			System.err.println(messege);
			System.err.println("coupon: " + coupon);
			System.err.println("company coupons: " + company.getCoupons());
		}
	}

	/**
	 * A copy of the current logged in Company
	 * @return
	 */
	public Company whichCompanyAmI() {
		return new Company(company);
	}
	
	public HashSet<Coupon> getAllCoupons() {
		return whichCompanyAmI().getCoupons();
	}
	
	public HashSet<Coupon> getCouponsByType(CouponType type) {

		HashSet<Coupon> typedCoupons = new HashSet<>();
		
		for(Coupon coupon : company.getCoupons()) {
			if(coupon.getType() == type) {
				typedCoupons.add(new Coupon(coupon));
			}
		}
		
		return typedCoupons;
	}
	
	/**
	 * Get logged in company coupons by price
	 * @param price
	 * @return
	 */
	public HashSet<Coupon> getCouponsByPrice(double price) {
		
		HashSet<Coupon> pricesCoupons = new HashSet<>();
		
		for(Coupon coupon : company.getCoupons()) {
			if(coupon.getPrice() < price) {
				pricesCoupons.add(new Coupon(coupon));
			}
		}
		
		return pricesCoupons;
	}

	/**
	 * Get logged in company coupons by Date
	 * @param price
	 * @return
	 */
	public HashSet<Coupon> getCouponsByDate(Date endDate) {
		
		HashSet<Coupon> dateValidCoupons = new HashSet<>();		
		
		for(Coupon coupon : company.getCoupons()) {
			if(coupon.getEndDate().before(endDate)) {
				dateValidCoupons.add(new Coupon(coupon));
			}
		}
		
		return dateValidCoupons;
	}
}
