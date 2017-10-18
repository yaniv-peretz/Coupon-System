package dao;

import java.util.HashSet;

import javaBeans.Company;
import javaBeans.Coupon;

public interface CompanyDAO {
	
	/**
	 * 
	 * @param comp create company and send to the DB, excluding coupons
	 */
	public void createCompany(Company comp);
	
	/**
	 * 
	 * @param comp remove company from DB, including coupons, and records of customers holding these coupons
	 */
	public void removeCompany(Company comp);
	
	/**
	 * 
	 * @param comp update company details: email, passwords excluding coupons.
	 */
	public void upadteCompany(Company comp);
	
	/**
	 * Get company by id number, including Coupons
	 * @param id
	 * @return
	 */
	public Company getCompany(long id);
	
	/**
	 * Get all Companies.
	 * @return
	 */
	public HashSet<Company> getAllCompanies();
	
	/**
	 * Get all Coupons.
	 * @return
	 */
	public HashSet<Coupon> getCoupons();
	
	/**
	 * Check if Company Name & Password combination exists. if successful than getLoginCompany() will return the last company to successfully login (will work once)
	 * @param compName
	 * @param password
	 * @return
	 */
	public boolean login(String compName, String password);

}
