package webComponents;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import main.entities.tables.Customer;

@XmlRootElement
public class WebCustomer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String custName;
	private String password;
	
	public WebCustomer() {
		super();
	}
	
	public WebCustomer(int id, String custName, String password) {
		super();
		this.id = id;
		this.custName = custName;
		this.password = password;
	}
	
	public WebCustomer(Customer cust) {
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
	
	public static WebCustomer returnWebCustomer(Customer Customer) {
		return new WebCustomer(Customer.getId(), Customer.getName(), Customer.getPassword());
	}
	
	public static Customer returnCustomer(WebCustomer webCustomer) {
		Customer customer = new Customer(webCustomer.getCustName(), webCustomer.getPassword());
		customer.setId(webCustomer.getId());
		return customer;
	}
	
}