package yanivProduction.CouponSystemServer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import clientFacade.ClientFacadeInterface;
import clientFacade.ClientType;
import main.CouponSystem;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@GET
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Login only accepts POST request ");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@POST
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

		//if got here with undifiend type
		if (type == null) {
			response.getWriter().append("Login type undifined");
			response.sendRedirect("index.html");
			return;
		}

		// initiate the system - try to login
		CouponSystem sys = CouponSystem.getInstance();
		ClientFacadeInterface client = sys.login(user, password, type);

		//return if login unsuccessful
		if (client == null) {
			response.sendRedirect(request.getContextPath() + "/index.html");
			return;
		}

		//Login successful, set session data
		HttpSession session = request.getSession();
		session.setAttribute("auth", true);
		session.setAttribute("Client-type", client);

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
