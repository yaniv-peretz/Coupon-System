package sysOperations;

import java.util.concurrent.TimeUnit;

import dao.CouponDBDAO;

public class DailyCouponExpirationTask implements Runnable {
	
	private static final DailyCouponExpirationTask instance = new DailyCouponExpirationTask();
	private static final CouponDBDAO coupDAO = new CouponDBDAO();
	private static boolean quit = false; 

	private DailyCouponExpirationTask() {
		super();
	}
	
	/**
	 * Singleton, get Instance
	 * @return
	 */
	public static DailyCouponExpirationTask getInstance() {
		return instance;
	}
	
	/**
	 * Activate daily deleteExpiredCoupons task.
	 * make sure to activate in a new Daemon thread.
	 */
	@Override
	public void run() {
		
		while(!quit) {
			
			coupDAO.deleteExpiredCoupons();
			
			try {
				TimeUnit.DAYS.sleep(1L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			};
			
		}

	}
	
	public void stopTask() {
		quit = true;
	}

}
