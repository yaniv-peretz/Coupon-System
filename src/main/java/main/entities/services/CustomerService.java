package main.entities.services;

import java.util.HashSet;
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
	
	public Set<Coupon> getAllCoupons(Customer customer) {
		return customerRepo.getAllCustomerCoupons(customer.getId());
	}
	
	public Set<Coupon> getCouponsByPrice(Customer customer, double price) {
		return customerRepo.getAllCustomerCouponsbyPrice(customer.getId(), price);
	}
	
	public Set<Coupon> getCouponsByDate(Customer customer, Long endDate) {
		return customerRepo.getAllCustomerCouponsbyDate(customer.getId(), endDate);
	}
	
	public Set<Coupon> getCouponsByType(Customer customer, CouponType type) {
		return customerRepo.getAllCustomerCouponsByType(customer.getId(), type);
	}
	
	public void purchaseCoupons(Set<Integer> couponIds, int cust_id) {
		Customer customer = customerRepo.findCustomerById(cust_id);
		if (customer != null) {
			Set<Coupon> uniqueCoupons = new HashSet<>();
			for (int coup_id : couponIds) {
				if (customerRepo.getCustomerCouponById(cust_id, coup_id) == null) {
					Coupon coupon = couponRepo.findOne(coup_id);
					if (coupon != null && 0 < coupon.getAmount()) {
						uniqueCoupons.add(coupon);
						coupon.setAmount(coupon.getAmount() - 1);
						couponRepo.save(coupon);
					}
				}
			}
			
			uniqueCoupons.addAll(customerRepo.getAllCustomerCoupons(cust_id));
			// TODO Save coupons without bringing all coupons back
			customer.setCoupons(uniqueCoupons);
			customerRepo.save(customer);
			
		}
	}
	
}
