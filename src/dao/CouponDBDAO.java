package dao;

import dbConnection.DBconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashSet;
import javaBeans.Coupon;
import javaBeans.CouponType;

public class CouponDBDAO implements CouponDAO {
	
	private static DBconnection db = DBconnection.getInstance();

	@Override
	public void createCoupon(Coupon coupon) {
		
		if(couponIdOrNameExists(coupon) ){
			throw new RecordExistsException(coupon);
			
		}

		//Capture the coupon.
		String sql = "INSERT INTO COUPON VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";
		
		Connection con = db.getconnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setLong	(1, coupon.getId());
			pstmt.setString	(2, coupon.getTitle());
			pstmt.setDate	(3, new java.sql.Date(coupon.getStartDate().getTime()));
			pstmt.setDate	(4, new java.sql.Date(coupon.getEndDate().getTime()));
			pstmt.setInt	(5, coupon.getAmount());
			pstmt.setString	(6, coupon.getType().toString());
			pstmt.setString	(7, coupon.getMessage());
			pstmt.setDouble	(8, coupon.getPrice());
			pstmt.setString	(9, coupon.getImage());
			pstmt.executeUpdate();
			//	System.out.println("Inserted to DB[ " + coupon + " ]");
						
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println(coupon);
			System.err.println(e.getMessage());
			
		}finally {
			db.returnConnection(con);
			
		}		
	}
	
	public Coupon createCouponFromResultSetd (ResultSet rs) throws SQLException{
			
		long id = rs.getLong(1);
		String title = rs.getString(2);
		Date startDate = rs.getDate(3);
		Date endDate = rs.getDate(4);
		int amount = rs.getInt(5);
		CouponType type = CouponType.valueOf(rs.getString(6));
		String message	= rs.getString(7);
		double price = rs.getDouble(8);
		String image = rs.getString(9);
		Coupon coupon = new Coupon(id, title, startDate, endDate, amount, type, message, price, image);
		
		return coupon;
	}

	@Override
	public void removeCoupon(Coupon coupon) {
				
		//delete coupon...
		long id = coupon.getId();
		String[] sql = new String[3];
		sql[0] = "DELETE FROM COMPANY_COUPON WHERE COUPON_ID = " + id;
		sql[1] = "DELETE FROM CUSTOMER_COUPON WHERE COUPON_ID = " + id;
		sql[2] = "DELETE FROM COUPON WHERE ID = " + id;

		Connection con = db.getconnection();
		try (Statement stmt = con.createStatement();){
			
			con.setAutoCommit(false);
			for(int i =0; i < sql.length; i++) {
				stmt.executeUpdate(sql[i]);
			}
								
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println(coupon);
			System.err.println(e.getMessage());	
			
		}finally {
			db.returnConnection(con);
			
		}
	}

	@Override
	public void updateCoupon(Coupon coupon) {
		
		long id = coupon.getId();
		Date endDate = coupon.getEndDate();
		double price = coupon.getPrice();
		
		//update coupon
		String sql = " UPDATE COUPON SET " +
				"end_Date = ?, " +
				"price = ? " +
				"WHERE ID = " + id;
		
		
		Connection con = db.getconnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);){
			
			
			pstmt.setDate	(1, new java.sql.Date(endDate.getTime()));
			pstmt.setDouble	(2, price);					
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println(coupon);
			System.err.println(e.getMessage());
			
		}finally {
			db.returnConnection(con);
			
		}
	}

	@Override
	public Coupon getCoupon(long id) {
		 
		Coupon coupon = null;
		String sql = "SELECT * FROM COUPON WHERE ID = " + id;

		Connection con = db.getconnection();
		try ( Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);){
			
			if(rs.next()) {
				coupon = createCouponFromResultSetd(rs);
				
			}else {
				String messege = "coupon id not found: " + id;
				throw new RuntimeException(messege);
				
			}
	
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println(coupon);
			System.err.println(e.getMessage());
			
		}finally {
			db.returnConnection(con);
			
		}
		return coupon;
	
	}
	
	public boolean couponExists(long id, String title) {
		 
		Coupon coupon = null;
		String sql = "SELECT * FROM COUPON"
				+ " WHERE TITLE = '" + title + "'"
						+ "OR ID = '" + id + "'";

		Connection con = db.getconnection();
		try ( Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);){
			
				if(rs.next()) {
					return true;
					
				}else {
					return false;
				}
				
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println(coupon);
			System.err.println(e.getMessage());
			
		}finally {
			db.returnConnection(con);
			
		}
		return false;
	
	}

	@Override
	public HashSet<Coupon> getAllCoupons() {
		
		//select all coupons
		HashSet<Coupon> coupons = new HashSet<>();
		String sql = " SELECT * FROM COUPON";
		
		Connection con = db.getconnection();		
		try ( Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);){
												
			while(rs.next()) {
				
				Coupon coupon = createCouponFromResultSetd(rs);
				coupons.add(coupon);				
			}
						
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println(coupons);
			System.err.println(e.getMessage());
			
		}finally {
			db.returnConnection(con);
			
		}	
		return coupons;
	}

	@Override
	public HashSet<Coupon> getCouponsByType(CouponType couponType) {

		//select all coupons
		HashSet<Coupon> coupons = new HashSet<>();
		String sql = " SELECT * FROM COUPON WHERE TYPE = '" + couponType.toString() +" '";
		 
		Connection con = db.getconnection();		
		try ( Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);){
												
			while(rs.next()) {				

				Coupon coupon = createCouponFromResultSetd(rs);			
				coupons.add(coupon);
			}
						
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println(coupons);
			System.err.println(e.getMessage());
			
		}finally {
			db.returnConnection(con);
			
		}	
		return coupons;
	}

	private boolean couponIdOrNameExists(Coupon coupon){
		
		long id = coupon.getId();
		String coupName = coupon.getTitle();
		boolean exists = false;
		String sql 	= "SELECT * FROM COUPON "
					+ "WHERE id = " + id + " "
					+ "OR TITLE = '" + coupName + "'";
		
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

	public void deleteExpiredCoupons() {
		
		String[] sql = new String[3];
		sql[0] 	= 	"DELETE FROM CUSTOMER_COUPON " 			+
					"WHERE COUPON_ID IN (" 					+
						"SELECT id FROM COUPON " 			+
						"WHERE END_DATE < current_date()" 	+ 	
					")"										;
		
		sql[1] 	= 	"DELETE FROM COMPANY_COUPON " 			+
					"WHERE COUPON_ID IN (" 					+
						"SELECT id FROM COUPON "			+
						"WHERE END_DATE < current_date()" 	+
					")"										;
		
		sql[2] 	= 	"DELETE FROM COUPON " 					+
					"WHERE END_DATE < current_date()" 		;

		Connection con = db.getconnection();
		try ( Statement stmt = con.createStatement(); ){
			
			for (int i = 0; i < sql.length; i++) {
				stmt.executeUpdate(sql[i]);
			}
				
		}catch (SQLException e) {
			System.err.println(sql);
			System.err.println(e.getMessage());
			
		}finally {
			db.returnConnection(con);	
			
		}		
	}
	
	
}
