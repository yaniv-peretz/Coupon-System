package main.entities.repoInterfaces;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import main.entities.tables.Coupon;

public interface CouponRepo extends CrudRepository<Coupon, Integer> {
	
	@Query("select c from Coupon c where c.id = ?1 AND c.amount > 0")
	Coupon findCouponWithValidAmount(int id);
}
