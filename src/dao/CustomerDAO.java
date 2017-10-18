package dao;

import java.util.HashSet;
import javaBeans.Customer;
import javaBeans.Coupon;

public interface CustomerDAO {

	/**
	 * Create new Customer into DB (does not capture Customer Coupons)
	 * @param cust
	 */
	public void createCustomer(Customer cust);
	
	/**
	 * Remove new Customer into DB
	 * @param cust
	 */
	public void removeCustomer(Customer cust);
	
	/**
	 * update Customer password
	 * @param cust
	 */
	public void updateCustomer(Customer cust);
	
	/**
	 * Get customer by id number
	 * @param id
	 * @return
	 */
	public Customer getCustomer(long id);
	
	/**
	 * Get all customers
	 * @return
	 */
	public HashSet<Customer> getAllCustomers();
	
	/**
	 * Get all Coupons
	 * @return
	 */
	public HashSet<Coupon> getCoupons();
	
	/**
	 * Login by user name and password
	 * @param custName
	 * @param password
	 * @return
	 */
	public boolean login (String custName, String password);
}
