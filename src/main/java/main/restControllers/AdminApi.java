package main.restControllers;

import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import main.entities.services.AdminService;
import main.entities.tables.Company;
import main.entities.tables.Customer;
import webComponents.WebCompany;
import webComponents.WebCustomer;;

@RestController
@RequestMapping(value = "admin")
public class AdminApi {
	
	@Autowired
	AdminService adminService;
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean getFacade(HttpServletRequest request, HttpServletResponse response) {
		HttpSession httpSession = request.getSession();
		
		// TODO: STOP fake the login##################################
		httpSession.setAttribute("auth", true);
		
		// Get the company from the session
		return (boolean) httpSession.getAttribute("auth");
	}
	
	// #################
	// Admin-Customer API
	// #################
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return get customer by Id
	 */
	
	@RequestMapping(value = "customer/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<WebCustomer> getCustomer(
			HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") int id) {
		
		if (!getFacade(request, response)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(null);
		}
		
		Customer customer = adminService.findCustomer(id);
		if (customer != null) {
			WebCustomer webCustomer = WebCustomer.returnWebCustomer(customer);
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCustomer);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param webCustomer
	 * @return create customer
	 */
	@RequestMapping(value = "customer", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> postCustomer(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody WebCustomer webCustomer) {
		
		if (!getFacade(request, response)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(null);
		}
		
		webCustomer.setId(0);
		Customer customer = WebCustomer.returnCustomer(webCustomer);
		if (!adminService.isCustNameExists(webCustomer.getCustName())) {
			try {
				adminService.createCustomer(customer);
				webCustomer.setId(customer.getId());
				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCustomer);
			} catch (RuntimeException e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			
		} else {
			String msg = "customer name exists";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param webCustomer
	 * @return update customer
	 */
	@RequestMapping(value = "customer", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Object> updateCustomer(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody WebCustomer webCustomer) {
		
		if (!getFacade(request, response)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(null);
		}
		
		Customer customer = WebCustomer.returnCustomer(webCustomer);
		if (!adminService.isCustNameExists(webCustomer.getCustName())) {
			try {
				adminService.createCustomer(customer);
				webCustomer.setId(customer.getId());
				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCustomer);
			} catch (RuntimeException e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			
		} else {
			String msg = "customer name exists";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param webCustomer
	 * @return delete customer
	 */
	@RequestMapping(value = "customer/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Object> deleteCustomer(
			HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") int id) {
		
		if (!getFacade(request, response)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(null);
		}
		
		if (adminService.findCustomer(id) != null) {
			try {
				adminService.deleteCustomer(id);
				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("");
			} catch (RuntimeException e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			
		} else {
			String msg = "customer id: " + id + " not exists!";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return get all customers
	 */
	@RequestMapping(value = "customer/all", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<HashSet<WebCustomer>> doGetCustomers(
			HttpServletRequest request,
			HttpServletResponse response) {
		
		if (!getFacade(request, response)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(null);
		}
		HashSet<WebCustomer> webCustomers = new HashSet<>();
		for (Customer customer : adminService.findAllCustomers()) {
			webCustomers.add(WebCustomer.returnWebCustomer(customer));
		}
		
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCustomers);
	}
	
	// #################
	// Admin-Company API
	// #################
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return get company by id
	 */
	@RequestMapping(value = "company/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<WebCompany> getCompany(
			HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") int id) {
		
		if (!getFacade(request, response)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(null);
		}
		
		Company company = adminService.findCompany(id);
		if (company != null) {
			WebCompany webCompany = WebCompany.retutnWebCompany(company);
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCompany);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(null);
			
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param webCompany
	 * @return create company
	 */
	@RequestMapping(value = "company", method = RequestMethod.POST)
	public ResponseEntity<Object> postCompany(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody WebCompany webCompany) {
		
		if (!getFacade(request, response)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(null);
		}
		
		webCompany.setId(0);
		Company company = WebCompany.retutnCompany(webCompany);
		try {
			adminService.createCompany(company);
			webCompany.setId(company.getId());
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCompany);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
					.body(null);
		}
		
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param webCompany
	 * @return update company
	 */
	@RequestMapping(value = "company", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Object> updateCompany(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody WebCompany webCompany) {
		
		if (!getFacade(request, response)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(null);
		}
		
		if (adminService.isCompNameExists(webCompany.getCompName())) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).contentType(MediaType.APPLICATION_JSON)
					.body("name already exists");
		}
		
		// company name is unique
		try {
			Company company = WebCompany.retutnCompany(webCompany);
			adminService.updateCompany(company);
			webCompany.setId(company.getId());
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCompany);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).contentType(MediaType.APPLICATION_JSON)
					.body("name already exists");
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return delete company by id
	 */
	@RequestMapping(value = "company/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Object> deleteCompany(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("id") int id) {
		
		if (!getFacade(request, response)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(null);
		}
		
		try {
			adminService.deleteCompany(id);
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("");
		} catch (RuntimeException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
					.body("company with id: " + id + " not exists");
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return get all companies
	 */
	@RequestMapping(value = "company/all", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> doGetCompanies(
			HttpServletRequest request,
			HttpServletResponse response) {
		
		if (!getFacade(request, response)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(null);
		}
		HashSet<WebCompany> webCompanies = new HashSet<>();
		
		for (Company company : adminService.findAllCompanies()) {
			webCompanies.add(WebCompany.retutnWebCompany(company));
		}
		if (0 < webCompanies.size()) {
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCompanies);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).contentType(MediaType.APPLICATION_JSON).body(null);
		}
	}
	
}