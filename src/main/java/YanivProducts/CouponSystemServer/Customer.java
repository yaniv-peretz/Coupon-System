package YanivProducts.CouponSystemServer;

import java.util.HashSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import clientFacade.ClientType;
import clientFacade.CustomerFacade;
import javaBeans.Coupon;
import javaBeans.CouponType;
import webJavaBeans.WebCoupon;

@Path("/customer")
public class Customer {

	private CustomerFacade getFacade() {
		CustomerFacade customer = new CustomerFacade().login("cut-api-test", "cut-api-test", ClientType.CUSTOMER);
		return customer;

	}

	@POST
	@Path("/coupon")
	@Consumes("application/json")
	public void purchaseCoupons(WebCoupon webcoupon) {

		CustomerFacade customer = getFacade();

		Coupon coupon = webcoupon.getCoupon();
		customer.purchaseCoupon(coupon);

	}

	@GET
	@Path("/coupon/")
	@Produces("application/json")
	public HashSet<WebCoupon> getCouponsByType() {

		CustomerFacade customer = getFacade();

		HashSet<Coupon> coupons = customer.allPurchasedCoupons();
		HashSet<WebCoupon> webCoupons = new HashSet<>();
		for (Coupon coupon : coupons) {
			webCoupons.add(new WebCoupon(coupon));
		}

		return webCoupons;

	}

	@GET
	@Path("/coupon/type/{type}")
	@Produces("application/json")
	public HashSet<WebCoupon> getCouponsByType(@PathParam("type") CouponType type) {

		CustomerFacade customer = getFacade();

		HashSet<Coupon> coupons = customer.allPurchasedCouponsByType(type);
		HashSet<WebCoupon> webCoupons = new HashSet<>();
		for (Coupon coupon : coupons) {
			webCoupons.add(new WebCoupon(coupon));
		}

		return webCoupons;

	}

	@GET
	@Path("/coupon/price/{price}")
	@Produces("application/json")
	public HashSet<WebCoupon> getAllCouponsbyPrice(@PathParam("price") double price) {

		CustomerFacade customer = getFacade();

		HashSet<Coupon> coupons = customer.allPurchasedCouponsByPrice(price);
		HashSet<WebCoupon> webCoupons = new HashSet<>();
		for (Coupon coupon : coupons) {
			webCoupons.add(new WebCoupon(coupon));
		}

		return webCoupons;

	}

}
