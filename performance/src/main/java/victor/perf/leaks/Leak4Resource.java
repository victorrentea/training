package victor.perf.leaks;

import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("leak4")
public class Leak4Resource {
	@Inject
    private SessionContext session;
	
	@GET
	public String test() throws Exception {
		String uuid = UUID.randomUUID().toString();
		session.tryCache(uuid, BigObject20MB::new);
		return "the most subtle";
	}
}
