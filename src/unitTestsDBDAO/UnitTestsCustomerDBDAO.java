package unitTestsDBDAO;

import java.util.HashSet;
import java.util.Iterator;
import dao.CustomerDBDAO;
import javaBeans.Customer;

public class UnitTestsCustomerDBDAO {

	public static void main(String[] args) {

		CustomerDBDAO db = new CustomerDBDAO();
		
		System.out.println("Starting Tests");
		System.out.println("----------------------");
		
		long id = 11L;
		Customer cust = new Customer(id, "The second test Customer", "password");
		System.out.println("created:" + cust);
		db.createCustomer(cust);
		System.out.println("sent to DB:" + cust);
				
		boolean login;
		login = db.login("The test Customer", "password");
		System.out.println("login success: " + login);
		
		login = db.login("The test Company", "Worngpassword");
		System.out.println("failed login (requires false): " + login);
		
		Customer getCustomer = db.getCustomer(id);
		System.out.println("get from DB:" + getCustomer);
		
		cust.setPassword("Safe1234");
		db.updateCustomer(cust);
		System.out.println("updated DB:" + cust);
		
		getCustomer = db.getCustomer(id);
		System.out.println("get from DB:" + getCustomer);
		
		db.removeCustomer(cust);
		System.out.println("removed from DB:" + cust);
		
		System.out.println("\nUploading new Customers");
		System.out.println("--------------------------");
		cust = new Customer(20L, "test20", "password200");
		db.createCustomer(cust);
		cust = new Customer(21L, "test21", "password201");
		db.createCustomer(cust);
		cust = new Customer(22L, "test22", "password202");
		db.createCustomer(cust);
		
		//TODO: get coupons##################################

		System.out.println("\nprinting all 200+ companies");
		System.out.println("--------------------------");
		HashSet<Customer> allCustomers = db.getAllCustomers();
		Iterator<Customer> itr = allCustomers.iterator();
		while(itr.hasNext()) {
			System.out.println(itr.next());
		}
		
		System.out.println("\nprinting companies after 200+ removals");
		System.out.println("--------------------------");
		cust = new Customer(20L, "test20", "password200");
		db.removeCustomer(cust);
		cust = new Customer(21L, "test20", "password200");
		db.removeCustomer(cust);
		cust = new Customer(22L, "test20", "password200");
		db.removeCustomer(cust);
		
		System.out.println("");
		System.out.println("all customers");
		System.out.println("-------------");
		allCustomers = db.getAllCustomers();
		itr = allCustomers.iterator();
		while(itr.hasNext()) {
			System.out.println(itr.next());
		}		
	}
}
