package main.entities.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.entities.repoInterfaces.CompanyRepo;
import main.entities.tables.Company;
import main.entities.tables.Coupon;
import main.entities.tables.enums.CouponType;

@Service
public class CompanyService {
	
	@Autowired
	CompanyRepo companyRepo;
	
	public Company findById(int id) {
		return companyRepo.findOne(id);
	}
	
	public Company findByNameAndPassword(String compName, String password) {
		return companyRepo.findCompanyByCompNameAndPassword(compName, password);
	}
	
	public void save(Company company) {
		companyRepo.save(company);
	}
	
	public void delete(int id) {
		companyRepo.delete(id);
	}
	
	public Coupon getCouponById(Company company, int id) {
		return companyRepo.findCompanyCouponById(id, company);
	}
	
	public List<Coupon> getCouponsByPrice(Company company, double price) {
		return companyRepo.findCompanyCouponsByPrice(price, company);
	}
	
	public List<Coupon> getCouponsByDate(Company company, Long endDate) {
		return companyRepo.findCompanyCouponsByDate(endDate, company);
	}
	
	public List<Coupon> getCouponsByType(Company company, CouponType type) {
		return companyRepo.findCompanyCouponsByType(type, company);
	}
	
}
