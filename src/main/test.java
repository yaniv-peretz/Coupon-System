package main;

import java.sql.SQLException;
import clientFacade.AdminFacade;
import clientFacade.ClientType;
import javaBeans.Customer;

public class test {
	public static void main(String[] args) throws SQLException {
		
		CouponSystem c = new CouponSystem();
		AdminFacade admin = (AdminFacade) c.login("admin", "1234", ClientType.ADMIN);
		
		admin.removeCustomer(new Customer(10L,"s","s"));
	}
}
