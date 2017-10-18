package unitTestsDBDAO;

import java.util.Calendar;
import java.util.Date;
import dao.CouponDBDAO;
import javaBeans.Coupon;
import javaBeans.CouponType;

public class UnitTestsCouponDBDAO {
	
	public static void main(String[] args) {
			
		CouponDBDAO copDB = new CouponDBDAO();
		Coupon coupon;
		Date startDate;
		Date endDate; 
		Calendar cal = Calendar.getInstance();
		
		cal.set(1017, 1, 10);
		startDate = cal.getTime();
		
		cal.set(1017, 12, 31);
		endDate = cal.getTime();
		
		coupon = new Coupon(10, "First", startDate, endDate, 1, CouponType.FOOD, "Burger", 10, "burger.gif");
		copDB.createCoupon(coupon);
		
		cal.set(1017, 2, 1);
		startDate = cal.getTime();
		
		cal.set(1017, 10, 11);
		endDate = cal.getTime();
		
		coupon = new Coupon(20, "second", startDate, endDate, 2, CouponType.ELECTRICITY, "Computer", 110, "Computer.gif");
		copDB.createCoupon(coupon);
		
		System.out.println("ALL COUPONS:");
		System.out.println(copDB.getAllCoupons());
		
		System.out.println(copDB.getCouponsByType(CouponType.FOOD));
		System.out.println(copDB.getCouponsByType(CouponType.ELECTRICITY));
		
		
		coupon = new Coupon(20, "updated second", startDate, endDate, 2, CouponType.ELECTRICITY, "Computer", 110, "Computer.gif");
		copDB.updateCoupon(coupon);
		System.out.println(copDB.getAllCoupons());
		
		//Removing Coupons
		System.out.println("\n ### Removing Coupons ### \n");
		System.out.println("--------------------------------\n");
		coupon = new Coupon(10, "First", startDate, endDate, 1, CouponType.FOOD, "Burger", 10, "burger.gif");
		copDB.removeCoupon(coupon);
		coupon = new Coupon(20, "updated second", startDate, endDate, 2, CouponType.ELECTRICITY, "Computer", 110, "Computer.gif");
		copDB.removeCoupon(coupon);
		System.out.println(copDB.getAllCoupons());
	}

}
