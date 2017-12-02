package main.facades;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import clientFacade.ClientFacadeInterface;
import clientFacade.ClientType;
import main.CouponSystem;

/**
 * Servlet implementation class Login
 */

@Controller
@RequestMapping("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@RequestMapping(method = RequestMethod.GET)
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Login only accepts POST request ");
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@RequestMapping(method = RequestMethod.POST)
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String user = request.getParameter("user");
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
		
		// if got here with undifiend type
		if (type == null) {
			response.getWriter().append("Login type undifined");
			response.sendRedirect(request.getContextPath() + "/index.html?err=type");
			return;
		}
		
		// initiate the system - try to login
		CouponSystem sys = CouponSystem.getInstance();
		ClientFacadeInterface client = sys.login(user, password, type);
		
		// return if login unsuccessful
		if (client == null) {
			response.sendRedirect(request.getContextPath() + "/index.html?err=psw");
			return;
		}
		
		// Login successful, set session data
		HttpSession session = request.getSession();
		session.setAttribute("auth", true);
		session.setAttribute("client", client);
		
		switch (type) {
		case ADMIN:
			response.sendRedirect(request.getContextPath() + "/admin.html");
			break;
		
		case COMPANY:
			response.sendRedirect(request.getContextPath() + "/company.html");
			break;
		
		case CUSTOMER:
			response.sendRedirect(request.getContextPath() + "/customer.html");
			break;
		
		default:
			response.sendRedirect(request.getContextPath() + "/index.html");
		}
		return;
	}
	
}