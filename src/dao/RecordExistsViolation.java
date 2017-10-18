package dao;

import javaBeans.Company;
import javaBeans.Coupon;
import javaBeans.Customer;

/**
 * Create a new runtime exception with proper details for existing id & name combination.
 * typically used to prevent inserting a new record to DB that may cause key duplication.   
 * @author yaniv
 *
 */
public class RecordExistsViolation extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	RecordExistsViolation(Object o){
		
		long id = -1;
		String name = "";
		String className = "";
		
		if (o instanceof Company) {
			Company c = (Company) o;
			id = c.getId(); 
			name = c.getCompName();
			className = "Company";
			
		}else if (o instanceof Coupon) {
			Coupon c = (Coupon) o;
			id = c.getId(); 
			name = c.getTitle();
			className = "Coupon";
			
		}else if (o instanceof Customer) {
			Customer c = (Customer) o;
			id = c.getId(); 
			name = c.getCustName();
			className = "Customer";
			
		}
		
		System.err.println(className + ": id: " + id + " name: " + name + " record already exists");
		printStackTrace();
	}
}
