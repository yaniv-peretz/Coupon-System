package main.entities.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.entities.repoInterfaces.CompanyRepo;
import main.entities.repoInterfaces.CustomerRepo;
import main.entities.tables.Company;
import main.entities.tables.Customer;

@Service
public class AdminService {
	
	public boolean login(String user, String password) {
		return (user.equals("admin") && password.equals("1234"));
		
	}
	
	@Autowired
	CompanyRepo companyRepo;
	
	// @formatter:off
		/*	##################
		 * 		Company
		 * 	################## */
		// @formatter:on
	
	public Company findCompany(int id) {
		return companyRepo.findOne(id);
	}
	
	public boolean isCompNameExists(String compName) {
		Company comp = companyRepo.findCompanuByCompName(compName);
		return comp == null;
	}
	
	public Company createCompany(Company company) {
		company.setId(0);
		return updateCompany(company);
	}
	
	public Company updateCompany(Company company) {
		return companyRepo.save(company);
	}
	
	public boolean deleteCompany(int id) {
		try {
			companyRepo.delete(id);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	public Iterable<Company> findAllCompanies() {
		return companyRepo.findAll();
	}
	
	// @formatter:off
	/*	##################
	 * 		Customer
	 * 	################## */
	// @formatter:on
	
	@Autowired
	CustomerRepo customerRepo;
	
	public Customer findCustomer(int id) {
		return customerRepo.findOne(id);
	}
	
	public boolean isCustNameExists(String custName) {
		Customer cust = customerRepo.findCustomerByName(custName);
		if (cust != null) {
			return true;
		}
		return false;
	}
	
	public Customer createCustomer(Customer customer) {
		customer.setId(0);
		return updateCustomer(customer);
	}
	
	public Customer updateCustomer(Customer customer) {
		return customerRepo.save(customer);
	}
	
	public boolean deleteCustomer(int id) {
		try {
			customerRepo.delete(id);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	public Iterable<Customer> findAllCustomers() {
		return customerRepo.findAll();
	}
	
}
