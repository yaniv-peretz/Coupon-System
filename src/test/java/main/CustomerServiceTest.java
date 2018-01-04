package main;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import main.entities.repoInterfaces.CouponRepo;
import main.entities.repoInterfaces.CustomerRepo;
import main.entities.services.AdminService;
import main.entities.services.CompanyService;
import main.entities.services.CustomerService;
import main.entities.tables.Company;
import main.entities.tables.Coupon;
import main.entities.tables.Customer;
import main.entities.tables.enums.CouponType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {
	static int index = 0;
	
	@Autowired
	AdminService adminService;
	@Autowired
	CompanyService companyService;
	@Autowired
	CouponRepo couponRepo;
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CustomerRepo customerRepo;
	
	@Before
	public void testDummy() {
		System.out.println("### starting test num: " + index++ + " ###");
	}
	
	@Test
	public void saveCustomer() {
		Customer customer = new Customer("name", "password");
		customerService.saveCustomer(customer);
		System.out.println(customer);
	}
	
	@Test
	public void saveCustomergetCoupons() {
		// Create new test company
		Company company = new Company("Unique45768", "password4");
		adminService.createCompany(company);
		
		double price1 = 10, price2 = 15;
		CouponType type = CouponType.FOOD, diffType = CouponType.ELECTRICITY;
		Coupon coupon1 = new Coupon("title1", 100, new Date().getTime(), new Date().getTime(), type, "message", price1,
				"sd", company);
		Coupon coupon2 = new Coupon("title2", 100, new Date().getTime(), new Date().getTime(), diffType, "message",
				price2, "sd", company);
		
		couponRepo.save(coupon1);
		couponRepo.save(coupon2);
		
		Customer customer = new Customer("Unique765434", "password");
		customerService.saveCustomer(customer);
		List<Coupon> coupons = new ArrayList<>();
		coupon1.setAmount(coupon1.getAmount() - 1);
		coupon2.setAmount(coupon2.getAmount() - 1);
		couponRepo.save(coupon1);
		couponRepo.save(coupon2);
		coupons.add(coupon1);
		coupons.add(coupon2);
		
		Set<Integer> couponIds = new HashSet<>();
		couponIds.add(coupon1.getId());
		couponIds.add(coupon2.getId());
		customerService.purchaseCoupons(couponIds, customer.getId());
		customerService.saveCustomer(customer);
		customer = customerService.findCustomerById(customer.getId());
		System.out.println(customer);
		for (Coupon coupon : customerService.getAllCoupons(customer)) {
			System.out.println(coupon);
		}
		
		adminService.deleteCustomer(customer.getId());
		adminService.deleteCompany(company.getId());
	}
	
	@Test
	public void getCustomerCouponsLists() {
		Company company = new Company("Unique45768", "password4");
		adminService.createCompany(company);
		
		double price1 = 10, price2 = 15;
		CouponType type = CouponType.FOOD, diffType = CouponType.ELECTRICITY;
		Coupon coupon1 = new Coupon("title1", 100, new Date().getTime(), new Date().getTime(), type, "message", price1,
				"sd", company);
		Coupon coupon2 = new Coupon("title2", 100, new Date().getTime(), new Date().getTime(), diffType, "message",
				price2, "sd", company);
		couponRepo.save(coupon1);
		couponRepo.save(coupon2);
		
		Customer customer = new Customer("Unique765434", "password");
		customerService.saveCustomer(customer);
		List<Coupon> coupons = new ArrayList<>();
		coupon1.setAmount(coupon1.getAmount() - 1);
		coupon2.setAmount(coupon2.getAmount() - 1);
		couponRepo.save(coupon1);
		couponRepo.save(coupon2);
		coupons.add(coupon1);
		coupons.add(coupon2);
		
		Set<Coupon> arr = customerRepo.getAllCustomerCoupons(customer.getId());
		for (Coupon coupon : arr) {
			System.out.println("all coupons: " + coupon);
		}
		System.out.println("##############################");
		
		arr = customerRepo.getAllCustomerCouponsbyPrice(customer.getId(), 11);
		for (Coupon coupon : arr) {
			System.out.println("all coupons by price 11: " + coupon);
		}
		System.out.println("##############################");
		
		arr = customerRepo.getAllCustomerCouponsByType(customer.getId(), CouponType.FOOD);
		for (Coupon coupon : arr) {
			System.out.println("all coupons by type FOOD: " + coupon);
		}
		System.out.println("##############################");
		
		arr = customerRepo.getAllCustomerCouponsbyDate(1, 1514993980265L);
		for (Coupon coupon : arr) {
			System.out.println("all coupons by date 1514993980265L: " + coupon);
		}
		System.out.println("##############################");
		
	}
	
}
