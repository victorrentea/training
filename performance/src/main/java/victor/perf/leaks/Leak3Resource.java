package victor.perf.leaks;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import victor.perf.leaks.CachingMethodObject.UserRightsCalculator;

@Path("leak3")
public class Leak3Resource {
	@Context
    private HttpServletRequest request;
	
	@GET
	public String test() {
		UserRightsCalculator rights = new CachingMethodObject().createRightsCalculator();
		HttpSession session = request.getSession();
		session.setAttribute("rights",rights);
		return "even more subtle";
	}
}
