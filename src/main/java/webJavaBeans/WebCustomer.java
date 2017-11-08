package webJavaBeans;

import java.io.Serializable;
import javaBeans.Customer;

public class WebCustomer implements Serializable{
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
	
	public Customer getCustomer() {
		return new Customer(id, custName, password);
	}
}
