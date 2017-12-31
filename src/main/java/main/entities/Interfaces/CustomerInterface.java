package main.entities.Interfaces;

import org.springframework.data.repository.CrudRepository;
import main.entities.tables.Customer;

public interface CustomerInterface extends CrudRepository<Customer, Integer> {
	
	// @Query("DELETE FROM customer_coupons WHERE customer_id = :cust_id")
	// public void deleteAllCustomerCoupons(@Param("cust_id") int cust_id);
	
}
