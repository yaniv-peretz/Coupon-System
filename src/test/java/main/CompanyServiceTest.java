package main;

import static org.junit.Assert.assertTrue;
import java.util.Calendar;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import main.entities.repoInterfaces.CouponRepo;
import main.entities.services.CompanyService;
import main.entities.tables.Company;
import main.entities.tables.Coupon;
import main.entities.tables.enums.CouponType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyServiceTest {
	static int index = 0;
	
	@Autowired
	CompanyService companyService;
	@Autowired
	CouponRepo couponRepo;
	
	@Before
	public void testDummy() {
		System.out.println("### starting test num: " + index++ + " ###");
	}
	
	@Test
	public void saveEditCustomer() {
		Company company = new Company("compName", "password");
		companyService.save(company);
		System.out.println(company);
		
		company.setCompName("changed");
		company.setEmail("changed");
		company.setPassword("changed");
		companyService.save(company);
		
		System.out.println(companyService.findById(company.getId()));
		System.out.println(companyService.findByNameAndPassword(company.getCompName(), company.getPassword()));
		
	}
	
	@Test
	public void handleCompanyCoupons() {
		Company company = new Company("compName2", "password2");
		companyService.save(company);
		
		Coupon coupon = new Coupon("title", 100, new Date().getTime(), new Date().getTime(), CouponType.FOOD, "message",
				5.5, "sd", company);
		couponRepo.save(coupon);
		
		company = companyService.findById(company.getId());
		for (Coupon c : company.getCoupons()) {
			System.out.println(c);
		}
		
	}
	
	@Test
	public void handleCompanyCouponsBySelections() {
		Company company = new Company("compName3", "password4");
		companyService.save(company);
		
		double price1 = 10;
		double price2 = 15;
		CouponType type = CouponType.FOOD;
		CouponType diffType = CouponType.ELECTRICITY;
		Coupon coupon1 = new Coupon("title1", 100, new Date().getTime(), new Date().getTime(), type,
				"message",
				price1, "sd", company);
		
		Coupon coupon2 = new Coupon("title2", 100, new Date().getTime(), new Date().getTime(), diffType,
				"message",
				price2, "sd", company);
		
		couponRepo.save(coupon1);
		couponRepo.save(coupon2);
		
		// test by price
		company = companyService.findById(company.getId());
		for (Coupon c : company.getCouponsByPrice(price1)) {
			System.out.println("by price: " + c);
			assertTrue(c.getPrice() <= price1);
		}
		
		// test by type
		for (Coupon c : company.getCouponsByType(type)) {
			System.out.println("by type: " + c);
			assertTrue(c.getType() == type);
		}
		
		// test by date
		Calendar cal = Calendar.getInstance();
		cal.set(2020, 12, 1);
		Long endDate = cal.getTimeInMillis();
		for (Coupon c : company.getCouponsByDate(endDate)) {
			System.out.println("by type: " + c);
			assertTrue(c.getEndDate() < endDate);
		}
	}
}
