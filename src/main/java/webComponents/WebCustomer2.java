package webComponents;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import main.entities.tables.Customer;

@XmlRootElement
public class WebCustomer2 implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String custName;
	private String password;
	
	public WebCustomer2() {
		super();
	}
	
	public WebCustomer2(int id, String custName, String password) {
		super();
		this.id = id;
		this.custName = custName;
		this.password = password;
	}
	
	public WebCustomer2(Customer cust) {
		super();
		this.id = cust.getId();
		this.custName = cust.getName();
		this.password = cust.getPassword();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCustName() {
		return custName;
	}
	
	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public static WebCustomer2 returnWebCustomer(Customer Customer) {
		return new WebCustomer2(Customer.getId(), Customer.getName(), Customer.getPassword());
	}
	
	public static Customer returnCustomer(WebCustomer2 webCustomer) {
		Customer customer = new Customer(webCustomer.getCustName(), webCustomer.getPassword());
		customer.setId(webCustomer.getId());
		return customer;
	}
}