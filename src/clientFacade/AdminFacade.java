package clientFacade;
import java.util.HashSet;

import dao.CompanyDBDAO;
import dao.CustomerDBDAO;
import javaBeans.Company;
import javaBeans.Customer;

public class AdminFacade implements CouponClientFacade {
	
	private CompanyDBDAO compDAO;
	private CustomerDBDAO custDAO;


	public AdminFacade() {
		super();		

	} 
	
	@Override
	public AdminFacade login(String name, String password, ClientType clientType) {
		
		AdminFacade adminFacade = null;
		
		if (name == "admin" &&
				password == "1234" &&
				clientType == ClientType.ADMIN ) {
			
			compDAO = new CompanyDBDAO();
			custDAO = new CustomerDBDAO();
			adminFacade = this;
			
		}else {
			String message = "Admin login failed check credentials: name: " + name + " password: " + password;
			throw new RuntimeException(message);
			
		}
			
		return adminFacade;
	}
	
	/**
	 * Create a new Company in DB (Company coupons will not be updated)
	 * @param comp
	 */
	public void CreateCompany(Company comp) {
		compDAO.createCompany(comp);
		
	}
	
	/**
	 * Remove Company from DB (including company Coupons, and records of Customer holding these coupons).
	 * @param comp
	 */
	public void removeCompany(Company comp) {		
		compDAO.removeCompany(comp);
		
	}

	/**
	 * Update Company details: email, passwords excluding coupons.
	 * @param comp
	 */
	public void updateCompany(Company comp) {
		compDAO.upadteCompany(comp);
		
	}
	
	/**
	 * Get Company by id (including coupons)
	 * @param id
	 * @return
	 */
	public Company getCompany(long id) {		
		Company comp = compDAO.getCompany(id); 
		return comp;
		
	}
	
	/**
	 * Get all Companies
	 */
	public HashSet<Company> getAllCompanies(){
		HashSet<Company> companies = compDAO.getAllCompanies();
		return companies;
		
	}
	
	/**
	 * Create customer in DB (excluding coupons).
	 * @param cust
	 */
	public void createCustomer(Customer cust) {
		custDAO.createCustomer(cust);
		
	}
	
	/**
	 * Remove customer from DB, including records customer coupons holding.
	 * @param cust
	 */
	public void removeCustomer(Customer cust) {
		custDAO.removeCustomer(cust);
		
	}
	
	/**
	 * Update customer password
	 * @param cust
	 */
	public void updateCustomer(Customer cust) {
		custDAO.updateCustomer(cust);
		
	}
	
	/**
	 * Get all customers
	 * @return
	 */
	public HashSet<Customer> getAllCustomers() {
		HashSet<Customer> customers = custDAO.getAllCustomers();
		return customers;
		
	}
	
	/**
	 * Get customer by id
	 * @param id
	 * @return
	 */
	public Customer getCustomer(long id) {
		Customer customer = custDAO.getCustomer(id);
		return customer;
		
	}
	
}
