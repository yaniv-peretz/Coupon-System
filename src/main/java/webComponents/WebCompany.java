package webComponents;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javaBeans.Company;

@XmlRootElement
public class WebCompany extends WebClient implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String compName;
	private String password;
	private String email;
	
	public WebCompany() {
		super();
	}
	
	public WebCompany(long id, String compName, String password, String email) {
		super();
		this.id = id;
		this.compName = compName;
		this.password = password;
		this.email = email;
	}
	
	public WebCompany(Company comp) {
		super();
		this.id = comp.getId();
		this.compName = comp.getCompName();
		this.password = comp.getPassword();
		this.email = comp.getEmail();
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getCompName() {
		return compName;
	}
	
	public void setCompName(String compName) {
		this.compName = compName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public static Company retutnCompany(WebCompany webCompany) {
		return new Company(webCompany.getId(), webCompany.getCompName(), webCompany.getPassword(),
				webCompany.getEmail());
		
	}
	
	public static WebCompany retutnWebCompany(Company Company) {
		return new WebCompany(Company.getId(), Company.getCompName(), Company.getPassword(),
				Company.getEmail());
		
	}
}