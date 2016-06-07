package victor.perf.leaks;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

@Path("leak3")
public class Leak3Resource {
	@Context
    private HttpServletRequest request;
	
	@GET
	public String test() {		
		// Clear JSESSIONID cookie
		HttpSession session = request.getSession();
		System.out.println("was new session: " + session.isNew());
		session.setAttribute("rights",new BigObject80MB());
		return "even more subtle";
	}
}
