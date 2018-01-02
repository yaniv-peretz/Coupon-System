package main.entities.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.entities.repoInterfaces.CouponRepo;
import main.entities.repoInterfaces.CustomerRepo;
import main.entities.tables.Coupon;
import main.entities.tables.Customer;
import main.entities.tables.enums.CouponType;

@Service
public class CustomerService {
	
	@Autowired
	CustomerRepo customerRepo;
	
	@Autowired
	CouponRepo couponRepo;
	
	public void saveCustomer(Customer customer) {
		customerRepo.save(customer);
	}
	
	public void deleteCustomer(int id) {
		customerRepo.delete(id);
	}
	
	public Customer findCustomerById(int id) {
		return customerRepo.findOne(id);
	}
	
	public Customer findCustomerByNameAndPassword(String name, String password) {
		return customerRepo.findCustomerByNameAndPassword(name, password);
	}
	
	public Coupon getCouponById(Customer customer, int id) {
		return couponRepo.findOne(id);
	}
	
	public List<Coupon> getAllCoupons(Customer customer) {
		customer = customerRepo.findOne(customer.getId());
		return customer.getCoupons();
	}
	
	public List<Coupon> getCouponsByPrice(Customer customer, double price) {
		customer = customerRepo.findOne(customer.getId());
		return customer.getCouponsByPrice(price);
	}
	
	public List<Coupon> getCouponsByDate(Customer customer, Long endDate) {
		customer = customerRepo.findOne(customer.getId());
		return customer.getCouponsByDate(endDate);
	}
	
	public List<Coupon> getCouponsByType(Customer customer, CouponType type) {
		customer = customerRepo.findOne(customer.getId());
		return customer.getCouponsByType(type);
	}
	
	public void purchaseCoupons(List<Integer> couponIds, int comp_id) {
		Customer customer = customerRepo.findOne(comp_id);
		Set<Coupon> uniqueCoupons = new HashSet<>(customer.getCoupons());
		
		for (int id : couponIds) {
			Coupon coupon = couponRepo.findOne(id);
			if (!uniqueCoupons.contains(coupon) && 0 < coupon.getAmount()) {
				coupon.setAmount(coupon.getAmount() - 1);
				uniqueCoupons.add(coupon);
			}
		}
		
		customer.setCoupons(new ArrayList<>(uniqueCoupons));
		customerRepo.save(customer);
	}
	
}
