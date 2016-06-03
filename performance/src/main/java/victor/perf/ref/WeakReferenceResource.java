package victor.perf.ref;

import java.util.Map;
import java.util.WeakHashMap;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import victor.perf.leaks.BigObject20MB;

@Path("weak")
public class WeakReferenceResource {

	@Inject
	private HttpServletRequest request;
	
	static Map<String, BigObject20MB> cache = new WeakHashMap<>();
	
	@GET
	public String requestWeakData() {
		HttpSession session = request.getSession();
		BigObject20MB object = cache.get("a");
		String result;
		if (object == null) {
			result = "Fetching real object and putting it into the cache";
			object = new BigObject20MB();
			cache.put("a", object);
			session.setAttribute("object",  object);
		} else {
			result = "Reusing value from cache";
			session.setAttribute("object", object);
		}
		return result;		
	}
	
	@GET
	@Path("logout")
	public void logout() {
		request.getSession().invalidate();
	}
	
}
