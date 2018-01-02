package main.entities.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.entities.repoInterfaces.CompanyRepo;
import main.entities.tables.Company;
import main.entities.tables.Customer;
import main.login.ClientType;

@Service
public class LoginService {
	
	@Autowired
	CompanyRepo companyRepo;
	
	@Autowired
	CustomerService customerService;
	
	public int login(String user, String password, ClientType type) {
		
		switch (type) {
		case ADMIN:
			return (user.equals("admin") && password.equals("1234")) ? 1 : 0;
		case COMPANY:
			Company compnay = companyRepo.findCompanyByCompNameAndPassword(user, password);
			return compnay.getId();
		case CUSTOMER:
			Customer customer = customerService.findCustomerByNameAndPassword(user, password);
			return customer.getId();
		default:
			return 0;
		}
	}
	
}
