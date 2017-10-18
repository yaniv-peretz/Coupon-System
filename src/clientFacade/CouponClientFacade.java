package clientFacade;

public interface CouponClientFacade {

	/**
	 * Login interface
	 * @param name Client name
	 * @param password Client password
	 * @param clientType Client type admin, Company, Customer 
	 * @return
	 * @throws RuntimeException
	 */
	public CouponClientFacade login(String name, String password, ClientType clientType) throws RuntimeException;
}
