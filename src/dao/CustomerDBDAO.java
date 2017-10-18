package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;

import dbConnection.DBconnection;
import javaBeans.Coupon;
import javaBeans.Customer;

public class CustomerDBDAO implements CustomerDAO{
	
	private static DBconnection db = DBconnection.getInstance();

	@Override
	public void createCustomer(Customer cust) {
		
		//Prevent storing in DB company with existing id OR name.
		if(CustomerIdOrNameExists(cust)) {
			throw new RecordExistsViolation(cust);
		}
		
		//Capture Customer.
		String sql = "INSERT INTO CUSTOMER VALUES ( ?, ? ,?)";

		Connection con = db.getconnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setLong(1, cust.getId());
			pstmt.setString(2, cust.getCustName());
			pstmt.setString(3, cust.getPassword());
			
			pstmt.executeUpdate();
							
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println(cust);
			System.err.println(e.getMessage());
			
		}finally {
			db.returnConnection(con);	
			
		}
				
		//Does not capture customer coupons (in order to prevent admin to add coupon to new customers)
		
				
	}

	@Override
	public void removeCustomer(Customer cust) {

		//delete company..
		long id = cust.getId();
		String[] sql = new String[2];
		sql[0] = "DELETE FROM CUSTOMER_COUPON WHERE CUST_ID = " + id;
		sql[1] = "DELETE FROM CUSTOMER WHERE ID = " + id;
		
		Connection con = db.getconnection();
		try (Statement stmt = con.createStatement();){
			
			con.setAutoCommit(false);
			
			for (int i = 0; i < sql.length; i++) {
				stmt.executeUpdate(sql[i]);
			}
			
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println(cust);
			System.err.println(e.getMessage());
			
		}finally {	
			db.returnConnection(con);
			
		}
		
	}

	
	@Override
	public void updateCustomer(Customer cust) {
		
		//Prevent updating a Customer if no customer with equal name AND id found.
		if(!CustomerExists(cust)) {
			String message = "Customer id: " + cust.getId() + " Name: " + cust.getCustName() + " record does not exists";
			throw new RuntimeException(message);
		}
		
		//update customer
		long id = cust.getId();
		String cusName = cust.getCustName();
		String sql = "UPDATE CUSTOMER SET "
				+ "PASSWORD = '" + cust.getPassword() + "' "
				+ "WHERE ("
					+ "ID = " + id + " "
					+ "AND CUST_NAME = '" + cusName + "'"
					+ ")";

		Connection con = db.getconnection();
		try (Statement stmt = con.createStatement();){
						
			stmt.executeUpdate(sql);
			
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println(cust);
			System.err.println(e.getMessage());
			
		}finally {
			db.returnConnection(con);
			
		}
		
	}
	
	/**
	 * Add Coupons to Customer's Coupons (does not remove Coupons)
	 * @param cust
	 */
	public void setCoupons(Customer cust) {
		
		Coupon coupon = null;
		
		//Create a list of unique new added coupons.
		long id = cust.getId();
		HashSet<Coupon> coupons = cust.getCoupons();
		HashSet<Coupon> ownedCoupons = this.getCustomer(id).getCoupons();
		coupons.removeAll(ownedCoupons);		
		
		String sql = "INSERT INTO CUSTOMER_COUPON VALUES ( " + id + ", ? )";
		Connection con = db.getconnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);){
			
			Iterator<Coupon> itr = coupons.iterator();								
			while(itr.hasNext()) {
				coupon = itr.next();
				pstmt.setLong(1, coupon.getId());					
				pstmt.executeUpdate();					
				
			}
		
		}catch (SQLException e) {
			System.err.println(sql);
			System.err.println(coupon);
			System.err.println(e.getMessage());
			
		}finally {
			db.returnConnection(con);						
		}
		
	}

	@Override
	public Customer getCustomer(long id) {

		Customer cust = null;
		String sql = "SELECT * FROM CUSTOMER WHERE ID = " + id;

		Connection con = db.getconnection();
		try ( Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);){
			
			if(rs.next()) {
				String custName = rs.getString(2);
				String password = rs.getString(3);
				cust = new Customer(id, custName, password);			
				
			}else {
				String message = "Customer for customer id: " + id + " recored does not exists";
				throw new RuntimeException(message);
			}

		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println(cust);
			System.err.println(e.getMessage());
		}
		
		//bring Customer coupons
		HashSet<Coupon> coupons = new HashSet<>();
		
		sql = "SELECT * FROM COUPON "
				+ "WHERE ID IN ("
					+ "SELECT COUPON_ID FROM CUSTOMER_COUPON "
					+ "WHERE COUPON_ID = " + id + ")";
		
		try ( Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);){
			
			CouponDBDAO couponDB = new CouponDBDAO();
				while(rs.next()) {
					
					Coupon coupon = couponDB.createCouponFromResultSetd(rs);
					coupons.add(coupon);
				}
				cust.setCoupons(coupons);
				
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println(coupons);
			System.err.println(e.getMessage());
			
		}finally {
			db.returnConnection(con);			
		}
		
		return cust;
	}

	@Override
	public HashSet<Customer> getAllCustomers() {

		HashSet<Customer> customers = new HashSet<>();
		String sql = "SELECT * FROM CUSTOMER";
		 
		Connection con = db.getconnection();
		try (Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);){
			
				while(rs.next()) {
					long id = rs.getLong(1);
					String custName = rs.getString(2);
					String password = rs.getString(3);
					Customer cust = new Customer(id, custName, password);
					
					customers.add(cust);
				}
				
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println(customers);
			System.err.println(e.getMessage());
			
		}finally {
			db.returnConnection(con);			
			
		}
		
		return customers;
	}

	@Override
	public HashSet<Coupon> getCoupons() {
		
		HashSet<Coupon> coupons = new HashSet<>();
		String sql 	= "SELECT * FROM COUPON "
				+ "WHERE ID IN ("
					+ "SELECT DISTINCT COUPON_ID FROM CUST_COUPON"
					+ " )";
		
		Connection con = db.getconnection();
		try ( Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);){
			
			CouponDBDAO coupDBDAO = new CouponDBDAO();
			while(rs.next()) {
				Coupon coupon = coupDBDAO.createCouponFromResultSetd(rs);
				coupons.add(coupon);					
			}
				
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println(e.getMessage());
			
		} finally {
			db.returnConnection(con);
			
		}

		return coupons;
	}

	@Override
	public boolean login(String custName, String password) {

		boolean login = false;
		String sql = "SELECT ID FROM CUSTOMER "
				+ "WHERE ("
					+ "CUST_NAME = '" + custName + "' "
					+ "AND PASSWORD = '" + password + "'"
							+ ")";
		
		Connection con = db.getconnection();
		try (Statement stmt = con.createStatement();
				ResultSet res = stmt.executeQuery(sql);){

			boolean exist = res.next(); 
			if(exist){
				login = true;
			}			
			
		}catch (SQLException e) {
			System.err.println(sql);
			System.err.println(e.getMessage());
			
		}finally {
			db.returnConnection(con);		
			
		}
		
		return login;
	}
	
	private boolean CustomerIdOrNameExists(Customer cust){
		long id = cust.getId(); 
		String custName = cust.getCustName();
		boolean exists = false;
		String sql 	= "SELECT * FROM CUSTOMER "
					+ "WHERE ("
						+ "id = " + id + " "
						+ "OR CUST_NAME = '" + custName + "'"
								+ ")";
		
		Connection con = db.getconnection();
		try ( Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);){
			
				if(rs.next()) {
					exists = true;
					
				}

		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println(e.getMessage());
			
		}finally {
			db.returnConnection(con);
		}
				
		return exists;
	}
	
