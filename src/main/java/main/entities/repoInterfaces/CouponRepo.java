package main.entities.repoInterfaces;

import java.util.Set;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import main.entities.tables.Coupon;

public interface CouponRepo extends CrudRepository<Coupon, Integer> {
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Coupon c WHERE c.id = :couponId")
	void removeCoupon(@Param("couponId") int couponId);
	
	@Query("select c from Coupon c where c.id = ?1 AND c.amount > 0")
	Coupon findCouponWithValidAmount(int id);
	
	Coupon findCouponByTitle(String title);
	
	@Query("select c from Coupon c where  0 < c.amount AND ?1 < c.endDate ")
	Set<Coupon> findPurchableCouponByAmountAndEndDate(Long endDate);
	
}
