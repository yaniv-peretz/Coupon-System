package main;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import clientFacade.AdminFacade;
import clientFacade.ClientType;
import clientFacade.CompanyFacade;
import clientFacade.CustomerFacade;
import javaBeans.Company;
import javaBeans.Coupon;
import javaBeans.CouponType;
import javaBeans.Customer;

public class Main {

	public static void main(String[] args) {
		
		CouponSystem cs = new CouponSystem();
		
				
		System.out.println("###################");
		System.out.println("   starting test   ");
		System.out.println("###################");
		
		AdminFacade admin;
		String name;
		String password;
		ClientType clientType;
		
		name = "admin";
		password = "1234";
		clientType = ClientType.ADMIN;
		
		
		admin = (AdminFacade) cs.login(name, password, clientType);
		if (admin != null) {
			System.out.println("#### login successful as admin ####");
			
		}
		
		long id;
		Company comp;
		
		//adding companies
		System.out.println("");
		System.out.println("Creating companies: 250, 251, 252");
		comp = new Company(250L, "test250", "password250", "200email@email.com");
		admin.CreateCompany(comp);
		comp = new Company(251L, "test251", "password250", "200email@email.com");
		admin.CreateCompany(comp);
		comp = new Company(252L, "test252", "password250", "200email@email.com");
		admin.CreateCompany(comp);
		
		System.out.println("Ptinting Companies from DB:");
		System.out.println("----------------------");
		HashSet<Company> companies = admin.getAllCompanies();
		for(Company company : companies) {
			System.out.println(company);
		}
		
		System.out.println("");
		System.out.println("Updating comp = 251 with new password & email");
		id = 251L;
		comp = new Company(id, "test251", "new Password", "new email");
		// id & name cannot change
		admin.updateCompany(comp);
		comp = admin.getCompany(id);
		System.out.println("----------------------");
		System.out.println("got from DB: " + comp);
		System.out.println("NOTE the new password & email");
		
		
		
		
		
		
		//adding customers		
		System.out.println("");
		System.out.println("Creating Customers: 20, 21, 22");
		Customer cust;
		cust = new Customer(20L, "test20", "password200");
		admin.createCustomer(cust);
		cust = new Customer(21L, "test21", "password201");
		admin.createCustomer(cust);
		cust = new Customer(22L, "test22", "password202");
		admin.createCustomer(cust);
		
		System.out.println("Ptinting Customers from DB:");
		System.out.println("----------------------");
		HashSet<Customer> customers = admin.getAllCustomers();
		for(Customer customer : customers) {
			System.out.println(customer);
		}
		
		System.out.println("");
		System.out.println("Updating customer = 21 with new password");
		id = 21L;
		cust = new Customer(id, "test21", "new Password");
		// id & name cannot change
		admin.updateCustomer(cust);
		cust = admin.getCustomer(id);
		System.out.println("----------------------");
		System.out.println("got from DB: " + cust);
		System.out.println("NOTE the new password");
		
		
		
		/*
		 * ##############################
		 * Companies Facade test section
		 * ##############################
		 * 
		 */
		
		Coupon coupon;
		Calendar cal = Calendar.getInstance();
		Date startDate;
		Date endDate;
		clientType = ClientType.COMPANY;
		
		
		//login as company 251
		id = 251L;
		comp = admin.getCompany(id);
		name = comp.getCompName();
		password = comp.getPassword();
		CompanyFacade companyClient1 = (CompanyFacade) cs.login(name, password, clientType);
		System.out.println("");
		if (companyClient1 == null) {
			System.out.println("#### login successful as company 251 ####");
	
		}
		
		//add 2 coupons to company 251
		System.out.println("");
		System.out.println("adding 2 coupons company 251...");
		cal.set(2017, 1, 10);
		startDate = cal.getTime();
		
		cal.set(2017, 12, 31);
		endDate = cal.getTime();
		
		coupon = new Coupon(25100, "251-First", startDate, endDate, 100, CouponType.FOOD, "Burger", 1.5, "burger.gif");		
		companyClient1.createCoupon(coupon);
		
		cal.set(2017, 2, 12);
		startDate = cal.getTime();
		
		cal.set(2017, 10, 1);
		endDate = cal.getTime();
		
		coupon = new Coupon(25101, "251-second", startDate, endDate, 7, CouponType.HEALTH, "spa", 340, "spa.gif");		
		companyClient1.createCoupon(coupon);
		
		System.out.println("----------------------");
		for(Coupon c : companyClient1.getAllCoupons()) {
			System.out.println(c);
		}
		
		
		// Login as company 252
		id = 252L;
		comp = admin.getCompany(id);
		name = comp.getCompName();
		password = comp.getPassword();
		clientType = ClientType.COMPANY;
		CompanyFacade companyClient2 = (CompanyFacade) cs.login(name, password, clientType);
		System.out.println("");
		if (companyClient2 != null) {
			System.out.println("#### login successful as company 252 ####");
			
		}

		
		// add 2 coupons to company 252
		System.out.println("");
		System.out.println("adding 2 coupons company 252...");		
		cal.set(2017, 1, 1);
		startDate = cal.getTime();
		
		cal.set(2017, 12, 31);
		endDate = cal.getTime();
		
		coupon = new Coupon(25200, "252-First", startDate, endDate, 100, CouponType.FOOD, "Burger", 1.5, "burger2.gif");		
		companyClient2.createCoupon(coupon);
		
		cal.set(2017, 2, 1);
		startDate = cal.getTime();
		
		cal.set(2017, 11, 25);
		endDate = cal.getTime();
		
		coupon = new Coupon(25201, "252-second", startDate, endDate, 7, CouponType.HEALTH, "spa", 340, "spa2.gif");		
		companyClient2.createCoupon(coupon);
		
		//Company update coupon
		coupon.setPrice(999);
		
		cal.set(2017, 31, 12);
		endDate = cal.getTime();
		coupon.setEndDate(endDate);
		
		companyClient2.updateCoupon(coupon);
		
		System.out.println("----------------------");
		for(Coupon c : companyClient2.getAllCoupons()) {
			System.out.println(c);
		}
		
		
		// printing only company 251 coupons details
		System.out.println("");
		System.out.println("printing company 251 coupons:");
		System.out.println("----------------------------");
		System.out.println("company 251 - all coupons of: " + companyClient1.whichCompanyAmI().getId());
		System.out.println(companyClient1.getAllCoupons());
		
		System.out.println("company 251 - all coupons with price less than 200");
		System.out.println(companyClient1.getCouponsByPrice(200));
		
		Date testDate;
		cal.set(2017, 11, 1);
		testDate = cal.getTime();
		System.out.println("company 252 - all coupons with expiry date before: " + testDate.toString());
		System.out.println(companyClient2.getCouponsByDate(testDate));
		
		
		
		
		
		
		/*
		 * ##############################
		 * Customer Facade test section
		 * ##############################
		 * 
		 */
		
		System.out.println();
		
		id = 20L;
		cust = admin.getCustomer(id);
		
		CustomerFacade customer1 = new CustomerFacade();
		customer1 = customer1.login(cust.getCustName(), cust.getPassword(), ClientType.CUSTOMER);
		if(customer1 != null) {
			System.out.println("#### login successful as customer ####");
			
		}
		
		customer1.purchaseCoupon(coupon);
		System.out.println("allPurchasedCoupons:");
		System.out.println(customer1.allPurchasedCoupons());
		System.out.println("allPurchasedCouponsByPrice 600 (none):");
		System.out.println(customer1.allPurchasedCouponsByPrice(600));
		System.out.println("allPurchasedCouponsByType:" + coupon.getType());
		System.out.println(customer1.allPurchasedCouponsByType(coupon.getType()));
		
		
		System.out.println("\n\n\n");
		System.out.println("####################");
		System.out.println("   Shuting down  ");
		System.out.println("####################");
		
		System.out.println("");
		System.out.println("#### as admin ####");

		//Removing customers
		System.out.println("");
		System.out.println("Deleting customers");
		System.out.println("###################");
		cust = new Customer(20L, "test20", "password200");
		admin.removeCustomer(cust);
		cust = new Customer(21L, "test21", "password201");
		admin.removeCustomer(cust);
		cust = new Customer(22L, "test22", "password202");
		admin.removeCustomer(cust);
		System.out.println(admin.getAllCustomers()); 

		
		//Removing companies
		System.out.println("");
		System.out.println("Deleting companies");
		System.out.println("###################");		
		comp = new Company(250L, "test250", "password250", "200email@email.com");
		admin.removeCompany(comp);
		comp = new Company(251L, "test251", "password250", "200email@email.com");
		admin.removeCompany(comp);
		comp = new Company(252L, "test252", "password250", "200email@email.com");
		admin.removeCompany(comp);
		System.out.println(admin.getAllCompanies());
		
		
		System.out.println();
		System.out.println("##### Shutting Down #####");
		cs.shutDown();
	}
}