private boolean CustomerExists(Customer cust){
		
	long id = cust.getId();
	String custName = cust.getCustName();
		
	boolean exists = false;
	String sql 	= "SELECT * FROM CUSTOMER "
				+ "WHERE ("
				+ "id = " + id + " "
				+ "OR CUST_NAME = '" + custName+ "'"
						+ ")";
	
		Connection con = db.getconnection();
		try ( Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);){
			
			if(rs.next()) {
				exists = true;
					
			}

		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println(e.getMessage());
			
		}finally {
			db.returnConnection(con);
		}
				
		return exists;
	}

public Customer getLoginCustomer(String custName, String password) {

	Customer cust = null;
	long id = -1L;
	String sql 	= "SELECT id FROM CUSTOMER "
				+ "WHERE ("
					+ "CUST_NAME = '" + custName + "'"
					+ "AND PASSWORD = '" + password + "'"
							+ ")";

	Connection con = db.getconnection();
	try ( Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);){
		
			if(rs.next()) {
				id = rs.getLong(1);
			}							

	} catch (SQLException e) {
		System.err.println(sql);
		System.err.println(cust);
		System.err.println(e.getMessage());
		
	}finally {
		db.returnConnection(con);
		
	}
	
	if(0 < id) {
		cust = this.getCustomer(id);
		
	}
	
	return cust;
}

public void addCouponToCustmer(long cust_id, long coup_id ) {
	
	String sql =	"INSERT INTO CUSTOMER_COUPON VALUES ( ?, ?)" ;
			
	Connection con = db.getconnection();		
	try (PreparedStatement pstmt = con.prepareStatement(sql);){
		
		pstmt.setLong(1, cust_id);
		pstmt.setLong(2, coup_id);
		pstmt.executeUpdate();
		
	}catch (Exception e) {
		System.err.println(sql);
		System.err.println(e.getMessage());
		
	} finally {
		db.returnConnection(con);
		
	}
	
}

}
