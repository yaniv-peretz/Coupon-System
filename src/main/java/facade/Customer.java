package facade;

import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import clientFacade.CustomerFacade;
import javaBeans.Coupon;
import javaBeans.CouponType;
import webComponents.WebCoupon;

@RestController
@RequestMapping("/customer")
public class Customer {
	
	private CustomerFacade getFacade(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		CustomerFacade customer = (CustomerFacade) session.getAttribute("client");
		return customer;
		
	}
	
	@RequestMapping(value = "coupon/all", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<HashSet<WebCoupon>> getCouponsByType(
			HttpServletRequest request, HttpServletResponse response) {
		
		CustomerFacade customer = getFacade(request, response);
		
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
	public @ResponseBody ResponseEntity<WebCoupon> getCouponById(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("id") int id) {
		
		CustomerFacade company = getFacade(request, response);
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
	public @ResponseBody ResponseEntity<Object> purchaseCoupons(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody WebCoupon webcoupon) {
		
		CustomerFacade customer = getFacade(request, response);
		
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
	public @ResponseBody ResponseEntity<HashSet<WebCoupon>> getCouponsByType(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("type") CouponType type) {
		
		CustomerFacade customer = getFacade(request, response);
		
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
	public @ResponseBody ResponseEntity<HashSet<WebCoupon>> getAllCouponsbyPrice(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("price") double price) {
		
		CustomerFacade customer = getFacade(request, response);
		
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