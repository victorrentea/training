package victor.perf.leaks;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("leak2")
public class Leak2Resource {
	static ThreadLocal<RequestContext> threadLocal = new ThreadLocal<>();
	
	@GET
	public String test() {
		RequestContext requestContext = new RequestContext();
		threadLocal.set(requestContext);
		requestContext.rights = new CachingMethodObject().createRightsCalculator();
		return "subtle";
	}
}
