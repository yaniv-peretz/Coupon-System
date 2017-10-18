package main;

import clientFacade.AdminFacade;
import clientFacade.ClientType;
import clientFacade.CompanyFacade;
import clientFacade.CouponClientFacade;
import clientFacade.CustomerFacade;
import dbConnection.DBconnection;
import sysOperations.DailyCouponExpirationTask;

public class CouponSystem implements CouponClientFacade{
	
	private static final CouponSystem instance = new CouponSystem();
	private static final DailyCouponExpirationTask task = DailyCouponExpirationTask.getInstance();
	private Thread exCoupons;
	
	public CouponSystem() {
		super();
		
		exCoupons = new Thread (task);
		exCoupons.setDaemon(true);
		exCoupons.start();
	}
	
	public CouponSystem getInstance() {
		return instance;
	}
	
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
		
		CouponClientFacade client = null;
		
		if(clientType == ClientType.ADMIN) {
			client = new AdminFacade();
			
		}else if (clientType == ClientType.COMPANY) {
			client = new CompanyFacade();
					
		}else if (clientType == ClientType.CUSTOMER) {
			client = new CustomerFacade();
			
		}
		
		client = client.login(name, password, clientType);
		return client;
	}

	/**
	 * Stop Daemon Thread (DailyCouponExpirationTask) from running. 
	 */
	public void shutDown() {
		//set daemon tread task to inactive
		task.stopTask();
		
		//close all connections
		DBconnection.getInstance().closeAllConnections();
	}

	

}
