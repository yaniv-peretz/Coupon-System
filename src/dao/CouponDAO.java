package dao;

import java.util.HashSet;

import javaBeans.Coupon;
import javaBeans.CouponType;

public interface CouponDAO {

	/**
	 * Create a new Coupon in DB
	 * id and coupon name are keys
	 * @param coupon
	 */
	public void createCoupon(Coupon coupon);
	
	/**
	 * Remove coupon by DB
	 * @param coupon
	 */
	public void removeCoupon(Coupon coupon);
	
	/**
	 * Update Coupon details by id (only end date and amount)
	 * @param coupon
	 */
	public void updateCoupon(Coupon coupon);
	 
	/**
	 * Get Coupon by id number
	 * @param id
	 * @return
	 */
	public Coupon getCoupon(long id);
	
	/**
	 * get all system coupons.
	 * @return
	 */
	public HashSet<Coupon> getAllCoupons();
	
	/**
	 * Get all coupons by type.
	 * @param couponType
	 * @return
	 */
	public HashSet<Coupon> getCouponsByType(CouponType couponType);

}
