package YanivProducts.CouponSystemServer;

import java.util.HashSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import clientFacade.AdminFacade;
import clientFacade.ClientType;
import javaBeans.Company;
import javaBeans.Customer;
import webJavaBeans.WebCompany;
import webJavaBeans.WebCustomer;;


@Path("/admin")
public class Admin {
	
	private AdminFacade getFacade() {
		AdminFacade admin = new AdminFacade().login("admin", "1234", ClientType.ADMIN);		
		return admin;
		
	}
		
	//#################
	//Admin-Customer API 
	//#################	
	
	@GET
	@Path("/customer/{id}")
	@Produces("application/json")
	public Customer getCustomer(@PathParam("id") int id){
		
		AdminFacade admin = getFacade();		
		
		Customer cust = admin.getCustomer(id);		
		return cust;
	}
	
	
	@POST
	@Path("/customer")
	@Consumes("application/json")
	public void postCustomer(WebCustomer webcust){
		
		AdminFacade admin = getFacade();
		Customer cust = webcust.getCustomer(); 
		
		admin.createCustomer(cust);
	}
	

	@PUT
	@Path("/customer")
	@Consumes("application/json")
	public void updateCustomer(WebCustomer webcust){
		
		AdminFacade admin = getFacade();
		Customer cust = webcust.getCustomer(); 
		
		admin.updateCustomer(cust);
	}
	
	
	@DELETE
	@Path("/customer")
	@Consumes("application/json")
	public void deleteCustomer(WebCustomer webcust){
		
		AdminFacade admin = getFacade();
		Customer cust = webcust.getCustomer(); 
		
		admin.removeCustomer(cust);
	}
	
	
	@GET
	@Path("/customer/all")
	@Produces("application/json")
	public HashSet<Customer> doGetCustomers() {
		
		AdminFacade admin = getFacade();		
		
		HashSet<Customer> companies = admin.getAllCustomers();
		return companies;
	}
		
	
	//#################
	//Admin-Company API 
	//#################	
	
	@GET
	@Path("/company/{id}")
	@Produces("application/json")
	public Company getCompany(@PathParam("id") int id){
		
		AdminFacade admin = getFacade();		
		
		Company comp = admin.getCompany(id);		
		return comp;
	}
	
	
	@POST
	@Path("/company")
	@Consumes("application/json")
	public void postCompany(WebCompany webComp){
		
		AdminFacade admin = getFacade();
		Company comp = webComp.getCompany(); 
		
		admin.CreateCompany(comp);
	}
	

	@PUT
	@Path("/company")
	@Consumes("application/json")
	public void updateCompany(WebCompany webComp){
		
		AdminFacade admin = getFacade();
		Company comp = webComp.getCompany(); 
		
		admin.updateCompany(comp);
	}
	
	
	@DELETE
	@Path("/company")
	@Consumes("application/json")
	public void deleteCompany(WebCompany webComp){
		
		AdminFacade admin = getFacade();
		Company comp = webComp.getCompany(); 
		
		admin.removeCompany(comp);
	}
	
	
	@GET
	@Path("/company/all")
	@Produces("application/json")
	public HashSet<Company> doGetCompanies(){
		
		AdminFacade admin = getFacade();		
		
		HashSet<Company> companies = admin.getAllCompanies();		
		return companies;
	}
	

}
