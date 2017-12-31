package main;

import java.util.Calendar;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import main.entities.Interfaces.CompanyInterface;
import main.entities.Interfaces.CouponInterface;
import main.entities.Interfaces.CustomerInterface;
import main.entities.tables.Company;
import main.entities.tables.Coupon;
import main.entities.tables.Customer;
import main.entities.tables.enums.CouponType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponSystemServerApplicationTests {
	
	@Autowired
	CustomerInterface customerInterface;
	@Autowired
	CompanyInterface companyInterface;
	@Autowired
	CouponInterface couponInterface;
	
	@Before
	public void testDummy() {
		System.out.println("### test started ###");
	}
	
	@Test
	public void customerCrudTest() {
		Customer customer = new Customer("crud-test", "crud-test");
		customerInterface.save(customer);
		System.out.println(customerInterface.findOne(customer.getId()));
		customer.setName("changed");
		customer.setPassword("changed");
		customerInterface.save(customer);
		System.out.println(customerInterface.findOne(customer.getId()));
		customerInterface.delete(customer.getId());
	}
	
	@Test
	public void companyCrudTest() {
		Company company = new Company("crud-test", "crud-test", "crud-test");
		companyInterface.save(company);
		System.out.println(companyInterface.findOne(company.getId()));
		company.setCompName("changed");
		company.setPassword("changed");
		company.setEmail("changed");
		companyInterface.save(company);
		System.out.println(companyInterface.findOne(company.getId()));
		companyInterface.delete(company.getId());
	}
	
	@Test
	public void couponCrudTest() {
		Company company = new Company("crud-test", "crud-test", "crud-test");
		companyInterface.save(company);
		long startDate = new Date().getTime();
		long endDate = new Date().getTime();
		
		Coupon coupon = new Coupon(company, "crud-test", 999, startDate, endDate, CouponType.FOOD, "crud-test", 999,
				"crud-test");
		couponInterface.save(coupon);
		System.out.println(couponInterface.findOne(coupon.getId()));
		
		coupon.setTitle("changed");
		coupon.setAmount(1111);
		Calendar cal = Calendar.getInstance();
		cal.set(2099, 12, 31);
		startDate = cal.getTimeInMillis();
		endDate = cal.getTimeInMillis();
		coupon.setStartDate(startDate);
		coupon.setStartDate(endDate);
		coupon.setType(CouponType.OTHER);
		coupon.setAmount(1111);
		coupon.setMessage("changed");
		coupon.setImage("changed");
		couponInterface.save(coupon);
		System.out.println(couponInterface.findOne(coupon.getId()));
		couponInterface.delete(coupon.getId());
		
		companyInterface.delete(company.getId());
	}
	
}
