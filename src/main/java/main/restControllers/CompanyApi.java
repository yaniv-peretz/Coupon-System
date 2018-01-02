package main.restControllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import main.entities.repoInterfaces.CouponRepo;
import main.entities.services.CompanyService;
import main.entities.tables.Company;
import main.entities.tables.Coupon;
import main.entities.tables.enums.CouponType;
import webComponents.WebCoupon;

@RestController
@RequestMapping(value = "company")
public class CompanyApi {
	
	@Autowired
	CompanyService companyService;
	@Autowired
	CouponRepo couponRepo;
	
	private Company getFacade(HttpServletRequest request, HttpServletResponse response) {
		HttpSession httpSession = request.getSession();
		
		// Get the company from the session
		int comp_id = (int) httpSession.getAttribute("id");
		return companyService.findById(comp_id);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param id
	 *            coupon id
	 * @return Coupon object or null if not found for the specific company.
	 */
	@RequestMapping(value = "coupon/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<WebCoupon> getCouponById(
			HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") int id) {
		
		Company company = getFacade(request, response);
		if (company == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		Coupon coupon = companyService.getCouponById(company, id);
		if (coupon != null) {
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
					.body(new WebCoupon(coupon));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param webcoupon
	 * @return create a new coupon for the company
	 */
	@RequestMapping(value = "coupon", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> postCoupon(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody WebCoupon webcoupon) {
		
		Company company = getFacade(request, response);
		if (company == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		// save the coupon
		Coupon coupon = WebCoupon.returnCoupon(webcoupon);
		coupon.setCompany(company);
		coupon.setId(0);
		couponRepo.save(coupon);
		
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
				.body(new WebCoupon(coupon));
	}
	
	@RequestMapping(value = "coupon", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Object> putCoupon(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody WebCoupon webcoupon) {
		
		Company company = getFacade(request, response);
		if (company == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		// save the coupon
		Coupon coupon = WebCoupon.returnCoupon(webcoupon);
		coupon.setCompany(company);
		coupon.setId(webcoupon.getId());
		try {
			couponRepo.save(coupon);
			return ResponseEntity.status(HttpStatus.OK).body("");
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			
		}
		
	}
	
	@RequestMapping(value = "coupon/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Object> deleteCoupon(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("id") int id) {
		
		Company company = getFacade(request, response);
		if (company == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		if (couponRepo.findOne(id) == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("coupon not found");
		}
		// delete the coupon
		// TODO must check if the coupon is owned by the deleting company!
		try {
			couponRepo.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body("");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			
		}
		
	}
	
	/* @formatter:off
	 * ####################### 
	 * 	finding coupons 
	 * #######################
	 	@formatter:on		*/
	
	@RequestMapping(value = "coupon/all", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<WebCoupon>> getAllCoupons(HttpServletRequest request,
			HttpServletResponse response) {
		
		Company company = getFacade(request, response);
		if (company == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		List<Coupon> coupons = company.getCoupons();
		List<WebCoupon> webCoupons = new ArrayList<>();
		for (Coupon coupon : coupons) {
			webCoupons.add(new WebCoupon(coupon));
		}
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCoupons);
	}
	
	@RequestMapping(value = "coupon/price/{price}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<WebCoupon>> getAllCouponsbyPrice(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("price") double price) {
		
		Company company = getFacade(request, response);
		if (company == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		List<Coupon> coupons = companyService.getCouponsByPrice(company, price);
		List<WebCoupon> webCoupons = new ArrayList<>();
		for (Coupon coupon : coupons) {
			webCoupons.add(new WebCoupon(coupon));
		}
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCoupons);
	}
	
	@RequestMapping(value = "coupon/date/{year}/{month}/{day}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<WebCoupon>> getAllCouponsbyDate(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("year") int yyyy,
			@PathVariable("month") int mm,
			@PathVariable("day") int dd) {
		
		Company company = getFacade(request, response);
		if (company == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		Calendar cal = Calendar.getInstance();
		cal.set(yyyy, mm, dd);
		
		List<Coupon> coupons = companyService.getCouponsByDate(company, cal.getTimeInMillis());
		List<WebCoupon> webCoupons = new ArrayList<>();
		for (Coupon coupon : coupons) {
			webCoupons.add(new WebCoupon(coupon));
		}
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCoupons);
	}
	
	//
	//
	@RequestMapping(value = "coupon/type/{type}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<WebCoupon>> getAllCouponsbyType(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("type") CouponType type) {
		
		Company company = getFacade(request, response);
		if (company == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		List<Coupon> coupons = companyService.getCouponsByType(company, type);
		List<WebCoupon> webCoupons = new ArrayList<>();
		for (Coupon coupon : coupons) {
			webCoupons.add(new WebCoupon(coupon));
		}
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCoupons);
	}
}
