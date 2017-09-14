package victor.first;

import java.io.IOException;
import java.util.Random;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PrimuServlet implements Servlet {

	@Override
	public void destroy() {
	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public String getServletInfo() {
		return null;
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		
		
		
		HttpSession session = httpRequest.getSession();
		if (session.isNew()) {
			session.setAttribute("zar", new Random().nextInt(6) + 1);
		}
		
		
		httpResponse.setHeader("Content-Type", "text/plain");
		httpResponse.getWriter().println(
				"<html><body><h1>Hello Web !! " + 
						session.getAttribute("zar")+"</h1></body></html>");
		System.out.println("p1 = " + httpRequest.getParameter("p1"));
		System.out.println("p2 = " + httpRequest.getParameter("p2"));
	}

}
