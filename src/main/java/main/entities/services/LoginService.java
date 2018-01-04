package main.entities.services;

import java.util.Optional;
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
	
	public Optional<Integer> login(String user, String password, ClientType type) {
		
		switch (type) {
		case ADMIN:
			return (user.equals("admin") && password.equals("1234")) ? Optional.of(1) : Optional.empty();
		case COMPANY:
			Company compnay = companyRepo.findCompanyByCompNameAndPassword(user, password);
			if (compnay != null) {
				return Optional.of(compnay.getId());
			} else {
				return Optional.empty();
			}
		case CUSTOMER:
			Customer customer = customerService.findCustomerByNameAndPassword(user, password);
			if (customer != null) {
				return Optional.of(customer.getId());
			} else {
				return Optional.empty();
			}
		default:
			return Optional.empty();
		}
	}
	
}
