package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import clientFacade.AdminFacade;
import clientFacade.ClientType;
import javaBeans.Company;
import main.CouponSystem;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponSystemServerApplicationTests {
	static AdminFacade admin;
	static AdminFacade wrongAdmin;
	static Company comp = new Company(1, "Junit-test", "Junit-test", "Junit-test");
	
	@BeforeClass
	public static void setAdminFacade() {
		CouponSystem sys = CouponSystem.getInstance();
		admin = (AdminFacade) sys.login("admin", "1234", ClientType.ADMIN);
	}
	
	@BeforeClass
	public static void setWrongAdminFacade() {
		CouponSystem sys = CouponSystem.getInstance();
		wrongAdmin = (AdminFacade) sys.login("admin", "1111", ClientType.ADMIN);
	}
	
	@Test
	public void adminLogin() {
		assertNotNull(admin);
	}
	
	@Test
	public void adminWorngLogin() {
		assertNull(wrongAdmin);
	}
	
	@Test
	public void adminCreateCompany() {
		long id = admin.CreateCompany(comp);
		comp.setId(id);
		comp.setEmail("Junit-change");
		admin.updateCompany(comp);
		admin.removeCompany(comp);
		
		System.out.println(comp + " passed!");
	}
	
}
