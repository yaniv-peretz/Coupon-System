package unitTestsDBDAO;

import java.util.HashSet;
import java.util.Iterator;

import dao.CompanyDBDAO;
import javaBeans.Company;

public class UnitTestsCompanyDBDAO {

	public static void main(String[] args) {
		
		CompanyDBDAO db = new CompanyDBDAO();
		System.out.println("Starting Tests");
		System.out.println("----------------------");

		
		long id = 101L;
		Company comp = new Company(id, "The test second test", "password", "email@email.com");
		db.createCompany(comp);
		System.out.println("sent to DB:" + comp);
		
		boolean login;
		login = db.login("The test Company", "password");
		System.out.println("login success: " + login);
		
		login = db.login("The test Company", "Worngpassword");
		System.out.println("failed login (requires false): " + login);
		
		Company getComp = db.getCompany(id);
		System.out.println("get from DB:" + getComp);
		
		//TODO ADD COUPONS
//		db.upadteCompany(comp);
//		System.out.println("updated DB:" + comp);
		
		
		db.removeCompany(comp);
		System.out.println("removed from DB:" + comp);
		
		System.out.println("\nUploading new companies");
		System.out.println("--------------------------");
		comp = new Company(200L, "test200", "password200", "200email@email.com");
		db.createCompany(comp);
		comp = new Company(201L, "test201", "password201", "201email@email.com");
		db.createCompany(comp);
		comp = new Company(202L, "test202", "password202", "202email@email.com");
		db.createCompany(comp);
		
		//TODO: get coupons##################################

		System.out.println("\nprinting all 200+ companies");
		System.out.println("--------------------------");
		HashSet<Company> allcompanies = db.getAllCompanies();
		Iterator<Company> itr = allcompanies.iterator();
		while(itr.hasNext()) {
			System.out.println(itr.next());
		}
		
		System.out.println("\nprinting companies after 200+ removals");
		System.out.println("--------------------------");
		comp = new Company(200L, "test200", "password200", "200email@email.com");
		db.removeCompany(comp);
		comp = new Company(201L, "test200", "password200", "200email@email.com");
		db.removeCompany(comp);
		comp = new Company(202L, "test200", "password200", "200email@email.com");
		db.removeCompany(comp);
		
		allcompanies = db.getAllCompanies();
		itr = allcompanies.iterator();
		while(itr.hasNext()) {
			System.out.println(itr.next());
		}		
	}
}
