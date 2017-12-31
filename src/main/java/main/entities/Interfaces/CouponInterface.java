package main.entities.Interfaces;

import org.springframework.data.repository.CrudRepository;
import main.entities.tables.Coupon;

public interface CouponInterface extends CrudRepository<Coupon, Integer> {
	
}
