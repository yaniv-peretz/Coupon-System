package main.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;

public class AuthFilter implements Filter {
	
	@Override
	public void destroy() {
	}
	
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		
		boolean auth = false;
		
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpSession session = request.getSession();
		if (session.getAttribute("auth") != null) {
			auth = (boolean) session.getAttribute("auth");
		}
		
		if (auth) {
			arg2.doFilter(arg0, arg1);
			
		} else {
			HttpServletResponse response = (HttpServletResponse) arg1;
			response.sendError(HttpStatus.FORBIDDEN.value());
			
		}
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
	
}
