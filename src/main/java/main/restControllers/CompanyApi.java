package main.restControllers;

import java.util.HashSet;
import java.util.Set;
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
	public @ResponseBody ResponseEntity<?> postCoupon(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody WebCoupon webcoupon) {
		
		Company company = getFacade(request, response);
		if (company == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		// save the coupon
		Coupon coupon = WebCoupon.returnCoupon(webcoupon);
		int newId = companyService.addNewCoupon(company, coupon);
		
		if (0 < newId) {
			coupon.setId(newId);
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
					.body(new WebCoupon(coupon));
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("coupon already name exists");
		}
		
	}
	
	@RequestMapping(value = "coupon", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> putCoupon(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody WebCoupon webcoupon) {
		
		Company company = getFacade(request, response);
		if (company == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		// update the coupon
		if (companyService.getCouponById(company, webcoupon.getId()) != null) {
			Coupon coupon = WebCoupon.returnCoupon(webcoupon);
			coupon.setId(webcoupon.getId());
			companyService.updateExistingCoupon(company, coupon);
			return ResponseEntity.status(HttpStatus.OK).body("");
			
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("company doesn't own the coupon!");
		}
		
	}
	
	@RequestMapping(value = "coupon/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> deleteCoupon(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("id") int id) {
		
		Company company = getFacade(request, response);
		if (company == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		if (companyService.deleteCoupon(company, id)) {
			return ResponseEntity.status(HttpStatus.OK).body("");
			
		} else {
			String msg = "coupon " + id + " not found for company" + company.getId();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
		}
		
	}
	
	/* @formatter:off
	 * ####################### 
	 * 	finding coupons 
	 * #######################
	 	@formatter:on		*/
	
	@RequestMapping(value = "coupon/all", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getAllCoupons(HttpServletRequest request,
			HttpServletResponse response) {
		
		Company company = getFacade(request, response);
		if (company == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
		}
		
		Set<Coupon> coupons = companyService.getCoupons(company);
		Set<WebCoupon> webCoupons = new HashSet<>();
		for (Coupon coupon : coupons) {
			webCoupons.add(new WebCoupon(coupon));
		}
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCoupons);
	}
	
	@RequestMapping(value = "coupon/price/{price}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getAllCouponsbyPrice(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("price") double price) {
		
		Company company = getFacade(request, response);
		if (company == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
		}
		
		Set<Coupon> coupons = companyService.getCouponsByPrice(company, price);
		Set<WebCoupon> webCoupons = new HashSet<>();
		for (Coupon coupon : coupons) {
			webCoupons.add(new WebCoupon(coupon));
		}
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCoupons);
	}
	
	@RequestMapping(value = "coupon/date/{date}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getAllCouponsbyDate(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("date") long date) {
		
		Company company = getFacade(request, response);
		if (company == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		Set<Coupon> coupons = companyService.getCouponsByDate(company, date);
		Set<WebCoupon> webCoupons = new HashSet<>();
		for (Coupon coupon : coupons) {
			webCoupons.add(new WebCoupon(coupon));
		}
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCoupons);
	}
	
	//
	//
	@RequestMapping(value = "coupon/type/{type}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getAllCouponsbyType(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("type") CouponType type) {
		
		Company company = getFacade(request, response);
		if (company == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		Set<Coupon> coupons = companyService.getCouponsByType(company, type);
		Set<WebCoupon> webCoupons = new HashSet<>();
		for (Coupon coupon : coupons) {
			webCoupons.add(new WebCoupon(coupon));
		}
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(webCoupons);
	}
}
