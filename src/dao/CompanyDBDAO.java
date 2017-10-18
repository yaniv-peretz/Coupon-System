package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import dbConnection.DBconnection;
import javaBeans.Company;
import javaBeans.Coupon;

public class CompanyDBDAO implements CompanyDAO{
	
	private static DBconnection db = DBconnection.getInstance();
	private Company lastLoginCompany = null;
	
	@Override
	public boolean login(String compName, String password) {
		
		boolean login = false;
		String sql = "SELECT * FROM COMPANY WHERE "
				+ "(COMP_NAME = '" + compName + "' "
				+ "AND PASSWORD = '" + password + "' )";
		
		Connection con = db.getconnection();
		try (Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);){
			
			if(rs.next()) {
				lastLoginCompany = setCompnayFromResultSet(rs);
				login = true;
				
			}
								
		}catch (SQLException e) {
			/*
			 * Error in case of SQL failure. 
			 * Unsuccessful login exception is handled at the ClientFacade level.
			 */
			System.err.println(sql);
			System.err.println(e.getMessage());
			
		}finally {
			db.returnConnection(con);
			
		}
		
		return login;
	}

	/**
	 * Return the login company details. details are nulled after each return.
	 * @param compName
	 * @param password
	 * @return
	 */
	public Company getLoginCompany(String compName, String password) {
		/*
		 * lastLoginCompany details are nulled in order to prevent sequential unsuccessful login to receive a valid pointer (to the last succefull login_
		 */
		Company Comp = new Company(lastLoginCompany);
		lastLoginCompany = null;
		
		return Comp;
	}

	@Override
	public void createCompany(Company comp) {
		/**
		 * Create company does not add coupons.
		 * Call add coupons instead.
		 * not adding the coupons is done in order to prevent adminFacade to create a company with coupons (admin should not add coupons) 
		 */
		
		//Prevent storing in DB company with existing id or name.
		if(idOrCompNameAlreadyExists(comp)) {
			// The requirement is not to set Company Name as key in Company Table, therefore a checked exception will be thrown if a duplicated Company name is sent in the create query.
			throw new RecordExistsViolation(comp);

		}
				
		//Add a new Company to DB
		String sql = "INSERT INTO COMPANY VALUES ( ?, ? ,?, ?)";
		
		Connection con = db.getconnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setLong(1, comp.getId());
			pstmt.setString(2, comp.getCompName());
			pstmt.setString(3, comp.getPassword());
			pstmt.setString(4, comp.getEmail());
			
			pstmt.executeUpdate();
							
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println(comp);
			System.err.println(e.getMessage());
			
		}finally {
			db.returnConnection(con);
			
		}
	}
	
	/**
	 * Add to Company coupons (does not remove coupons).
	 * @param comp
	 */
	public void setCoupons(Company comp){
		
		//Capture the company's coupons.
		if(!comp.getCoupons().isEmpty()) {
					
			long id = comp.getId();			
			String sql = "INSERT INTO COMPANY_COUPON VALUES ( " + id + ", ? )";
			
			Connection con = db.getconnection();
			try (PreparedStatement pstmt = con.prepareStatement(sql);){
				
				Iterator<Coupon> itr = comp.getCoupons().iterator();												
				while(itr.hasNext()) {
					Coupon coupon = itr.next();					
					pstmt.setLong(1, coupon.getId());					
					pstmt.executeUpdate();					
					
				}
			
			}catch (SQLException e) {
				System.err.println(sql);
				System.err.println(e.getMessage());
				e.printStackTrace();
				
			}finally {
				db.returnConnection(con);
			}
		}
	}

	@Override
	public Company getCompany(long id) {
		
		Company comp = null;		 
		String sql = "SELECT * FROM COMPANY WHERE ID = " + id;
		
		Connection con = db.getconnection();
		try ( Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);){
				
			if(rs.next()) {				
				comp = setCompnayFromResultSet(rs);
			}
	
		} catch (SQLException e) {
			System.err.println(sql + " " + comp);
			System.err.println(e.getMessage());
		}
		
		//set company coupons
		sql = "SELECT * FROM COUPON "
				+ "WHERE ID IN ("
					+ "SELECT COUPON_ID FROM COMPANY_COUPON"
					+ " WHERE COMP_ID = " + id + ")";
		
		try ( Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);){
			
			HashSet<Coupon> coupons = new HashSet<>();
			CouponDBDAO couponDB = new CouponDBDAO();
			
			while(rs.next()) {
				
				Coupon coupon = couponDB.createCouponFromResultSetd(rs);
				coupons.add(coupon);
			}
			comp.setCoupons(coupons);
				
		} catch (SQLException e) {
			System.err.println(sql);			
			System.err.println(e.getMessage());
			e.printStackTrace();
			
		}finally {
			db.returnConnection(con);
			
		}
		
		return comp;
	}

	@Override
	public HashSet<Coupon> getCoupons() {
		
		HashSet<Coupon> coupons = null;
		String sql = "SELECT * FROM COUPON";
		
		Connection con = db.getconnection();
		try ( Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);){
			
			CouponDBDAO couponDBDAO = new CouponDBDAO();
			coupons = new HashSet<>();
			
				while(rs.next()) {
					Coupon coupon = couponDBDAO.createCouponFromResultSetd(rs);
					coupons.add(coupon);					
				}
				
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println(e.getMessage());
			
		}finally {
			db.returnConnection(con);
			
		}
		
		return coupons;
	}

	@Override
	public HashSet<Company> getAllCompanies() {
		
		HashSet<Company> companies = null;
		String sql = "SELECT * FROM COMPANY";
		 
		Connection con = db.getconnection();
		try (Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);){
			
			companies = new HashSet<>();
				while(rs.next()) {
					Company comp = setCompnayFromResultSet(rs);
					
					companies.add(comp);
				}
				
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println(e.getMessage());
			
		}finally {
			db.returnConnection(con);
			
		}
		
		return companies;
	}

	@Override
	public void upadteCompany(Company comp) {

		//update company
		long id = comp.getId();
		String comName = comp.getCompName();
		String sql = "UPDATE COMPANY SET "
				+ "PASSWORD = ?, "
				+ "EAMIL = ? "
				+ "WHERE ("
					+ "ID = " + id + " "
					+ "AND COMP_NAME = '" + comName + "'"
					+ ")";
		
		Connection con = db.getconnection();		
		try (PreparedStatement pstmt = con.prepareStatement(sql);){
						
			pstmt.setString(1, comp.getPassword());
			pstmt.setString(2, comp.getEmail());			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println(comp);
			System.err.println(e.getMessage());
			e.printStackTrace();
			
		}finally {
			db.returnConnection(con);
			
		}	
	}

	@Override
	public void removeCompany(Company comp) {
		
		//fetch the company with all coupons.
		comp = this.getCompany(comp.getId());
		HashSet<Coupon> coupons = comp.getCoupons();
		
		//create a comma separated list of Coupons IDs
		String couponsIds = IdList.convertHashSetToIdList(coupons);		
						
		//delete company, company coupons, and customers holding these coupons..
		long id = comp.getId();
		String[] sql = new String[4];
		sql[0] = "DELETE FROM CUSTOMER_COUPON WHERE COUPON_ID IN " + couponsIds;
		sql[1] = "DELETE FROM COMPANY_COUPON WHERE COMP_ID = " + id;
		sql[2] = "DELETE FROM COUPON WHERE ID IN " + couponsIds;
		sql[3] = "DELETE FROM COMPANY WHERE ID = " + id;
		String currentSql = null;
		//NOTE: SQL deletion order is important, DO NOT CHANGE
		
		Connection con = db.getconnection();
		try (Statement stmt = con.createStatement();){
	        con.setAutoCommit(false);
	        
			for(int i = 0; i < sql.length; i++) {
				currentSql = sql[i];
				stmt.executeUpdate(currentSql);
				
			}
			
            con.commit();
            con.setAutoCommit(true);
            
		} catch (SQLException e) {
			System.err.println(currentSql);
			System.err.println(e.getMessage());
			e.printStackTrace();
			
		}finally {
			
			db.returnConnection(con);
			
		}
	}
	
	private Company setCompnayFromResultSet(ResultSet rs) throws SQLException {
	
		Company comp = null;
			
		long id 		= rs.getLong(1);
		String compName = rs.getString(2);
		String password = rs.getString(3);
		String email 	= rs.getString(4);
		comp 	= new Company(id, compName, password, email);
		
		return comp;		 
	}
	
	private boolean idOrCompNameAlreadyExists(Company comp){
		
		long id = comp.getId(); 
		String compName = comp.getCompName();
		
		boolean exists = false;
		String sql 	= "SELECT * FROM COMPANY "
					+ "WHERE id = " + id + " "
					+ "OR COMP_NAME = '" + compName + "'";
		
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
	
}
