package main.entities.repoInterfaces;

import org.springframework.data.repository.CrudRepository;
import main.entities.tables.Customer;

public interface CustomerRepo extends CrudRepository<Customer, Integer> {
	
	Customer findCustomerByNameAndPassword(String name, String password);
	
	Customer findCustomerByName(String compName);
}
