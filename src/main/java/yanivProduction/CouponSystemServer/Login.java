package yanivProduction.CouponSystemServer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import clientFacade.ClientType;
import clientFacade.CouponClientFacade;
import main.CouponSystem;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		response.getWriter().append("Login only accepts POST request ");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String user 	= request.getParameter("user");
		String password = request.getParameter("password");
		String selector = request.getParameter("selector");
		
		ClientType type = null;
		
		switch (selector) {
		case "admin":
			type = ClientType.ADMIN;
			break;
		
		case "company":
			type = ClientType.COMPANY;
			break;
			
		case "customer":
			type = ClientType.CUSTOMER;
			break;

		}
		
		if(type == null) {
			response.getWriter().append("Login type undifined");
			response.sendRedirect("index.html");
			return;
		}
		
		// initiate the system
		CouponSystem sys = CouponSystem.getInstance();
		CouponClientFacade client = sys.login(user, password, type);
		
		//return if login unsuccessful
		if(client == null){
			response.sendRedirect(request.getContextPath() +"/index.html");
			return;
		}
		
		//Login successful, set session data
		HttpSession session = request.getSession();
		session.setAttribute("auth", true);
		session.setAttribute("Client-type", client);
		response.sendRedirect(request.getContextPath() +"/app.html");
		return;
	}

}
