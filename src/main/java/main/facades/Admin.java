package main.facades;

import java.util.HashSet;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import clientFacade.AdminFacade;
import clientFacade.ClientType;
import javaBeans.Company;
import javaBeans.Customer;
import webComponents.WebCompany;
import webComponents.WebCustomer;;

@RestController
@RequestMapping("/admin")
public class Admin {
	
	private AdminFacade getFacade() {
		AdminFacade admin = new AdminFacade().login("admin", "1234", ClientType.ADMIN);
		return admin;
		
	}
	
	// #################
	// Admin-Customer API
	// #################
	
	@RequestMapping(value = "customer/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<WebCustomer> getCustomer(@PathVariable("id") int id) {
		
		AdminFacade admin = getFacade();
		
		Customer Customer = admin.getCustomer(id);
		WebCustomer webCustomer = WebCustomer.returnWebCustomer(Customer);
		
//		@formatter:off
		return ResponseEntity.
				status(HttpStatus.OK).
				contentType(MediaType.APPLICATION_JSON).
				body(webCustomer);
//		@formatter:on
	}
	
	@RequestMapping(value = "customer", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> postCustomer(@RequestBody WebCustomer webCustomer) {
		
		AdminFacade admin = getFacade();
		Customer cust = WebCustomer.returnCustomer(webCustomer);
		
		admin.createCustomer(cust);
//		@formatter:off
		return ResponseEntity.
				status(HttpStatus.OK).
				contentType(MediaType.APPLICATION_JSON).
				body(null);
//		@formatter:on
	}
	
	@RequestMapping(value = "customer", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Object> updateCustomer(@RequestBody WebCustomer webCustomer) {
		
		AdminFacade admin = getFacade();
		Customer cust = WebCustomer.returnCustomer(webCustomer);
		
		admin.updateCustomer(cust);
//		@formatter:off
		return ResponseEntity.
				status(HttpStatus.OK).
				contentType(MediaType.APPLICATION_JSON).
				body(null);
//		@formatter:on
	}
	
	@RequestMapping(value = "customer", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Object> deleteCustomer(@RequestBody WebCustomer webCustomer) {
		
		AdminFacade admin = getFacade();
		Customer cust = WebCustomer.returnCustomer(webCustomer);
		
		admin.removeCustomer(cust);
//		@formatter:off
		return ResponseEntity.
				status(HttpStatus.OK).
				contentType(MediaType.APPLICATION_JSON).
				body(null);
//		@formatter:on
	}
	
	@RequestMapping(value = "customer/all", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<HashSet<WebCustomer>> doGetCustomers() {
		
		AdminFacade admin = getFacade();
		
		HashSet<Customer> companies = admin.getAllCustomers();
		HashSet<WebCustomer> webCompanies = new HashSet<>();
		for (Customer customer : companies) {
			webCompanies.add(new WebCustomer(customer));
		}
//		@formatter:off
		return ResponseEntity.
				status(HttpStatus.OK).
				contentType(MediaType.APPLICATION_JSON).
				body(webCompanies);
//		@formatter:on
	}
	
	// #################
	// Admin-Company API
	// #################
	
	@RequestMapping(value = "company/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<WebCompany> getCompany(@PathVariable("id") int id) {
		
		AdminFacade admin = getFacade();
		
		Company comp = admin.getCompany(id);
		WebCompany webComp = new WebCompany(comp);
//		@formatter:off
		return ResponseEntity.
				status(HttpStatus.OK).
				contentType(MediaType.APPLICATION_JSON).
				body(webComp);
//		@formatter:on
	}
	
	@RequestMapping(value = "company", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> postCompany(@RequestBody WebCompany webCompany) {
		
		AdminFacade admin = getFacade();
		Company comp = WebCompany.retutnCompany(webCompany);
		
		admin.CreateCompany(comp);
//		@formatter:off
		return ResponseEntity.
				status(HttpStatus.OK).
				contentType(MediaType.APPLICATION_JSON).
				body(null);
//		@formatter:on
	}
	
	@RequestMapping(value = "company", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Object> updateCompany(@RequestBody WebCompany webCompany) {
		
		AdminFacade admin = getFacade();
		Company comp = WebCompany.retutnCompany(webCompany);
		
		admin.updateCompany(comp);
//		@formatter:off
		return ResponseEntity.
				status(HttpStatus.OK).
				contentType(MediaType.APPLICATION_JSON).
				body(null);
//		@formatter:on
	}
	
	@RequestMapping(value = "company", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Object> deleteCompany(@RequestBody WebCompany webCompany) {
		
		AdminFacade admin = getFacade();
		Company comp = WebCompany.retutnCompany(webCompany);
		
		admin.removeCompany(comp);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(null);
		//		@formatter:on
	}
	
	@RequestMapping(value = "/company/all", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> doGetCompanies() {
		
		AdminFacade admin = getFacade();
		
		HashSet<Company> companies = admin.getAllCompanies();
		HashSet<WebCompany> webCompanies = new HashSet<>();
		for (Company comp : companies) {
			webCompanies.add(WebCompany.retutnWebCompany(comp));
		}
//		@formatter:off
		return ResponseEntity.
				status(HttpStatus.OK).
				contentType(MediaType.APPLICATION_JSON).
				body(webCompanies);
//		@formatter:on
	}
	
}