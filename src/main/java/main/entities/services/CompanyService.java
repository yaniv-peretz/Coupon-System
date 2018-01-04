package main.entities.services;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.entities.repoInterfaces.CompanyRepo;
import main.entities.repoInterfaces.CouponRepo;
import main.entities.tables.Company;
import main.entities.tables.Coupon;
import main.entities.tables.enums.CouponType;

@Service
public class CompanyService {
	
	@Autowired
	CompanyRepo companyRepo;
	
	@Autowired
	CouponRepo couponRepo;
	
	public Company findById(int id) {
		return companyRepo.findOne(id);
	}
	
	public Company findByNameAndPassword(String compName, String password) {
		return companyRepo.findCompanyByCompNameAndPassword(compName, password);
	}
	
	public boolean deleteCoupon(Company company, int id) {
		if (companyRepo.findCompanyCouponById(id, company) != null) {
			couponRepo.removeCoupon(id);
			return true;
		} else {
			return false;
		}
	}
	
	public int addNewCoupon(Company company, Coupon coupon) {
		// check that the name is unique
		if (couponRepo.findCouponByTitle(coupon.getTitle()) == null) {
			coupon.setCompany(company);
			coupon.setId(0);
			couponRepo.save(coupon);
			
			return coupon.getId();
		}
		return -1;
	}
	
	public void updateExistingCoupon(Company company, Coupon coupon) {
		coupon.setCompany(company);
		couponRepo.save(coupon);
	}
	
	public Set<Coupon> getCoupons(Company company) {
		return companyRepo.findCompanyCoupons(company);
	}
	
	public Coupon getCouponById(Company company, int id) {
		return companyRepo.findCompanyCouponById(id, company);
	}
	
	public Set<Coupon> getCouponsByPrice(Company company, double price) {
		return companyRepo.findCompanyCouponsByPrice(price, company);
	}
	
	public Set<Coupon> getCouponsByDate(Company company, Long endDate) {
		return companyRepo.findCompanyCouponsByDate(endDate, company);
	}
	
	public Set<Coupon> getCouponsByType(Company company, CouponType type) {
		return companyRepo.findCompanyCouponsByType(type, company);
	}
	
}
