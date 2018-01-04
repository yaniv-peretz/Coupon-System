package main.restControllers;

import java.util.Calendar;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import main.entities.repoInterfaces.CouponRepo;
import main.entities.tables.Coupon;
import webComponents.WebCoupon;

@RestController
@RequestMapping(value = "public")
public class PublicApi {
	
	@Autowired
	CouponRepo couponRepo;
	
	@RequestMapping(value = "coupons", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getCustomer(
			HttpServletRequest request, HttpServletResponse response) {
		
		// transform all coupons to webCoupons
		HashSet<WebCoupon> webCoupons = new HashSet<>();
		for (Coupon c : couponRepo.findPurchableCouponByAmountAndEndDate(Calendar.getInstance().getTimeInMillis())) {
			webCoupons.add(new WebCoupon(c));
		}
		
		if (0 < webCoupons.size()) {
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCoupons);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		
	}
	
}
