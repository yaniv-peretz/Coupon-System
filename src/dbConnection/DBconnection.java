package dbConnection;

import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;

/**
 * <b>Singleton class</b> Set up the Database table structure and create a DB connection pool.
 * @author Yaniv Peretz.
 *   
 */
public class DBconnection {
	
	//The Main connection pool which allows access to the DB.
	//The purpose of the connectionPoolBackup is to ensure the validity of the returned connections.
	private static HashSet<Connection> connectionPool = null;
	private static HashSet<Connection> connectionPoolBackup = null;	
	private static final int NUMBER_OF_CONNECTIONS = 10;
	
	private static DBconnection INSTANCE = null;
	/**
	 * <b>Constructor</b> 
	 * 
	 * Setup JDBC driver (Java DataBase Client) and initialize the connection pool.
	 */
	private DBconnection() {
		
		super();
						
			//check if the connections were already initialized.
			if(connectionPool != null) {
				return;
				
			}else {
				try {
				//setup the connection driver
				String driverName = "com.mysql.jdbc.Driver";
				Class.forName(driverName);
				
				//initialize new connections into connectionPool
				connectionPool = new HashSet<>();
				connectionPoolBackup = new HashSet<>();
				loadConnectionsToConnectionPool();
				
				//validate that the DB structure is setup
				validateDBstructure();
				
				} catch (ClassNotFoundException e) {
					System.out.println("Driver for jdbc loading failed check liberies");
					e.printStackTrace();
					
				}
			}			
	}
		
	public static DBconnection getInstance(){
		
		if(INSTANCE == null) {
			INSTANCE = new DBconnection();
			
		}
		
		return INSTANCE;
	} 
	private void loadConnectionsToConnectionPool() {		
		/*
		 * Create a new pool of connections.
		 */
		try {			
			String connetionName = "jdbc:mysql://"+ConnectionVars.ip+":"+ConnectionVars.port+"/"+ConnectionVars.dbName;
			for(int i = 0; i < NUMBER_OF_CONNECTIONS; i++) {
				Connection con = DriverManager.getConnection(connetionName,ConnectionVars.user,ConnectionVars.password);
				connectionPool.add(con);
				connectionPoolBackup.add(con);
				}
						
			} catch (SQLTimeoutException e) {
				e.printStackTrace();
			}catch (SQLException e) {
				System.out.println("JDBC connection failed, JDBC is down or table does not exists");
				e.printStackTrace();
			}
				
		}
	
	private void validateDBstructure() {
		/**
		 * validate that the DB structure is set up.
		 */
		String sql = "";
		Connection con = connectionPool.iterator().next();		
		for(DBTables table : DBTables.values()) {
			
			try (Statement stmt = con.createStatement();){
				DatabaseMetaData md = con.getMetaData();
				
				String tableName = table.toString();
				try(ResultSet rs = md.getTables(null, null, tableName, new String[] {"TABLE"});){
					
					// if the DB table does not exist, create it
					if(!rs.next()) {
						String attributes = "";
						switch(table) {
							case COUPON:			attributes = ""
													+ "ID BIGINT primary key, "
													+ "TITLE VARCHAR(255) NOT NULL, "
													+ "START_DATE date NOT NULL, "
													+ "END_DATE date, "
													+ "AMOUNT INT NOT NULL, "
													+ "TYPE varchar(255), "
													+ "MESSAGE varchar(255), "
													+ "PRICE DOUBLE NOT NULL, "
													+ "IMAGE varchar(255)";
												break;
												
							case COMPANY:			attributes = ""
													+ "ID BIGINT primary key, "
													+ "COMP_NAME varchar(255) NOT NULL, "
													+ "PASSWORD varchar(255) NOT NULL, "
													+ "EAMIL varchar(255) NOT NULL";
												break;
												
							case CUSTOMER:		attributes = ""
													+ "ID BIGINT primary key, "
													+ "CUST_NAME varchar(255) NOT NULL, "
													+ "PASSWORD varchar(255) NOT NULL";
												break;
												
							case COMPANY_COUPON:	attributes = ""
													+ "COMP_ID BIGINT NOT NULL, "
													+ "COUPON_ID BIGINT NOT NULL, "
													+ "FOREIGN KEY (COMP_ID) REFERENCES COMPANY(ID), "
													+ "FOREIGN KEY (COUPON_ID) REFERENCES COUPON(ID)";
												break;
												
							case CUSTOMER_COUPON:	attributes = ""
													+ "CUST_ID BIGINT NOT NULL, "
													+ "COUPON_ID BIGINT NOT NULL, "
													+ "FOREIGN KEY (CUST_ID) REFERENCES CUSTOMER(ID), "
													+ "FOREIGN KEY (COUPON_ID) REFERENCES COUPON(ID)";
												break;
						}
						
						sql = "CREATE TABLE " + tableName + " (" + attributes + ")";
						stmt.executeUpdate(sql);

					}
					}catch (SQLException e) {
					System.out.println("failed on: " + sql);
					e.printStackTrace();
				}
			} catch (SQLException e) {
				System.out.println("create statment failed");
				e.printStackTrace();
				
			}finally {
				this.returnConnection(con);
				
			}
		}
		
		System.out.println("\n");
	}
	
	/**
	 * 
	 * @return a new available connection from the connection pool.
	 * returnConnection.
	 */
	public synchronized Connection getconnection() {
		
		while (connectionPool.isEmpty()) {
			try {
				wait();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
				
			}
		}
		
		Iterator<Connection> itr = connectionPool.iterator();
		Connection con =  itr.next();
		
		try{
			
			if(con.isClosed()) {
				String message = "All connections appear to be closed";
				throw new RuntimeException(message);
			}
			
			}catch(Exception e) {
			e.printStackTrace();
		}
			
		return con;
	}

	/**
	 * Returns a connection to the connection pool.
	 * @param con The connection to be returned.
	 */
	public synchronized void returnConnection(Connection con) {
		if (connectionPoolBackup.contains(con)) {
			connectionPool.add(con);
			notify();
		}else {
			System.out.println("connection " + con + " is not part of the original system generated connections");
		}
	}

	/**
	 * close all the connections
	 */
	public void closeAllConnections() {
		
		Iterator<Connection> itr = connectionPool.iterator();
		
		for(int i = 0; i < NUMBER_OF_CONNECTIONS; i++) {
			
			if(itr.hasNext()) {
				
				Connection con = itr.next();
				
				try {
					con.close();
					
				} catch (SQLException e) {
					e.printStackTrace();
					
				}
				
			}else {
				try {
					wait();
					
				} catch (Exception e) {
					e.printStackTrace();

				}
			}	
		}
	}
}
