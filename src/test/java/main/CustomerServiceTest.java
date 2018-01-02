package main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import main.entities.repoInterfaces.CouponRepo;
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
	CompanyService companyService;
	@Autowired
	CouponRepo couponRepo;
	@Autowired
	CustomerService customerService;
	
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
		companyService.save(company);
		
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
		
		customer.setCoupons(coupons);
		customerService.saveCustomer(customer);
		customer = customerService.findCustomerById(customer.getId());
		System.out.println(customer);
		for (Coupon coupon : customer.getCoupons()) {
			System.out.println(coupon);
		}
		
		couponRepo.delete(coupon1);
		
		// customerService.deleteCustomer(customer.getId());
		// companyService.delete(company.getId());
	}
	
}
