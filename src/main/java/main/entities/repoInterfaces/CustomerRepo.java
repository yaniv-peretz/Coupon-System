package main.entities.repoInterfaces;

import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import main.entities.tables.Coupon;
import main.entities.tables.Customer;
import main.entities.tables.enums.CouponType;

public interface CustomerRepo extends CrudRepository<Customer, Integer> {
	
	Customer findCustomerByNameAndPassword(String name, String password);
	
	Customer findCustomerById(int id);
	
	Customer findCustomerByName(String name);
	
	Customer findCustomerByIdAndName(int it, String name);
	
	@Query("SELECT coup FROM Coupon coup WHERE coup.id IN (SELECT coup.id FROM coup.customers cust WHERE cust.id=?1 AND coup.id = ?2)")
	Coupon getCustomerCouponById(int cust_id, int coup_id);
	
	@Query("SELECT coup FROM Coupon coup WHERE coup.id IN (SELECT coup.id FROM coup.customers cust WHERE cust.id=?1)")
	Set<Coupon> getAllCustomerCoupons(int cust_id);
	
	@Query("SELECT coup FROM Coupon coup WHERE coup.price < ?2 AND coup.id IN (SELECT coup.id FROM coup.customers cust WHERE cust.id=?1)")
	Set<Coupon> getAllCustomerCouponsbyPrice(int cust_id, double price);
	
	@Query("SELECT coup FROM Coupon coup WHERE coup.endDate < ?2 AND coup.id IN (SELECT coup.id FROM coup.customers cust WHERE cust.id=?1)")
	Set<Coupon> getAllCustomerCouponsbyDate(int cust_id, long endDate);
	
	@Query("SELECT coup FROM Coupon coup WHERE type = ?2 AND coup.id IN (SELECT coup.id FROM coup.customers cust WHERE cust.id=?1)")
	Set<Coupon> getAllCustomerCouponsByType(int cust_id, CouponType type);
}
