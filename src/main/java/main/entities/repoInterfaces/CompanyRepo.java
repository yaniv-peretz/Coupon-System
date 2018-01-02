package main.entities.repoInterfaces;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import main.entities.tables.Company;
import main.entities.tables.Coupon;
import main.entities.tables.enums.CouponType;

public interface CompanyRepo extends CrudRepository<Company, Integer> {
	
	Company findCompanyByCompNameAndPassword(String compName, String password);
	
	Company findCompanuByCompName(String compName);
	
	@Query("select c from Coupon c where c.id < ?1 AND c.company = ?2")
	Coupon findCompanyCouponById(int id, Company company);
	
	@Query("select c from Coupon c where c.price < ?1 AND c.company = ?2")
	List<Coupon> findCompanyCouponsByPrice(double price, Company company);
	
	@Query("select c from Coupon c where c.type < ?1 AND c.company = ?2")
	List<Coupon> findCompanyCouponsByType(CouponType type, Company company);
	
	@Query("select c from Coupon c where c.endDate < ?1 AND c.company = ?2")
	List<Coupon> findCompanyCouponsByDate(long endDate, Company company);
	
}
