package main.userFacades;

import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import main.entities.Interfaces.CompanyInterface;
import main.entities.Interfaces.CustomerInterface;
import main.entities.tables.Company;
import main.entities.tables.Customer;
import webComponents.WebCompany2;
import webComponents.WebCustomer2;;

@RestController
@RequestMapping(value = "admin-hb")
public class AdminFacade {
	
	@Autowired
	CustomerInterface customerInterface;
	@Autowired
	CompanyInterface companyInterface;
	
	private boolean getFacade(HttpServletRequest request, HttpServletResponse response) {
		return true;
	}
	
	// #################
	// Admin-Customer API
	// #################
	
	@RequestMapping(value = "customer/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<WebCustomer2> getCustomer(
			HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") int id) {
		
		if (!getFacade(request, response)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(null);
		}
		
		Customer customer = customerInterface.findOne(id);
		WebCustomer2 webCustomer = WebCustomer2.returnWebCustomer(customer);
		
		if (webCustomer != null) {
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCustomer);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(null);
			
		}
	}
	
	@RequestMapping(value = "customer", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> postCustomer(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody WebCustomer2 webCustomer) {
		
		if (!getFacade(request, response)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(null);
		}
		webCustomer.setId(0);
		Customer customer = WebCustomer2.returnCustomer(webCustomer);
		
		try {
			customerInterface.save(customer);
			webCustomer.setId(customer.getId());
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCustomer);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(null);
		}
		
	}
	
	@RequestMapping(value = "customer", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Object> updateCustomer(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody WebCustomer2 webCustomer) {
		
		if (!getFacade(request, response)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(null);
		}
		Customer customer = WebCustomer2.returnCustomer(webCustomer);
		
		try {
			customerInterface.save(customer);
			webCustomer.setId(customer.getId());
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCustomer);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(null);
		}
	}
	
	@RequestMapping(value = "customer", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Object> deleteCustomer(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody WebCustomer2 webCustomer) {
		
		if (!getFacade(request, response)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(null);
		}
		
		try {
			customerInterface.delete(webCustomer.getId());
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(null);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(null);
		}
	}
	
	@RequestMapping(value = "customer/all", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<HashSet<WebCustomer2>> doGetCustomers(
			HttpServletRequest request,
			HttpServletResponse response) {
		
		if (!getFacade(request, response)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(null);
		}
		
		HashSet<WebCustomer2> webCustomers = new HashSet<>();
		for (Customer customer : customerInterface.findAll()) {
			webCustomers.add(WebCustomer2.returnWebCustomer(customer));
		}
		
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCustomers);
	}
	
	// #################
	// Admin-Company API
	// #################
	
	@RequestMapping(value = "company/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<WebCompany2> getCompany(
			HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") int id) {
		
		if (!getFacade(request, response)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(null);
		}
		Company company = companyInterface.findOne(id);
		WebCompany2 webCompany = WebCompany2.retutnWebCompany(company);
		
		if (webCompany != null) {
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCompany);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(null);
			
		}
	}
	
	@RequestMapping(value = "company", method = RequestMethod.POST)
	public ResponseEntity<Object> postCompany(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody WebCompany2 webCompany) {
		
		webCompany.setId(0);
		if (!getFacade(request, response)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(null);
		}
		
		Company company = WebCompany2.retutnCompany(webCompany);
		try {
			companyInterface.save(company);
			webCompany.setId(company.getId());
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCompany);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
					.body(null);
		}
		
	}
	
	@RequestMapping(value = "company", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Object> updateCompany(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody WebCompany2 webCompany) {
		
		if (!getFacade(request, response)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(null);
		}
		
		Company company = WebCompany2.retutnCompany(webCompany);
		try {
			companyInterface.save(company);
			webCompany.setId(company.getId());
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCompany);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
					.body(null);
		}
	}
	
	@RequestMapping(value = "company", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Object> deleteCompany(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody WebCompany2 webCompany) {
		
		if (!getFacade(request, response)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(null);
		}
		
		try {
			companyInterface.delete(webCompany.getId());
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(null);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
					.body(null);
		}
	}
	
	@RequestMapping(value = "company/all", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> doGetCompanies(
			HttpServletRequest request,
			HttpServletResponse response) {
		
		if (!getFacade(request, response)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(null);
		}
		HashSet<WebCompany2> webCompanies = new HashSet<>();
		
		for (Company company : companyInterface.findAll()) {
			webCompanies.add(WebCompany2.retutnWebCompany(company));
		}
		if (0 < webCompanies.size()) {
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCompanies);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).contentType(MediaType.APPLICATION_JSON).body(null);
		}
	}
	
}