package main;

import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import main.entities.services.AdminService;
import main.entities.tables.Company;
import main.entities.tables.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceTest {
	static int index = 0;
	
	@Autowired
	AdminService adminService;
	
	@Before
	public void testDummy() {
		System.out.println("### starting test num: " + index++ + " ###");
		
	}
	
	// @Test
	// public void allCompanies() {
	// for (Company company : adminService.findAllCompanies()) {
	// System.out.println(company);
	// }
	// }
	
	// @Test
	// public void allCustomers() {
	// for (Customer customer : adminService.findAllCustomers()) {
	// System.out.println(customer);
	// }
	// }
	
	@Test
	public void crudCompany() {
		// create company
		Company company = new Company("comp", "password");
		int preSaveId = company.getId();
		System.out.println(company);
		
		// save the company
		adminService.createCompany(company);
		int postSaveId = company.getId();
		System.out.println(company);
		assertFalse(preSaveId == postSaveId);
		
		// update the company
		company.setCompName("changed");
		company.setPassword("changed");
		company.setEmail("changed");
		adminService.updateCompany(company);
		System.out.println(adminService.findCompany(company.getId()));
		
		adminService.deleteCompany(company.getId());
		
	}
	
	@Test
	public void crudCustomer() {
		// create customer
		Customer customer = new Customer("cust", "password");
		int preSaveId = customer.getId();
		System.out.println(customer);
		
		// save the customer
		adminService.createCustomer(customer);
		int postSaveId = customer.getId();
		System.out.println(customer);
		assertFalse(preSaveId == postSaveId);
		
		// update the customer
		customer.setName("changed");
		customer.setPassword("changed");
		adminService.updateCustomer(customer);
		System.out.println(adminService.findCustomer(customer.getId()));
		
		adminService.deleteCustomer(customer.getId());
		
	}
}
