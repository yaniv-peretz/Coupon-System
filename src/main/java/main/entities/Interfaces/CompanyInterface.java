package main.entities.Interfaces;

import org.springframework.data.repository.CrudRepository;
import main.entities.tables.Company;

public interface CompanyInterface extends CrudRepository<Company, Integer> {
	
}
