package main.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import clientFacade.ClientFacadeInterface;
import clientFacade.ClientType;
import clientFacade.CustomerFacade;
import main.CouponSystem;
import webComponents.WebCompany;

@RestController
@RequestMapping("api/login")
public class LoginApi {
	
	/**
	 * Try to authenticate a Customer.
	 * 
	 * @param request
	 * @param response
	 * @return status: OK, true on success. false and error status on failure.
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Boolean> login(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody WebCompany webCompany) {
		
		// get customer user and password parameters.
		String user = webCompany.getCompName();
		String password = webCompany.getPassword();
		ClientType type = ClientType.CUSTOMER;
		
		// try to login, else return specific HTTP-status
		try {
			CouponSystem sys = CouponSystem.getInstance();
			ClientFacadeInterface client = sys.login(user, password, type);
			
			// user & password incorrect - customer not found
			if (client == null) {
//			@formatter:off
				return ResponseEntity.
						status(HttpStatus.NOT_FOUND).
						contentType(MediaType.APPLICATION_JSON).
						body(false);
//			@formatter:on
			}
			
			// Login successful, set session data
			CustomerFacade customerFacade = (CustomerFacade) client;
			HttpSession session = request.getSession();
			session.setAttribute("auth", true);
			session.setAttribute("client", customerFacade);
			
//		@formatter:off
			return ResponseEntity.
					status(HttpStatus.OK).
					contentType(MediaType.APPLICATION_JSON).
					body(true);
//		@formatter:on
			
		} catch (Exception e) {
//			@formatter:off
				return ResponseEntity.
						status(HttpStatus.INTERNAL_SERVER_ERROR).
						contentType(MediaType.APPLICATION_JSON).
						body(false);
//			@formatter:on
		}
		
	}
}
