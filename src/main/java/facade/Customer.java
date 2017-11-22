package facade;

import java.util.HashSet;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import clientFacade.ClientType;
import clientFacade.CustomerFacade;
import javaBeans.Coupon;
import javaBeans.CouponType;
import webComponents.WebCoupon;

@RestController
@RequestMapping("/customer")
public class Customer {
	
	private CustomerFacade getFacade() {
		CustomerFacade customer = new CustomerFacade().login("test", "test", ClientType.CUSTOMER);
		return customer;
		
	}
	
	@RequestMapping(value = "coupon/all", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<HashSet<WebCoupon>> getCouponsByType() {
		
		CustomerFacade customer = getFacade();
		
		HashSet<Coupon> coupons = customer.allPurchasedCoupons();
		HashSet<WebCoupon> webCoupons = new HashSet<>();
		for (Coupon coupon : coupons) {
			webCoupons.add(new WebCoupon(coupon));
		}
		
//		@formatter:off
		return ResponseEntity.
				status(HttpStatus.OK).
				contentType(MediaType.APPLICATION_JSON).
				body(webCoupons);
//		@formatter:on		
	}
	
	@RequestMapping(value = "coupon/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<WebCoupon> getCouponById(@PathVariable("id") int id) {
		
		CustomerFacade company = getFacade();
		Coupon coupon = company.getCoupon(new Long(id));
		
		WebCoupon webCoupon = new WebCoupon(coupon);
		
//		@formatter:off
		return ResponseEntity.
				status(HttpStatus.OK).
				contentType(MediaType.APPLICATION_JSON).
				body(webCoupon);
//		@formatter:on
		
	}
	
	@RequestMapping(value = "coupon", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> purchaseCoupons(@RequestBody WebCoupon webcoupon) {
		
		CustomerFacade customer = getFacade();
		
		Coupon coupon = WebCoupon.returnCoupon(webcoupon);
		customer.purchaseCoupon(coupon);
//		@formatter:off
		return ResponseEntity.
				status(HttpStatus.OK).
				contentType(MediaType.APPLICATION_JSON).
				body(null);
//		@formatter:on
	}
	
	@RequestMapping(value = "coupon/type/{type}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<HashSet<WebCoupon>> getCouponsByType(@PathVariable("type") CouponType type) {
		
		CustomerFacade customer = getFacade();
		
		HashSet<Coupon> coupons = customer.allPurchasedCouponsByType(type);
		HashSet<WebCoupon> webCoupons = new HashSet<>();
		for (Coupon coupon : coupons) {
			webCoupons.add(new WebCoupon(coupon));
		}
		
//		@formatter:off
		return ResponseEntity.
				status(HttpStatus.OK).
				contentType(MediaType.APPLICATION_JSON).
				body(webCoupons);
//		@formatter:on		
	}
	
	@RequestMapping(value = "coupon/price/{price}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<HashSet<WebCoupon>> getAllCouponsbyPrice(@PathVariable("price") double price) {
		
		CustomerFacade customer = getFacade();
		
		HashSet<Coupon> coupons = customer.allPurchasedCouponsByPrice(price);
		HashSet<WebCoupon> webCoupons = new HashSet<>();
		for (Coupon coupon : coupons) {
			webCoupons.add(new WebCoupon(coupon));
		}
		
//		@formatter:off
		return ResponseEntity.
				status(HttpStatus.OK).
				contentType(MediaType.APPLICATION_JSON).
				body(webCoupons);
//		@formatter:on	
	}
	
}