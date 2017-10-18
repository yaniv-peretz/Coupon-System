package dao;

import java.util.HashSet;

import javaBeans.Coupon;

class IdList {
	
	/**
	 * Convert a HashSet of Coupon IDs into a sting of IDs separated with ",'
	 * @param Coupons
	 * @return
	 */
	protected static String convertHashSetToIdList(HashSet<Coupon> Coupons) {
		
		String res = "(-1,-2)";
		
		//set to Object type so the function is expendable to other types (customer, Company etc).
		if(Coupons.isEmpty()) {
			return res;
		}
		
		res = "(";
		for (Coupon coupon : Coupons) {			
				
			String id = String.valueOf(coupon.getId());
			res = res + id + ",";
			
			}
		
		res = res.substring(0, res.length()-1);
		res = res + ")";
		return res;
	}

}
