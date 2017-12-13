package webComponents;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javaBeans.Customer;

@XmlRootElement
public class WebCustomer extends WebClient implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String custName;
	private String password;
	
	public WebCustomer() {
		super();
	}
	
	public WebCustomer(long id, String custName, String password) {
		super();
		this.id = id;
		this.custName = custName;
		this.password = password;
	}
	
	public WebCustomer(Customer cust) {
		super();
		this.id = cust.getId();
		this.custName = cust.getCustName();
		this.password = cust.getPassword();
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
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
		return new WebCustomer(Customer.getId(), Customer.getCustName(), Customer.getPassword());
	}
	
	public static Customer returnCustomer(WebCustomer webCustomer) {
		return new Customer(webCustomer.getId(), webCustomer.getCustName(), webCustomer.getPassword());
	}
}