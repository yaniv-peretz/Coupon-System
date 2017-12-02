package main.facades;

import java.util.Calendar;
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
import clientFacade.CompanyFacade;
import javaBeans.Coupon;
import javaBeans.CouponType;
import webComponents.WebCoupon;

@RestController
@RequestMapping("/company")
public class Company {
	
	private CompanyFacade getFacade(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		CompanyFacade company = (CompanyFacade) session.getAttribute("client");
		return company;
		
	}
	
	@RequestMapping(value = "coupon/all", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<HashSet<WebCoupon>> getAllCoupons(HttpServletRequest request,
			HttpServletResponse response) {
		
		CompanyFacade company = getFacade(request, response);
		
		HashSet<Coupon> coupons = company.getAllCoupons();
		HashSet<WebCoupon> webCoupons = new HashSet<>();
		for (Coupon c : coupons) {
			webCoupons.add(new WebCoupon(c));
		}
		
//		@formatter:off
		return ResponseEntity.
				status(HttpStatus.OK).
				contentType(MediaType.APPLICATION_JSON).
				body(webCoupons);
//		@formatter:on	
		
	}
	
	@RequestMapping(value = "coupon/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<WebCoupon> getCouponById(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("id") int id) {
		
		CompanyFacade company = getFacade(request, response);
		Coupon coupon = company.getCoupon(new Long(id));
		
		WebCoupon webCoupon = new WebCoupon(coupon);
//		@formatter:off
		return ResponseEntity.
				status(HttpStatus.OK).
				contentType(MediaType.APPLICATION_JSON).
				body(webCoupon);
//		@formatter:on
	}
	
	@RequestMapping(value = "coupon/date/{year}/{month}/{day}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<HashSet<WebCoupon>> getAllCouponsbyDate(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("year") int yyyy,
			@PathVariable("month") int mm,
			@PathVariable("day") int dd) {
		
		CompanyFacade company = getFacade(request, response);
		
		Calendar cal = Calendar.getInstance();
		cal.set(yyyy, mm, dd);
		java.util.Date endDate = cal.getTime();
		
		HashSet<Coupon> coupons = company.getCouponsByDate(endDate);
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
		
		CompanyFacade company = getFacade(request, response);
		
		HashSet<Coupon> coupons = company.getCouponsByPrice(price);
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
	
	@RequestMapping(value = "coupon/type/{type}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<HashSet<WebCoupon>> getAllCouponsbyType(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("type") CouponType type) {
		
		CompanyFacade company = getFacade(request, response);
		
		HashSet<Coupon> coupons = company.getCouponsByType(type);
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
	
	@RequestMapping(value = "coupon", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> postCoupon(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody WebCoupon webcoupon) {
		
		CompanyFacade company = getFacade(request, response);
		
		Coupon coupon = WebCoupon.returnCoupon(webcoupon);
		company.createCoupon(coupon);
//		@formatter:off
		return ResponseEntity.
				status(HttpStatus.OK).
				contentType(MediaType.APPLICATION_JSON).
				body(null);
//		@formatter:on
	}
	
	@RequestMapping(value = "coupon", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Object> putCoupon(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody WebCoupon webcoupon) {
		
		CompanyFacade company = getFacade(request, response);
		
		Coupon coupon = WebCoupon.returnCoupon(webcoupon);
		company.updateCoupon(coupon);
//		@formatter:off
		return ResponseEntity.
				status(HttpStatus.OK).
				contentType(MediaType.APPLICATION_JSON).
				body(null);
//		@formatter:on
	}
	
	@RequestMapping(value = "coupon", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Object> deleteCoupon(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody WebCoupon webcoupon) {
		
		CompanyFacade company = getFacade(request, response);
		
		Coupon coupon = WebCoupon.returnCoupon(webcoupon);
		company.removeCoupon(coupon);
//		@formatter:off
		return ResponseEntity.
				status(HttpStatus.OK).
				contentType(MediaType.APPLICATION_JSON).
				body(null);
//		@formatter:on
	}
	
}
