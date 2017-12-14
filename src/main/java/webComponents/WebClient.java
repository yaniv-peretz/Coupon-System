package webComponents;

import java.io.Serializable;
import javaBeans.Client;

public class WebClient implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String password;
	private String type;
	
	public WebClient() {
		super();
	}
	
	public WebClient(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public static WebClient returnWebClientFromClient(Client client) {
		return new WebClient(client.getId(), client.getName());
	}
	
}
