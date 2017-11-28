package facade;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import clientFacade.ClientType;
import clientFacade.CustomerFacade;
import javaBeans.Coupon;
import webComponents.WebCoupon;

@RestController
@RequestMapping("/test")

public class test {
	
	private CustomerFacade getFacade() {
		CustomerFacade customer = new CustomerFacade().login("test", "test", ClientType.CUSTOMER);
		return customer;
		
	}
	
	@RequestMapping(value = "coupon/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<WebCoupon> getCustomer(@PathVariable("id") int id) {
		
		CustomerFacade customer = getFacade();
		
		Coupon c = customer.getCoupon((long) id);
		
//		@formatter:off
		return ResponseEntity.
				status(HttpStatus.OK).
				contentType(MediaType.APPLICATION_JSON).
				body(new WebCoupon(c));
//		@formatter:on
	}
	
}
