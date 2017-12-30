package main.facades;

import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import clientFacade.PublicFacade;
import javaBeans.Coupon;
import webComponents.WebCoupon;;

@RestController
@RequestMapping(value = "public")
public class Public {
	
	// #################
	// Admin-Customer API
	// #################
	
	@RequestMapping(value = "coupons", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<HashSet<WebCoupon>> getCustomer(
			HttpServletRequest request,
			HttpServletResponse response) {
		// ensure the system is up and running
		
		try {
			// get all coupons from DB
			PublicFacade publicFacade = new PublicFacade();
			HashSet<Coupon> Coupons = publicFacade.getAllCoupons();
			
			// transform all coupons to webCoupons
			HashSet<WebCoupon> webCoupons = new HashSet<>();
			for (Coupon c : Coupons) {
				webCoupons.add(new WebCoupon(c));
				
			}
//		@formatter:off
			return ResponseEntity.
					status(HttpStatus.OK).
					contentType(MediaType.APPLICATION_JSON).
					body(webCoupons);
//		@formatter:on
			
		} catch (Exception e) {
//			@formatter:off
					return ResponseEntity.
							status(HttpStatus.NO_CONTENT).
							body(null);
//			@formatter:on
			
		}
	}
	
}